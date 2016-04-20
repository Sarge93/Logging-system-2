package ru.medvedev.test;

import ru.medvedev.logging.*;
import ru.medvedev.logging.Logger;



/**
 * Created by Сергей on 12.04.2016.
 */
public class Test {
    public static void main(String[] args) {
        Logger logger = Logger.createLogger("SuperLogger");
        ConsoleHandler consoleHandler = new ConsoleHandler();
        consoleHandler.setFormatter(new XMLFormatter());
       // ((SimpleFormatter)consoleHandler.getFormatter()).setPattern("%1 %2%n%4: %3");
        logger.addHandler(consoleHandler);
        logger.info("123");
    }
}
