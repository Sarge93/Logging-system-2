package ru.medvedev.test;

import ru.medvedev.logging.*;
import ru.medvedev.logging.ConsoleHandler;
import ru.medvedev.logging.FileHandler;
import ru.medvedev.logging.Handler;
import ru.medvedev.logging.Level;
import ru.medvedev.logging.Logger;
import java.io.IOException;

import java.util.logging.*;

/**
 * Created by Сергей on 12.04.2016.
 */
public class Test {
    public static void main(String[] args) {
        Logger logger = Logger.createLogger(Test.class.getName());

        int a = 3;
        int b = 4;
        int c = a + b;
        Handler handler = new ConsoleHandler();
        logger.addHandler(handler);
        logger.log(Level.info, "Sum of a and b is complete");
        try {
            logger.addHandler(new FileHandler("Q:/loglog/log.txt"));
        } catch (IOException e) {
        }
        logger.log(Level.info, "Some action");
        try {
            logger.addHandler(new HtmlHandler("Q:/loglog/log.htm"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        logger.log(Level.info, "Let the bodies hit the floor --- 1");
        logger.log(Level.info, "Let the bodies hit the floor --- 2");
        logger.log(Level.info, "Let the bodies hit the floor --- 3");

    }
}
