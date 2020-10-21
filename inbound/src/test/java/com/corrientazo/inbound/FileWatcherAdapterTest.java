package com.corrientazo.inbound;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.corrientazo.core.FileListenerPort;
import com.corrientazo.support.FileListener;
import com.corrientazo.support.FileEvent;
import org.junit.Test;

public class FileWatcherAdapterTest {
    @Test
    public void FileWatcherTest() throws IOException, InterruptedException {
        File folder = new File("src/test/resources");
        final Map<String, String> map = new HashMap<>();
        FileWatcherAdapter watcher = new FileWatcherAdapter().setFolder(folder);
        watcher.addListener(new FileListener() {
            public void onCreated(FileEvent event) {
                map.put("file.created", event.getFile().getName());
            }
            public void onModified(FileEvent event) {
                map.put("file.modified", event.getFile().getName());
            }
            public void onDeleted(FileEvent event) {
                map.put("file.deleted", event.getFile().getName());
            }

        }).watch();
        assertEquals(1, watcher.getListeners().size());
        wait(2000);
        File file = new File(folder + "/test.txt");
        try(FileWriter writer = new FileWriter(file)) {
            writer.write("Some String");
        }
        assertEquals(1, FileWatcherAdapter.getWatchServices().size());
        wait(2000);
        file.delete();
        wait(2000);
        assertEquals(file.getName(), map.get("file.created"));
        assertEquals(file.getName(), map.get("file.modified"));
        assertEquals(file.getName(), map.get("file.deleted"));

        File dir = new File(folder + "/directory");
        dir.mkdir();
        wait(2000);
        dir.delete();
        assertEquals(2, FileWatcherAdapter.getWatchServices().size());
    }

    public void wait(int time) throws InterruptedException {
        Thread.sleep(time);
    }

    @Test
    public void FileWatcherListenersCheckedTest() {
        File folder = new File("src/test/resources");
        final Map<String, String> map = new HashMap<>();
        FileWatcherAdapter watcher = new FileWatcherAdapter().setFolder(folder);
        List<FileListenerPort> listeners = new ArrayList<>();
        listeners.add(new FileListener() {
            public void onCreated(FileEvent event) {
                map.put("file.created", event.getFile().getName());
            }
        });
        listeners.add(new FileListener() {
            public void onModified(FileEvent event) {
                map.put("file.modified", event.getFile().getName());
            }
        });
        listeners.add(new FileListener() {
            public void onDeleted(FileEvent event) {
                map.put("file.deleted", event.getFile().getName());
            }
        });
        watcher.setListeners(listeners);
        assertEquals(watcher.getListeners().size(),3);

        watcher.removeListener(listeners.get(0));
        assertEquals(watcher.getListeners().size(),2);
    }
}


