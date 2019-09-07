package cn.com.codingtu.func4a.core.processor.annotation.onclick;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.SOURCE)
public @interface ClickView {
    int[] value();
}
