package ru.medvedev.logging;

import java.io.*;

/**
 * Created by Сергей on 12.04.2016.
 */
public class HtmlHandler implements Handler {

    private String fullPath;

    public HtmlHandler(String fullPath) throws IOException {
        this.fullPath = fullPath;
    }

    private String getHead() {
        return "<html><head><title>AppLog</title>" +
                "<meta http-equiv=\"content-type\" content=\"text/html; charset=utf-8\">" +
                "</head><body>" +
                "<table border=1>" +
                "<tr bgcolor=CYAN><td>num</td><td>date'n'time</td><td>level</td><td>message</td></tr>";
    }

    private String getTail() {
        return "</table></body></html>";
    }

    private String makeFormatString(Record record)
    {
        StringBuilder result=new StringBuilder();
        Level level = record.getLevel();


        String oldFile = null;
        try {
            oldFile = read();
        } catch (IOException e) {
            e.printStackTrace();
        }

        result.append("\n");

        result.append("<td>");
        result.append(record.getNum());
        result.append("</td><td>");
        result.append(record.getDate());
        result.append("</td><td>");
        result.append(record.getLevel());
        result.append("</td><td>");
        result.append(record.getMessage());
        result.append("</td><td>");


        result.append("</tr>\n");

        if (!oldFile.isEmpty()) oldFile = oldFile.replace(getTail(),result.toString() + getTail());
        else oldFile = getHead() + result.toString() + getTail();

        return oldFile;
    }

    private void write(String text) throws IOException {
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

    private String read() throws IOException {
        File file = new File(fullPath);
        StringBuilder sb = new StringBuilder();
        exists(fullPath);
        try {
            BufferedReader in = new BufferedReader(new FileReader( file.getAbsoluteFile()));
            try {
                String s;
                while ((s = in.readLine()) != null) {
                    sb.append(s);
                    sb.append("\n");
                }
            } finally {
                in.close();
            }
        } catch(IOException e) {
            throw new RuntimeException(e);
        }
        return sb.toString();
    }

    private void exists(String fileName) throws IOException {
        File file = new File(fileName);
        if (!file.exists()){
            file.createNewFile();
        }
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
