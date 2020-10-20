package com.corrientazo.outbound;

import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

import static org.junit.Assert.*;

public class FileWriterAdapterTest {

    @Before
    public void deleteTestFiles() {
        File myObj = new File("src/test/resources/out01.txt");
        assertFalse(myObj.exists());
    }

    @Test
    public void writeSomeFiles() throws IOException {
        String[] lines = {"first", "second", "third"};

        FileWriterAdapter fileWriterAdapter =  new FileWriterAdapter()
                .setPathAndHeader("src/test/resources","== Reporte de entregas ==");
        fileWriterAdapter.addNewLine(lines[0]);
        fileWriterAdapter.addNewLine(lines[1]);
        fileWriterAdapter.addNewLine(lines[2]);
        fileWriterAdapter.write("out01.txt");

        try {
            File myObj = new File("src/test/resources/out01.txt");
            Scanner myReader = new Scanner(myObj);
            if (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                assertEquals(data,"== Reporte de entregas ==");
            }
            int i=0;
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                assertEquals(data,lines[i++]);
            }
            myReader.close();
            assertTrue(myObj.delete());
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}
