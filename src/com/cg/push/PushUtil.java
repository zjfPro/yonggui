package com.cg.push;

import java.io.IOException;
import java.util.HashMap;

import com.cg.common.utils.HTTPUtil;

public class PushUtil {
	private static final String path="http://118.123.7.243:8080/mdcpbPushServer";
	public static String pushNotice(String id,String sessionId) {
		//推送公告
		HashMap<String, Object> params = new HashMap<String, Object>();
		// ****************************************************************************************************
		params.put("id", id);
		try {
			return HTTPUtil.sendPOSTRequest(
					path+"/push/notice",
					params, "utf-8", sessionId);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static String pushTask(String id,String sessionId) {
		//推送公告
		HashMap<String, Object> params = new HashMap<String, Object>();
		// ****************************************************************************************************
		params.put("id", id);
		try {
			return HTTPUtil.sendPOSTRequest(
					path+"/push/task",
					params, "utf-8", sessionId);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	
}
