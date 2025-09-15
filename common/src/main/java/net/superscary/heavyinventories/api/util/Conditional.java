package net.superscary.heavyinventories.api.util;

public class Conditional {

    public static <T> T check(boolean condition, T returnValue) {
        return check(condition, returnValue, null);
    }

    public static <T> T check(boolean condition, T returnValue, String message) {
        if (condition) {
            return returnValue;
        } else {
            throw new IllegalArgumentException(message);
        }
    }

}
