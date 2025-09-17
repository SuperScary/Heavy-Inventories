package net.superscary.heavyinventories.api.util;

public class Functions {

    /**
     * This is fairly pointless since ternary operators are already available.
     * However, it looks cleaner than spamming ternary operators everywhere.
     * @param conditionTrue The value to return if the condition is true.
     * @param conditionFalse The value to return if the condition is false.
     * @param condition The condition to check.
     * @return The value of either conditionTrue or conditionFalse.
     * @param <T> The type of the values. Both must be of the same type.
     */
    public static <T> T either(T conditionTrue, T conditionFalse, boolean condition) {
        return condition ? conditionTrue : conditionFalse;
    }

    public static <T> T either(boolean condition, T valueTrue, T valueFalse) {
        return either(valueTrue, valueFalse, condition);
    }

}
