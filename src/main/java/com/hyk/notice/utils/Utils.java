package com.hyk.notice.utils;

public class Utils {
    private Utils() {}

    public static String convertArgumentsIntoMessage(String[] args, int startArgumentsIndex) {
        StringBuilder message = new StringBuilder();
        for (int index = startArgumentsIndex; index < args.length; index++) {
            message.append(args[index] + " ");
        }
        message.deleteCharAt(message.lastIndexOf(" "));
        return message.toString();
    }
}
