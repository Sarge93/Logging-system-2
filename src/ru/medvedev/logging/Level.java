package ru.medvedev.logging;

/**
 * Created by Сергей on 12.04.2016.
 */
public enum Level {
    debug1, debug2, debug3, info, warning, severe;

    public static Level stringToLevel(String s) {
        if (s.equals("debug1")) return Level.debug1;
        if (s.equals("debug2")) return Level.debug2;
        if (s.equals("debug3")) return Level.debug3;
        if (s.equals("info")) return Level.info;
        if (s.equals("warning")) return Level.warning;
        if (s.equals("severe")) return Level.severe;
        throw new IllegalArgumentException("Неверный формат уровня сообщения");
    }
}
