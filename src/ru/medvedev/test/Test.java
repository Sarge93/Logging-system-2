package ru.medvedev.test;

import ru.medvedev.logging.*;
import ru.medvedev.logging.FileHandler;
import ru.medvedev.logging.Logger;
import java.io.IOException;

import java.util.logging.*;

/**
 * Created by Сергей on 12.04.2016.
 */
public class Test {
    public static void main(String[] args) {
        Logger logger = new Logger();
        String path = "Q://loglog/a.txt";

        try {
            FileHandler fileHandler = new FileHandler(path);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
