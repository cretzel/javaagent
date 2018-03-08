package de.nick.agent;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.lang.instrument.Instrumentation;
import java.security.ProtectionDomain;

/**
 * Created by cretzel on 07.03.18.
 */
public class JavaAgentJavaAssist {

    public static void premain(String agentArgs, Instrumentation inst) {
        inst.addTransformer(new ClassFileTransformer() {

            public byte[] transform(ClassLoader classLoader, String s, Class<?> aClass,
                                    ProtectionDomain protectionDomain, byte[] bytes)
                throws IllegalClassFormatException {

                if ("de/nick/agenttest/Test".equals(s)) {
                    try {
                        final ClassPool cp = ClassPool.getDefault();
                        final CtClass cc = cp.get("de.nick.agenttest.Test");
                        final CtMethod m = cc.getDeclaredMethod("doIt");

                        m.addLocalVariable("elapsedTime", CtClass.longType);
                        m.insertBefore("elapsedTime = System.currentTimeMillis();");
                        m.insertAfter("{elapsedTime = System.currentTimeMillis() - elapsedTime;"
                            + "System.out.println(\"Method Executed in ms: \" + elapsedTime);}");

                        byte[] byteCode = cc.toBytecode();
                        cc.detach();
                        return byteCode;
                    } catch (Exception ex) {
                        System.err.println("foobar" + ex);
                        ex.printStackTrace();
                    }
                }

                return null;
            }
        });
    }
}
