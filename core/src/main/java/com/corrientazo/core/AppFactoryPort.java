package com.corrientazo.core;

public interface AppFactoryPort {
    DeliveryServicePort createDeliveryService();
    FileWriterPort createFileWriter();
    FileWatcherPort createFileWatcher();
}
