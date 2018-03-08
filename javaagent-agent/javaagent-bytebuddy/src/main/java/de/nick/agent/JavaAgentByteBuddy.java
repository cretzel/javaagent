package de.nick.agent;

import net.bytebuddy.agent.builder.AgentBuilder;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.matcher.ElementMatchers;

import java.lang.instrument.Instrumentation;

/**
 * Created by cretzel on 07.03.18.
 */
public class JavaAgentByteBuddy {

    public static void premain(String agentArgs, Instrumentation inst) {

        new AgentBuilder.Default()
            .with(new ErrorLoggingListener())
            .type(ElementMatchers.named("de.nick.agenttest.Test"))
            .transform((builder, type, classloader, javaModule) -> builder
                .method(ElementMatchers.nameContains("doIt"))
                .intercept(
//                  MethodDelegation.to(PrintingInterceptor.class).andThen(SuperMethodCall.INSTANCE)
                    MethodDelegation.to(TimingInterceptor.class)
                ))
            .installOn(inst);

    }

}
