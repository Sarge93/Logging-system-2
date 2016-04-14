package ru.medvedev.logging;

import java.io.*;
import java.util.ArrayList;
import java.util.Properties;

/**
 * Created by Сергей on 12.04.2016.
 */


public class LogManager {
    private final static LogManager logManager = new LogManager();

    private ArrayList<Logger> loggers;
    private File propertiesFile;
    private Properties settings;
    private Level defaultLevel;
    private boolean config = false;


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
                stream.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }

    private LogManager() {}

    public static LogManager getLogManager() {
        return logManager;
    }

    Logger getLogger(String name) {
        if (!config) {
            defaultLevel = Level.stringToLevel(settings.getProperty("level"));
            ArrayList<Logger> temp = new ArrayList<Logger>();
            int numberOfLoggers = Integer.parseInt(settings.getProperty("loggers"));
            for (int i = 0; i < numberOfLoggers; i++) {
                String n = settings.getProperty(Integer.valueOf(i).toString());
                temp.add(new Logger(n));
            }
            loggers = temp;
            temp = null;
            config = true;
        }
        Logger resultLogger = findLogger(name);
        if (resultLogger == null) {
            resultLogger = new Logger(name);
            loggers.add(resultLogger);
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
