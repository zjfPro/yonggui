package com.cg.common.utils;

import java.sql.Timestamp;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * 对gson的封装类 加入了自定义转换功能 zjx
 */
public class GsonUtil {
	private Gson gson;
 

	public GsonUtil() {
		// 注意这里的Gson的构建方式为GsonBuilder,区别于test1中的Gson gson = new Gson();
		this.gson = new GsonBuilder()
		 		.excludeFieldsWithoutExposeAnnotation() //不导出实体中没有用@Expose注解的属性
				.enableComplexMapKeySerialization() // 支持Map的key为复杂对象的形式
				.serializeNulls().setDateFormat("yyyy-MM-dd")// 时间转化为特定格式
				.registerTypeAdapter(Timestamp.class,new TimestampTypeAdapter()).setDateFormat("yyyy-MM-dd HH:mm:ss")
				.setPrettyPrinting() // 对json结果格式化.
				// .setVersion(1.0)
				// //有的字段不是一开始就有的,会随着版本的升级添加进来,那么在进行序列化和返序列化的时候就会根据版本号来选择是否要序列化.
				// //@Since(版本号)能完美地实现这个功能.还的字段可能,随着版本的升级而删除,那么
				// //@Until(版本号)也能实现这个功能,GsonBuilder.setVersion(double)方法需要调用.
				.create();
		;
	}

 
	/**
	 * 将对象中带有JsonHide注解的属性设为空
	 * 
	 * @param list
	 * @return json
	 */
	public String toJson(Object obj) {
		try {
			return this.gson.toJson(obj);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public Gson getGson() {
		return gson;
	}

	public void setGson(Gson gson) {
		this.gson = gson;
	}

}
