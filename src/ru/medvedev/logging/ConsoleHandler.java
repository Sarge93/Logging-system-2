package ru.medvedev.logging;

/**
 * Created by Сергей on 12.04.2016.
 */
public class ConsoleHandler implements Handler {

    private Formatter formatter;

    public void setFormatter(Formatter formatter) {
        this.formatter = formatter;
    }

    private String makeFormatString(Record record) {
        StringBuilder result = new StringBuilder();
        result.append(record.getNum());
        result.append(": " + record.getDate());
        result.append(" --- " + record.getLevel());
        result.append(": " + record.getMessage());
        return result.toString();
    }

    public ConsoleHandler() {
        configure();
    }

    private void configure() {
        LogManager manager = LogManager.getLogManager();
        String cname = getClass().getName();
        setFormatter(manager.getFormatterProperty(cname + ".formatter", new SimpleFormatter()));
    }

    @Override
    public void publish(Logger logger, Record record) {
        System.out.println(makeFormatString(record));
    }
}
