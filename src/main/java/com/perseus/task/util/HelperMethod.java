package com.perseus.task.util;

public final class HelperMethod {
    public static <E extends Enum<E>> E filterEnum(Class<E> clazz, String s) {
        for (var c : clazz.getEnumConstants()) {
            String each = c.name().replaceAll("_", " ");
            if(each.equalsIgnoreCase(s)) return c;
        }
        return null;
    }
}
