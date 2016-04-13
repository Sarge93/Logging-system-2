package ru.medvedev.logging;

import java.util.ArrayList;

/**
 * Created by Сергей on 12.04.2016.
 */


public class LogManager {
    private static LogManager logManager = new LogManager();
    ArrayList<Logger> loggers = new ArrayList<Logger>();

    private LogManager() {}

    public static LogManager getLogManager() {
        return logManager;
    }

    Logger getLogger(String name) {
        Logger resultLogger = findLogger(name);
        if (resultLogger == null) {
            resultLogger = new Logger(name);
            loggers.add(resultLogger);
        }
        return resultLogger;
    }

    private Logger findLogger(String name) {
        for (Logger logger : loggers) {
            if (logger.getName().equals(name)) {
                return logger;
            }
        }
        return null;
    }
}
