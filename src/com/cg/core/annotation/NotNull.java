package com.cg.core.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 非空校验 
 * @author zjx
 *
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface NotNull {
	/**
	 * 错误提示信息
	 * @return
	 */
	public String msg() default "不能为空";
	/**
	 * 是否将错误信息返回客户端
	 * @return
	 */
	public boolean isReturn() default false;
}
