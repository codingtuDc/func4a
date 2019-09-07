package cn.com.codingtu.func4a.core.processor.annotation.activity;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.SOURCE)
public @interface Launcher {

    Class[] paramClasses() default {};

    String[] paramNames() default {};

}
