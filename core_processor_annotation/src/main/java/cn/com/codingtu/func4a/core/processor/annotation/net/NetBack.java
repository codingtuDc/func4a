package cn.com.codingtu.func4a.core.processor.annotation.net;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.SOURCE)
public @interface NetBack {

    Class value() default Void.class;

}
