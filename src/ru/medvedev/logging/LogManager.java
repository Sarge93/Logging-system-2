package ru.medvedev.logging;

/**
 * Created by Сергей on 12.04.2016.
 */


public class LogManager {
    private static LogManager logManager = new LogManager();

    private LogManager() {}

    public static LogManager getLogManager() {
        return logManager;
    }
}
