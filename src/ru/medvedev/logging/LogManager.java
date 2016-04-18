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
    private static boolean updateConfig = false;


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
//        String[] h = separateClasses(settings.getProperty("handlers"));
//        for (String s : h) {
//            s = s.trim();
//            try {
//                Class<?> clz = ClassLoader.getSystemClassLoader().loadClass(s);
//                clz.newInstance();
//            } catch (ClassNotFoundException e) {
//                e.printStackTrace();
//            } catch (InstantiationException e) {
//                e.printStackTrace();
//            } catch (IllegalAccessException e) {
//                e.printStackTrace();
//            }
//        }

        String[] l = separateClasses(settings.getProperty("loggers"));
        HashMap<String, Logger> temp = new HashMap<>();
        for (String s : l) {
            s = s.trim();
            Logger logger = new Logger(s);
            logger.setLoggerLevel(Level.stringToLevel(settings.getProperty(s + ".level")));
            String[] h = separateClasses(settings.getProperty(s + ".handlers"));
            for (String s1 : h) {
                try {
                    Class<?> clz = ClassLoader.getSystemClassLoader().loadClass(s1);
                    Handler hand = (Handler) clz.newInstance();
                    if (hand instanceof FileHandler) {
                        FileHandler filehandler = (FileHandler) hand;
                        filehandler.setFullPath(getStringProperty(s+"."+s1+".fullpath", System.getProperty("java.io.tmpdir")));
                        filehandler.setFormatter(getFormatterProperty(s+"."+s1+".fullpath", new SimpleFormatter()));
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
            if (updateConfig) {
                int numberOfLoggers = Integer.parseInt(settings.getProperty("loggers"));
                settings.put("loggers", Integer.valueOf(numberOfLoggers + 1).toString());
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

    private String[] separateClasses(String s) {
        return s.split(",");
    }

    String getStringProperty(String s, String defaultProp) {
        String prop = settings.getProperty(s);
        if (s == null) return defaultProp;
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

    public HashMap<String, Logger> getLoggers() {
        return loggers;
    }
}
