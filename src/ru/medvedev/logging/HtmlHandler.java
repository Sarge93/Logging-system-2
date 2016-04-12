package ru.medvedev.logging;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by Сергей on 12.04.2016.
 */
public class HtmlHandler implements Handler {

    private String fullPath;

    public String getHead(Handler h) {
        return "<html><head><title>AppLog</title>" +
                "<meta http-equiv=\"content-type\" content=\"text/html; charset=utf-8\">" +
                "</head><body>" +
                "<table border=1>" +
                "<tr bgcolor=CYAN><td>num</td><td>date</td><td>level</td><td>message</td></tr>";
    }

    public String getTail(Handler h) {
        return "</table></body></html>";
    }

    public String makeFormatString(Record record)
    {
        StringBuilder result=new StringBuilder();
        Level level = record.getLevel();

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
        return result.toString();
    }

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


    @Override
    public void publish(Record record) {
        try {
            this.write(makeFormatString(record));
        } catch (IOException e) {
            new Logger().log(Level.warning, "Ошибка ввода/вывода");
        }
    }
}
