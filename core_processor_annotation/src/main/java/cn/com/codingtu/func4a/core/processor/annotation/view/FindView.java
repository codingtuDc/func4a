package cn.com.codingtu.func4a.core.processor.annotation.view;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.SOURCE)
public @interface FindView {
    int value();
}
