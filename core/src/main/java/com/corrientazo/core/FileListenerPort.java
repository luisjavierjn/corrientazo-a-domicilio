package com.corrientazo.core;

import com.corrientazo.support.FileEvent;

import java.util.EventListener;
public interface FileListenerPort extends EventListener {
    void onCreated(FileEvent event);
    void onModified(FileEvent event);
    void onDeleted(FileEvent event);
}