package ru.medvedev.test;

import ru.medvedev.logging.*;
import ru.medvedev.logging.ConsoleHandler;
import ru.medvedev.logging.FileHandler;
import ru.medvedev.logging.Handler;
import ru.medvedev.logging.Level;
import ru.medvedev.logging.Logger;

import javax.mail.MessagingException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;


/**
 * Created by Сергей on 12.04.2016.
 */
public class Test {
    public static void main(String[] args) {
        LogManager.getLogManager().readConfiguration();
        LogManager.getLogManager().showLoggers();
        Logger loglog = Logger.createLogger("log1");
        System.out.println(loglog);
        loglog.severe("qazwsxedc");
    }
}
