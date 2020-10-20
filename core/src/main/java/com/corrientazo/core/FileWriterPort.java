package com.corrientazo.core;

import java.io.IOException;

public interface FileWriterPort {
    FileWriterPort setPathAndHeader(String path, String header);
    void addNewLine(String line);
    void write(String fileName) throws IOException;
}
