package ru.medvedev.logging;

import java.util.Date;

/**
 * Created by Сергей on 12.04.2016.
 */
public class Record {
    private Level level;
    private String message;
    private Date date;
    private int num;

    public Level getLevel() {
        return level;
    }

    public String getMessage() {
        return message;
    }

    public Date getDate() {
        return date;
    }

    public int getNum() {
        return num;
    }

    public Record(Level level, String message) {
        this.level = level;
        this.message = message;
    }
}
