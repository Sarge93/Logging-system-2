package ru.medvedev.logging;

/**
 * Created by Сергей on 12.04.2016.
 */
public class ConsoleHandler implements Handler {

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
        System.out.println(makeFormatString(record));
    }
}
