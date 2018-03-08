package de.nick.agent;

import net.bytebuddy.implementation.bind.annotation.Origin;

import java.lang.reflect.Method;

public class PrintingInterceptor {

    public static void intercept(@Origin Method m) {
        System.out.println("INTERCEPTED " + m.getName());
    }

}