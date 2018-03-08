package de.nick.agent;

import net.bytebuddy.implementation.bind.annotation.AllArguments;
import net.bytebuddy.implementation.bind.annotation.Origin;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import net.bytebuddy.implementation.bind.annotation.SuperCall;

import java.lang.reflect.Method;
import java.util.concurrent.Callable;

/**
 * Created by cretzel on 08.03.18.
 */
public class TimingInterceptor {

    @RuntimeType
    public static Object intercept(@SuperCall Callable<?> callable, @AllArguments Object[] allArguments,
                                   @Origin Method method, @Origin Class clazz) throws Exception {
        long startTime = System.currentTimeMillis();
        Object response;
        try {
            response = callable.call();
        } catch (Exception e) {
            System.out.println("Exception occurred in method call: " + methodName(clazz, method, allArguments) + " Exception = " + e);
            throw e;
        } finally {
            System.out.println("Method " + methodName(clazz, method) + " executed in ms: " + (System.currentTimeMillis() - startTime));
        }
        return response;
    }

    private static String methodName(Class clazz, Method method) {
        return methodName(clazz, method, null);
    }

    private static String methodName(Class clazz, Method method, Object[] allArguments) {
        StringBuilder builder = new StringBuilder();
        builder.append(clazz.getName());
        builder.append(".");
        builder.append(method.getName());
        builder.append("(");
        for (int i = 0; i < method.getParameters().length; i++) {

            builder.append(method.getParameters()[i].getName());
            if (allArguments != null) {
                Object arg = allArguments[i];
                builder.append("=");
                builder.append(arg != null ? arg.toString() : "null");
            }

            if (i < method.getParameters().length - 1) {
                builder.append(", ");
            }
        }
        builder.append(")");
        return builder.toString();
    }
}
