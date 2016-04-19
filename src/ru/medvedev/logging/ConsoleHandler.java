package ru.medvedev.logging;

/**
 * Created by Сергей on 12.04.2016.
 */
public class ConsoleHandler implements Handler {

    private Formatter formatter;

    public Formatter getFormatter() {
        return formatter;
    }

    public void setFormatter(Formatter formatter) {
        this.formatter = formatter;
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
        System.out.println(formatter.format(record));
    }
}
