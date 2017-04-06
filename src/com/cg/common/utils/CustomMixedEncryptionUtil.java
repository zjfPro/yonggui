package com.cg.common.utils;

public class CustomMixedEncryptionUtil extends DesUtil{
	
	public static final String encrypt(String data, String key)
			throws Exception {
		return DesUtil.encryptFixed(MD5Util.string2MD5(data).substring(2), key)
				.replaceAll("\\W", "").substring(3);

	}

	public static final String createSign(String data) {
		String str = null;
		try {
			str = DesUtil
					.encryptFixed(MD5Util.string2MD5(data).substring(1),
							KeyUtil.sign).replaceAll("\\W", "").substring(3);

			if (str.length() > 11) {
				str = str.substring(0, 10);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return str;
	}

}
