package ru.medvedev.logging;

import java.nio.charset.Charset;

/**
 * Created by Сергей on 20.04.2016.
 */
public class XMLFormatter implements Formatter {

    @Override
    public String format(Record record) {

        StringBuilder sb = new StringBuilder();
        String encoding;
        sb.append("<?xml version=\"1.0\"");

        encoding = java.nio.charset.Charset.defaultCharset().name();

        try {
            Charset cs = Charset.forName(encoding);
            encoding = cs.name();
        } catch (Exception ex) {
        }

        sb.append(" encoding=\"");
        sb.append(encoding);
        sb.append("\"");
        sb.append(" standalone=\"no\"?>\n");
        sb.append("<!DOCTYPE log SYSTEM \"logger.dtd\">\n");
        sb.append("<log>\n");

        sb.append("<record>\n");

        sb.append("  <date>");
        sb.append(record.getDate());
        sb.append("</date>\n");

        sb.append("  <number>");
        sb.append(record.getNum());
        sb.append("</number>\n");

        sb.append("  <level>");
        sb.append(record.getLevel());
        sb.append("</level>\n");

        if (record.getMessage() != null) {
            sb.append("  <message>");
            sb.append(record.getMessage());
            sb.append("</message>");
            sb.append("\n");
        }
        sb.append("</record>\n");
        sb.append("</log>\n");
        return sb.toString();
    }
}
