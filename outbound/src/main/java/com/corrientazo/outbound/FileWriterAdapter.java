package com.corrientazo.outbound;

import com.corrientazo.core.FileWriterPort;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

public class FileWriterAdapter implements FileWriterPort {

    private String path;
    private String header;
    private List<String> lines;

    public FileWriterAdapter() {
        this.lines = new ArrayList<>();
    }

    public FileWriterAdapter setPathAndHeader(String path, String header) {
        this.path = path;
        this.header = header;
        return this;
    }

    public void addNewLine(String line) {
        lines.add(line);
    }

    public void write(String fileName) throws IOException {
        File fout = new File(path.concat("/").concat(fileName));
        try(FileOutputStream fos = new FileOutputStream(fout, false)) {
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
            bw.write(header);
            bw.newLine();
            lines.forEach(l -> {
                try {
                    bw.write(l);
                    bw.newLine();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            lines.clear();
            bw.close();
        }
    }
}
