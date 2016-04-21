package ru.medvedev.logging;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Created by Сергей on 12.04.2016.
 */


public class LogManager {
    private final static LogManager logManager = new LogManager();

    private HashMap<String, Logger> loggers = new HashMap<>();
    private File propertiesFile;
    private Properties settings = new Properties();
    private static Level defaultLevel;
    private static Logger rootLogger;


    {
        rootLogger = new Logger("rootLogger");
        //rootLogger.addHandler(new ConsoleHandler());
    }

    public void readConfiguration() {
        String userDir = System.getProperty("user.home");
        File propertiesDir = new File(userDir, ".prop");
        if (!propertiesDir.exists()) propertiesDir.mkdir();
        propertiesFile = new File(propertiesDir, "logging.properties");

        Properties defaultSettings = new Properties();
        defaultSettings.put(".level", "info");
        defaultSettings.put("handlers", "");
        defaultSettings.put("loggers", "");

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

        defaultLevel = Level.stringToLevel(settings.getProperty(".level"));
        rootLogger.setLoggerLevel(Level.stringToLevel(settings.getProperty(".level")));
        String[] l = separateClasses(settings.getProperty("loggers"));
        HashMap<String, Logger> temp = new HashMap<>();
        for (String s : l) {
            s = s.trim();
            Logger logger = new Logger(s);
            logger.setLoggerLevel(Level.stringToLevel(settings.getProperty(s + ".level")));
            String[] h = separateClasses(settings.getProperty(s + ".handlers"));
            for (String s1 : h) {
                s1 = s1.trim();
                try {
                    System.out.println("Class = " + s1);
                    Class<?> clz = ClassLoader.getSystemClassLoader().loadClass(s1);
                    Handler hand = (Handler) clz.newInstance();
                    if (hand instanceof FileHandler) {
                        FileHandler filehandler = (FileHandler) hand;
                        filehandler.setFullPath(getStringProperty(s+"."+s1+".fullpath", System.getProperty("java.io.tmpdir")));
                        filehandler.setFormatter(getFormatterProperty(s+"."+s1+".formatter", new SimpleFormatter()));
                        logger.addHandler(filehandler);
                    }
                    if (hand instanceof ConsoleHandler) {
                        ConsoleHandler consoleHandler = (ConsoleHandler) hand;
                        consoleHandler.setFormatter(getFormatterProperty(s+"."+s1+".fullpath", new SimpleFormatter()));
                        logger.addHandler(consoleHandler);
                    }
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
            String[] f = separateClasses(settings.getProperty(s + ".filters"));
            for (String s1 : f) {
                s1 = s1.trim();
                try {
                    Class<?> clz = ClassLoader.getSystemClassLoader().loadClass(s1);
                    Filter hand = (Filter) clz.newInstance();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
            temp.put(s, logger);
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
        }
        return resultLogger;
    }

    public static Level getDefaultLevel() {
        return defaultLevel;
    }

    public static Logger getRootLogger() {
        return rootLogger;
    }

    private String[] separateClasses(String s) {
        return s.split(",");
    }

    String getStringProperty(String s, String defaultProp) {
        String prop = settings.getProperty(s);
        if (prop == null) return defaultProp;
        return prop.trim();
    }

    Formatter getFormatterProperty(String name, Formatter defaultValue) {
        String val = settings.getProperty(name);
            if (val != null) {
                Class<?> clz = null;
                try {
                    clz = ClassLoader.getSystemClassLoader().loadClass(val);
                    return (Formatter) clz.newInstance();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        return defaultValue;
    }

    public  void showLoggers() {
        for (Map.Entry<String, Logger> entry: loggers.entrySet())
            System.out.println(entry.getKey() + " = " + entry.getValue());
    }
}
