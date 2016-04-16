package ru.medvedev.logging;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by Сергей on 12.04.2016.
 */
public class FileHandler implements Handler {

    private String fullPath;

    private void write(String text) throws IOException {
        File file = new File(fullPath);

        try {
            if(!file.exists()){
                file.createNewFile();
            }

            FileWriter fileWriter = new FileWriter(file.getAbsoluteFile(),true);
            try {
                fileWriter.write(text);
                fileWriter.write("\r\n");

            } finally {
                fileWriter.close();
            }
        } catch(IOException e) {
            throw new RuntimeException(e);
        }
    }

    public FileHandler(String fullPath) throws IOException {
        this.fullPath = fullPath;
    }

    private String makeFormatString(Record record) {
        StringBuilder result = new StringBuilder();
        result.append(record.getNum());
        result.append(": " + record.getDate());
        result.append(" --- " + record.getLevel());
        result.append(": " + record.getMessage());
        return result.toString();
    }

    @Override
    public void publish(Logger logger, Record record) {
        try {
            this.write(makeFormatString(record));
        } catch (IOException e) {
            Logger.createLogger(getClass().getName()).log(Level.warning, "Ошибка ввода/вывода");
        }
    }
}
