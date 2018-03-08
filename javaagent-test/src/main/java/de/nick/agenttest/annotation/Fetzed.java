package de.nick.agenttest.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by wied013 on 08.03.18.
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface Fetzed {
    String dude();
}
