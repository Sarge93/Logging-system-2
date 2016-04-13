package ru.medvedev.logging;

import sun.reflect.Reflection;

import java.util.ArrayList;

/**
 * Created by Сергей on 12.04.2016.
 */
public class Logger {
    private String name;
    private ArrayList<Handler> handlers = new ArrayList<Handler>();
    private Filter filter;
    private Logger parent;
    private Level defaulLevel;

    public String getName() {
        return name;
    }

    public static Logger createLogger(String name) {
        LogManager logManager = LogManager.getLogManager();
        Logger logger = logManager.getLogger(name);
        return logger;
    }

    public Logger() {
        defaulLevel = Level.info;
    }

    public Logger(String name) {
        this();
        this.name = name;
    }

    public void setFilter(Filter filter) {
        this.filter = filter;
    }

    public Filter getFilter() {
        return filter;
    }

    public void addHandler(Handler handler) {

        handlers.add(handler);
    }

    public ArrayList<Handler> getHandlers() {
        return handlers;
    }

    public void log(Level level, String lodMsg) {
        if (level.ordinal() < defaulLevel.ordinal()) {
            return;
        }
        Record record = new Record(level, lodMsg);
        if ((filter != null) && (!filter.isLoggable(record))) {
            return;
        }

        Logger logger = this;

        while (logger != null) {
            ArrayList<Handler> handlers = logger.getHandlers();
            for (Handler handler : handlers) {
                handler.publish(record);
            }
            logger = null;
        }

    }
}
