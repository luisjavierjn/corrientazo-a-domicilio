package com.corrientazo.support;

import com.corrientazo.core.FileListenerPort;

public abstract class FileListener implements FileListenerPort {

    @Override
    public void onCreated(FileEvent event) {
        // no implementation provided
    }

    @Override
    public void onModified(FileEvent event) {
        // no implementation provided
    }

    @Override
    public void onDeleted(FileEvent event) {
        // no implementation provided
    }
}
