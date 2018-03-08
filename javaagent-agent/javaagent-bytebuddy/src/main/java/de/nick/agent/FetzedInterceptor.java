package de.nick.agent;

import net.bytebuddy.implementation.bind.annotation.AllArguments;
import net.bytebuddy.implementation.bind.annotation.Origin;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import net.bytebuddy.implementation.bind.annotation.SuperCall;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
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
        final Optional<Annotation> fetzedAnnotationOpt = annotations.stream()
            .filter(a -> a.annotationType().getName().equals("de.nick.agenttest.annotation.Fetzed"))
            .findAny();
        if (fetzedAnnotationOpt.isPresent()) {
            final Annotation fetzedAnnotation = fetzedAnnotationOpt.get();
            final String dude = (String) fetzedAnnotation.annotationType().getDeclaredMethod("dude").invoke(fetzedAnnotation);
            System.out.println(method.getName() + " is fetzed by " + dude);
        }
        return callable.call();
    }

}
