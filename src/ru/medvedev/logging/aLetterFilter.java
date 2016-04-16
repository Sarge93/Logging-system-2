package ru.medvedev.logging;

/**
 * Created by Сергей on 16.04.2016.
 */
public class aLetterFilter implements Filter {

    @Override
    public boolean isLoggable(Record log) {
        for (int i = 0; i < log.getMessage().length(); i++) {
            if (Character.toLowerCase(log.getMessage().charAt(i)) == 'a') {
                return false;
            }
        }
        return true;
    }
}
