package ru.medvedev.logging;

/**
 * Created by Сергей on 12.04.2016.
 */
public interface Filter {
    public boolean isLoggable(String log);
}
