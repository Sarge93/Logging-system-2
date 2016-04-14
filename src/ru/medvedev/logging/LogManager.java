package ru.medvedev.logging;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;

/**
 * Created by Сергей on 12.04.2016.
 */


public class LogManager {
    private static LogManager logManager = new LogManager();

    private ArrayList<Logger> loggers;
    private File propertiesFile;
    private Properties settings;
    private Level defaultLevel;


    {
        String userDir = System.getProperty("user.home");
        File propertiesDir = new File(userDir, ".prop");
        if (!propertiesDir.exists()) propertiesDir.mkdir();
        propertiesFile = new File(propertiesDir, "logging.properties");

        Properties defaultSettings = new Properties();
        defaultSettings.put("level", "info");
        defaultSettings.put("loggers", "0");

        settings = new Properties(defaultSettings);

        if (propertiesFile.exists()) {
            try {
                FileInputStream stream = new FileInputStream(propertiesFile);
                settings.load(stream);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        defaultLevel = Level.stringToLevel(settings.getProperty("level"));
        ArrayList<Logger> temp = new ArrayList<Logger>();
        int numberOfLoggers = Integer.parseInt(settings.getProperty("loggers"));
        for (int i = 0; i < numberOfLoggers; i++) {

        }

    }

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

    public Level getDefaultLevel() {
        return defaultLevel;
    }
}
