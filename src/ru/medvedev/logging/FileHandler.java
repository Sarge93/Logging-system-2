package ru.medvedev.logging;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by Сергей on 12.04.2016.
 */
public class FileHandler extends Handler {

    private String fullPath;

    public void write(String text) throws IOException {
        File file = new File(fullPath);

        try {
            if(!file.exists()){
                file.createNewFile();
            }
            PrintWriter out = new PrintWriter(file.getAbsoluteFile());
            try {
                out.print(text);
            } finally {
                out.close();
            }
        } catch(IOException e) {
            throw new RuntimeException(e);
        }
    }

    public FileHandler(String fullPath) throws IOException {
        this.fullPath = fullPath;
    }
}
