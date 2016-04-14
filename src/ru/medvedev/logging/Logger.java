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
    private Level loggerLevel;

    public String getName() {
        return name;
    }

    public static Logger createLogger(String name) {
        LogManager logManager = LogManager.getLogManager();
        Logger logger = logManager.getLogger(name);
        return logger;
    }

    public Logger() {
        loggerLevel = LogManager.getLogManager().getDefaultLevel();
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
        if (level.ordinal() < loggerLevel.ordinal()) {
            return;
        }
        Record record = new Record(level, lodMsg);
        if ((filter != null) && (!filter.isLoggable(record))) {
            return;
        }

        Logger logger = this;

        while (logger != null) {
            ArrayList<Handler> handlers = logger.getHandlers();
            if (handlers.isEmpty()) handlers.add(new ConsoleHandler());
            for (Handler handler : handlers) {
                handler.publish(logger, record);
            }
            logger = null;
        }

    }

    public void log(Level level, Exception ex) {
        if (level.ordinal() < loggerLevel.ordinal()) {
            return;
        }
        Record record = new Record(level, transformExeption(ex));
        if ((filter != null) && (!filter.isLoggable(record))) {
            return;
        }

        Logger logger = this;

        while (logger != null) {
            ArrayList<Handler> handlers = logger.getHandlers();
            if (handlers.isEmpty()) handlers.add(new ConsoleHandler());
            for (Handler handler : handlers) {
                handler.publish(logger, record);
            }
            logger = null;
        }

    }

    private String transformExeption(Exception ex) {
        String result = "";
        result += ex.getMessage();
        result += "\n" + ex.getLocalizedMessage();
        result += "\n" + ex.getStackTrace();
        return result;
    }
}
