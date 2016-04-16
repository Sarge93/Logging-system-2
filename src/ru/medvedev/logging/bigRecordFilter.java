package ru.medvedev.logging;

/**
 * Created by Сергей on 16.04.2016.
 */
public class bigRecordFilter implements Filter {
    @Override
    public boolean isLoggable(Record log) {
        if (log.getMessage().length() > 30) return false;
        return true;
    }
}
