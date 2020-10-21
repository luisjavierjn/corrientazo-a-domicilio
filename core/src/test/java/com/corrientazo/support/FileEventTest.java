package com.corrientazo.support;

import org.junit.Test;

import java.io.File;

import static org.junit.Assert.assertEquals;

public class FileEventTest {

    @Test
    public void createFileEvent() {
        File file = new File("src/test/resources");
        FileEvent fileEvent = new FileEvent(file);
        assertEquals(fileEvent.getFile(),file);
    }
}
