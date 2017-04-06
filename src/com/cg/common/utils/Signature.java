package com.cg.common.utils;


import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

public class Signature {
	/**
	 * 签名生成算法 DES
	 * 
	 * @param HashMap
	 *            <String,String> params 请求参数集，所有参数必须已转换为字符串类型
	 * @return 签名
	 * @throws IOException
	 */
	public static String getSignatureByDES(HashMap<String, Object> params)
			throws IOException {
		// 先将参数以其参数名的字典序升序进行排序
		Map<String, Object> sortedParams = new TreeMap<String, Object>(params);
		Set<Entry<String, Object>> entrys = sortedParams.entrySet();
		// 遍历排序后的字典，将所有参数按"key=value"格式拼接在一起
		StringBuilder basestring = new StringBuilder();
		basestring.append("{");
		for (Entry<String, Object> param : entrys) {
			basestring.append(param.getKey()).append("=")
					.append(param.getValue());
			basestring.append(",");
		}
		basestring= basestring.deleteCharAt(basestring.length()-1);
		basestring.append("}");
		return CustomMixedEncryptionUtil.createSign(basestring.toString().replaceAll(" ", "") );
	}

}
