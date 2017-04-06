package com.cg.core.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 使用GsonUtil转换时隐藏带有该注解的属性
 * @author zjx
 *
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface JsonHide {

}
