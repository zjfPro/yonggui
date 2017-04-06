package com.cg.common.utils;

import java.lang.reflect.Field;
import java.sql.Timestamp;
import java.util.Date;

import com.cg.core.annotation.NotNull;


/**
 * 初始化实体类的工具
 * @author 周家雄
 */
public class InitEntityUtil {

	
	public static String checkModel(Object obj) throws Exception {
		Field[] fields = obj.getClass().getDeclaredFields();
		boolean isReturn = false;
		String msg = null;
		for (Field field : fields) {
			boolean b = field.isAccessible();
			field.setAccessible(true);
			NotNull n = field.getAnnotation(NotNull.class);
			if (n != null) {
				try {
					Object value = field.get(obj);
					if (value == null || value.toString().length() < 1) {
						msg = n.msg().toString();
						isReturn = n.isReturn();
						throw new NullPointerException(msg);
					}
				} catch (Exception e) {
					throw new NullPointerException(msg);
				}
			}
			field.setAccessible(b);
		}

			return msg;
	}
	
	/**
	 * 读取类所有属性 将其基本属性进行初始化赋值(不会覆盖已有的值,仅针值为null的属性进行操作)
	 */
	public static final <T> T initObject(Object obj)
			throws IllegalArgumentException, IllegalAccessException {

		Field[] df = obj.getClass().getDeclaredFields();
		for (Field field : df) {
			boolean b = field.isAccessible();
			field.setAccessible(true);
			if (field.get(obj) == null) {
				if (field.getType().equals(String.class)) {
					field.set(obj, "");
				} else if (field.getType().equals(Integer.class)
						|| field.getType().equals(int.class)) {
					field.set(obj, 0);
				} else if (field.getType().equals(Double.class)
						|| field.getType().equals(double.class)) {
					field.set(obj, 0.0);
				} else if (field.getType().equals(Short.class)
						|| field.getType().equals(short.class)) {
					field.set(obj, (short) 0);
				} else if (field.getType().equals(Float.class)
						|| field.getType().equals(float.class)) {
					field.set(obj, (float) 0);
				} else if (field.getType().equals(Long.class)
						|| field.getType().equals(long.class)) {
					field.set(obj, (long) 0);
				} else if (field.getType().equals(Boolean.class)
						|| field.getType().equals(boolean.class)) {
					field.set(obj, false);
				} else if (field.getType().equals(Timestamp.class)) {
					field.set(obj, new Timestamp(System.currentTimeMillis()));
				} else if (field.getType().equals(Date.class)) {
					field.set(obj, new Date());
				} else if (field.getType().equals(java.sql.Date.class)) {
					field.set(obj,
							new java.sql.Date(System.currentTimeMillis()));
				}
			}
			field.setAccessible(b);
		}
		return (T) obj;
	}

	
	/**
	 * 读取2个obj的所有属性 将属性的值转成字符串,然后进行对比 如果所有属性一致则返回true
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public static final boolean ObjectContrast(Object obj1,Object obj2)
			throws Exception {
		
		//2个对象必须是一个类型的
		if (!obj1.getClass().equals(obj2.getClass())) {
			return false;
		}
		
		Field[] df = obj1.getClass().getDeclaredFields();
		for (Field field : df) {
			boolean b = field.isAccessible();
			field.setAccessible(true);
			 if (field.get(obj1).toString().equals(field.get(obj2).toString())==false) {
				return false;
			}
			 field.setAccessible(b);
		}
		return true;
	}
	
	
	
}
