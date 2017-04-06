package com.cg.common.utils;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;
 
/**
 * HTTP请求相关
 * 
 * 
 * @author 周家雄
 * 
 */
public class HTTPUtil {

 
 
 
	/**
	 * 发送POST请求
	 * 
	 * @param path
	 *            完整路径
	 * @param params
	 *            参数集合 key,value
	 * @param encoding
	 *            编码格式
	 * @param SessionID
	 *            作为用户标识的sessionId 可为空
	 * @return 服务器返回得字符串
	 * @throws IOException
	 */
	public static String sendPOSTRequest(String path,
			Map<String, Object> params, String encoding, String SessionID)
			throws IOException {
		OutputStream out = null;
		StringBuffer sb = new StringBuffer();
		InputStream in = null;
		StringBuilder data = new StringBuilder();
		if (params != null && !params.isEmpty()) {
			for (Map.Entry<String, Object> entry : params.entrySet()) {
				data.append(entry.getKey()).append("=");
				data.append(URLEncoder.encode(entry.getValue().toString(),
						encoding));
				data.append("&");
			}
			data.deleteCharAt(data.length() - 1);
		}
		byte[] entity = data.toString().getBytes("utf-8");
		HttpURLConnection conn = null;
		try {
			conn = (HttpURLConnection) new URL(path).openConnection();

			conn.setConnectTimeout(5000);
			conn.setRequestMethod("POST");

			if (SessionID != null) {
				conn.setRequestProperty("cookie", SessionID);
			}
			conn.setDoOutput(true);// 允许对外输出数据
			conn.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded");
			conn.setRequestProperty("Content-Length",
					String.valueOf(entity.length));

			out = conn.getOutputStream();
			out.write(entity);

			if (conn.getResponseCode() == 200) {
				in = conn.getInputStream();
				BufferedInputStream bis = new BufferedInputStream(in);
				byte[] b = new byte[4096];
				int len = 0;
				while ((len = bis.read(b)) != -1) {
					sb.append((new String(b, 0, len)));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			out.close();
			in.close();
		}
		return sb.toString();
	}

  

}