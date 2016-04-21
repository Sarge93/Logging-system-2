package ru.medvedev.test;

import ru.medvedev.logging.*;
import ru.medvedev.logging.Logger;



/**
 * Created by Сергей on 12.04.2016.
 */
public class Test {
    public static void main(String[] args) {
        LogManager.getLogManager().readConfiguration();
        LogManager.getLogManager().showLoggers();
    }
}
