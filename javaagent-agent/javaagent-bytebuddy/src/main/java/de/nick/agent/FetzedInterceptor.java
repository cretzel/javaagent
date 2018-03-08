package de.nick.agent;

import net.bytebuddy.implementation.bind.annotation.AllArguments;
import net.bytebuddy.implementation.bind.annotation.Origin;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import net.bytebuddy.implementation.bind.annotation.SuperCall;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Callable;

/**
 * Created by cretzel on 08.03.18.
 */
public class FetzedInterceptor {

    @RuntimeType
    public static Object intercept(@SuperCall Callable<?> callable, @AllArguments Object[] allArguments,
                                   @Origin Method method, @Origin Class clazz) throws Exception {

        final List<Annotation> annotations = Arrays.asList(method.getAnnotations());
        final Optional<Annotation> fetzedAnnotation = annotations.stream().filter(a -> a.annotationType().getName().equals("de.nick.agenttest.annotation.Fetzed")).findAny();
        if (fetzedAnnotation.isPresent()) {
            final String dude = (String) fetzedAnnotation.get().annotationType().getDeclaredMethod("dude").invoke(fetzedAnnotation.get());
            System.out.println(method.getName() + " is fetzed by " + dude);
        }
        return callable.call();
    }

}
