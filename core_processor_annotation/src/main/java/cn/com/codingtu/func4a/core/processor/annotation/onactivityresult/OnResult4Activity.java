package cn.com.codingtu.func4a.core.processor.annotation.onactivityresult;


import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.SOURCE)
public @interface OnResult4Activity {
    Class value();

    boolean isDeal() default true;
}
