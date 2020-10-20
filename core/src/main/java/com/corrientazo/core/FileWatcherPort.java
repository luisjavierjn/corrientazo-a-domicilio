package com.corrientazo.core;

import java.io.File;
import java.util.List;

public interface FileWatcherPort {
    FileWatcherPort setFolder(File folder);
    void watch();
    FileWatcherPort addListener(FileListenerPort listener);
    FileWatcherPort removeListener(FileListenerPort listener);
    List<FileListenerPort> getListeners();
    FileWatcherPort setListeners(List<FileListenerPort> listeners);
}
