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
    private Formatter formatter;

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
        configure();
        this.fullPath = fullPath;
    }

    public FileHandler() {
        configure();
    }

    private void configure() {
        LogManager manager = LogManager.getLogManager();
        String cname = getClass().getName();
        fullPath = manager.getStringProperty(cname + ".fullpath", System.getProperty("java.io.tmpdir"));
        setFormatter(manager.getFormatterProperty(cname + ".formatter", new SimpleFormatter()));
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

    public void setFormatter(Formatter formatter) {
        this.formatter = formatter;
    }

    public String getFullPath() {
        return fullPath;
    }

    public void setFullPath(String fullPath) {
        this.fullPath = fullPath;
    }
}
