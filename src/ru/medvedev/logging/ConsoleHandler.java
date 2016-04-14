package ru.medvedev.logging;

/**
 * Created by Сергей on 12.04.2016.
 */
public class ConsoleHandler implements Handler {

    private String makeFormatString(Record record) {
        String result = "";
        result += record.getNum();
        result += ": " + record.getDate();
        result += " --- " + record.getLevel();
        result += ": " + record.getMessage();
        return result;
    }

    @Override
    public void publish(Logger logger, Record record) {
        System.out.println(makeFormatString(record));
    }
}
