package com.wdy.common.utils;

/**
 * Created by hdy on 2018/4/17.
 */
public class Logs {
    public static void printHr(Object msg) {
        System.out.println("===================println=======================================================================================================================================================");
        System.out.println("\u001b[48;32mprintln: " + msg + "\u001b[0m");
        System.out.println("===================println=======================================================================================================================================================");
    }
}
