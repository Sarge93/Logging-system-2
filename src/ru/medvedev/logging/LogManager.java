package ru.medvedev.logging;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;

/**
 * Created by Сергей on 12.04.2016.
 */


public class LogManager {
    private final static LogManager logManager = new LogManager();

    private HashMap<String, Logger> loggers;
    private File propertiesFile;
    private Properties settings;
    private static Level defaultLevel;
    private static Logger rootLogger;
    private static boolean updateConfig = true;


    {
        rootLogger = new Logger("rootLogger");
        rootLogger.addHandler(new ConsoleHandler());
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
                stream.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        defaultLevel = Level.stringToLevel(settings.getProperty("level"));
        HashMap<String, Logger> temp = new HashMap<>();
        int numberOfLoggers = Integer.parseInt(settings.getProperty("loggers"));
        for (int i = 0; i < numberOfLoggers; i++) {
            String n = settings.getProperty(Integer.valueOf(i).toString());
            temp.put(n, new Logger(n));
        }
        loggers = temp;
        temp = null;

    }

    private LogManager() {}

    public static LogManager getLogManager() {
        return logManager;
    }

    Logger getLogger(String name) {

        Logger resultLogger = loggers.get(name);
        if (resultLogger == null) {
            resultLogger = new Logger(name);
            loggers.put(name, resultLogger);
            int numberOfLoggers = Integer.parseInt(settings.getProperty("loggers"));
            settings.put("loggers",Integer.valueOf(numberOfLoggers + 1).toString());
            settings.put(Integer.valueOf(numberOfLoggers).toString(), name);
            try {
                FileOutputStream stream = new FileOutputStream(propertiesFile);
                settings.store(stream, "");
                stream.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return resultLogger;
    }

    public static Level getDefaultLevel() {
        return defaultLevel;
    }

    public static Logger getRootLogger() {
        return rootLogger;
    }

    public static boolean isUpdateConfig() {
        return updateConfig;
    }

    public static void setUpdateConfig(boolean updateConfig) {
        LogManager.updateConfig = updateConfig;
    }
}
