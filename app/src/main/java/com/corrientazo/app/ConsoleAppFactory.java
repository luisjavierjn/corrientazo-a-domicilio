package com.corrientazo.app;

import com.corrientazo.core.AppFactoryPort;
import com.corrientazo.core.DeliveryServicePort;
import com.corrientazo.core.FileWatcherPort;
import com.corrientazo.core.FileWriterPort;
import com.corrientazo.inbound.FileWatcherAdapter;
import com.corrientazo.outbound.FileWriterAdapter;
import com.corrientazo.services.DeliveryServiceAdapter;

public class ConsoleAppFactory implements AppFactoryPort {
    @Override
    public DeliveryServicePort createDeliveryService() {
        return new DeliveryServiceAdapter();
    }

    @Override
    public FileWriterPort createFileWriter() {
        return new FileWriterAdapter();
    }

    @Override
    public FileWatcherPort createFileWatcher() {
        return new FileWatcherAdapter();
    }
}
