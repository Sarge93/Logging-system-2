package ru.medvedev.logging;


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

    private Logger() {
        loggerLevel = LogManager.getDefaultLevel();
    }

    public Logger(String name) {
        this();
        this.name = name;
        this.parent = LogManager.getRootLogger();
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

        doLog(record);

    }

    public void log(Level level, Exception ex) {
        if (level.ordinal() < loggerLevel.ordinal()) {
            return;
        }
        Record record = new Record(level, transformExeption(ex));
        if ((filter != null) && (!filter.isLoggable(record))) {
            return;
        }
        doLog(record);
    }

    private void doLog(Record record) {
        Logger logger = this;

        while (logger != null) {
            if ((logger.getFilter() != null) && (!logger.getFilter().isLoggable(record))) {
                return;
            }
            if (record.getLevel().ordinal() < logger.getLoggerLevel().ordinal()) {
                return;
            }
            ArrayList<Handler> handlers = logger.getHandlers();
            for (Handler handler : handlers) {
                handler.publish(logger, record);
            }
            logger = logger.getParent();
        }
    }

    public void debug3(String message) {
        log(Level.debug3, message);
    }

    public void debug3(Exception message) {
        log(Level.debug3, message);
    }

    public void debug2(String message) {
        log(Level.debug2, message);
    }

    public void debug2(Exception message) {
        log(Level.debug2, message);
    }

    public void debug1(String message) {
        log(Level.debug1, message);
    }

    public void debug1(Exception message) {
        log(Level.debug1, message);
    }

    public void info(String message) {
        log(Level.info, message);
    }

    public void info(Exception message) {
        log(Level.info, message);
    }

    public void warning(String message) {
        log(Level.warning, message);
    }

    public void warning(Exception message) {
        log(Level.warning, message);
    }

    public void severe(String message) {
        log(Level.severe, message);
    }

    public void severe(Exception message) {
        log(Level.severe, message);
    }


    private String transformExeption(Exception ex) {
        StringBuilder result = new StringBuilder();
        result.append(ex.getMessage());
        result.append("\n" + ex.getLocalizedMessage());
        result.append("\n" + ex.getStackTrace());
        return result.toString();
    }

    public Logger getParent() {
        return parent;
    }

    public void setParent(Logger parent) {
        if (parent == null) throw new NullPointerException();

        this.parent = parent;
    }

    public Level getLoggerLevel() {
        return loggerLevel;
    }

    public void setLoggerLevel(Level loggerLevel) {
        this.loggerLevel = loggerLevel;
    }
}
