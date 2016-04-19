package ru.medvedev.logging;

/**
 * Created by Сергей on 18.04.2016.
 */
public class SimpleFormatter implements Formatter {

    private String pattern;

    public String getPattern() {
        return pattern;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    @Override
    public String format(Record record) {
        if (pattern != null) {
            return format(record, pattern);
        }
        StringBuilder result = new StringBuilder();
        result.append(record.getNum());
        result.append(": " + record.getDate());
        result.append(" --- " + record.getLevel());
        result.append(": " + record.getMessage());
        return result.toString();
    }

    private String format(Record record, String format) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < format.length(); i++) {
            if (format.charAt(i) == '%') continue;
            if (format.charAt(i) == '1') {
                result.append(record.getDate());
            } else if (format.charAt(i) == '2') {
                result.append(record.getLevel());
            } else if (format.charAt(i) == '3') {
                result.append(record.getMessage());
            } else if (format.charAt(i) == '4') {
                result.append(record.getNum());
            } else if (format.charAt(i) == 'n'){
                result.append('\n');
            } else {
                result.append(format.charAt(i));
            }
        }
        return result.toString();
    }

}


//Example: %1 %2%n%4: %3

//%1 - time stamp
//%2 - level
//%3 - message
//%4 - number of record


