package com.corrientazo.support;

import com.corrientazo.core.FileListenerPort;
import org.junit.Test;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class FileListenerTest {

    @Test
    public void createFileListener() {
        final Map<String, String> map = new HashMap<>();
        FileListenerPort fileListenerPort = new FileListener() {
            public void onCreated(FileEvent event) {
                super.onCreated(event);
                map.put("file.created", "created");
            }
            public void onModified(FileEvent event) {
                super.onModified(event);
                map.put("file.modified", "modified");
            }
            public void onDeleted(FileEvent event) {
                super.onDeleted(event);
                map.put("file.deleted", "deleted");
            }
        };

        File file = new File("src/test/resources");
        FileEvent fileEvent = new FileEvent(file);
        assertEquals(fileEvent.getFile(),file);

        fileListenerPort.onCreated(fileEvent);
        fileListenerPort.onModified(fileEvent);
        fileListenerPort.onDeleted(fileEvent);

        assertEquals("created", map.get("file.created"));
        assertEquals("modified", map.get("file.modified"));
        assertEquals("deleted", map.get("file.deleted"));
    }
}
