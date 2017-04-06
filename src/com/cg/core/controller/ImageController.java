package com.cg.core.controller;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.util.Properties;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.cg.common.utils.ImageCompressUtil;

@Controller
@RequestMapping("/image")
public class ImageController {
	private static final String htmlPath = "/image/getimg.app?url=";

	/** 将本地连接转成网络连接 */
	public static String pathForUrl(String path) {
		return htmlPath + path.replaceAll("\\\\", "/");
	}

	/** 将网络连接转成本地连接 */
	public static String urlForPath(String path) {
		if (path.length() > htmlPath.length()) {
			return path.substring(htmlPath.length());
		} else {
			return path;
		}
	}

	/** 重命名 */
	public static String rename(String oldname) {
		return System.currentTimeMillis()
				+ oldname.substring(oldname.lastIndexOf("."));
	}

	/**
	 * 限制4M文件
	 */
	public static boolean checkFileOutSize(File file) {
		return file.length() > 4194304;
	}

	/**
	 * 限制4M文件
	 */
	public static boolean checkFileOutSize(MultipartFile[] files) {
		long sumSize = 0;
		for (MultipartFile file : files) {
			sumSize += file.getSize();
		}
		return sumSize > 4194304;
	}

	/**
	 * 保存上传文件，并将其相对路径返回
	 * 
	 * @param session
	 * @param logo
	 *            上传文件
	 * @return
	 * @throws IOException
	 */
	public static String saveFile(HttpSession session, MultipartFile logo,
			String path, String completionUrl) throws IOException {
		if (!logo.isEmpty()) {
			try {
				String filepath = filePath(session, path);
				File file = new File(filepath);
				if (file.exists()) {
					file.delete();
				}
				logo.transferTo(file);

				if (path.indexOf(".png") >= 0) {
					String newFileType = filepath.substring(filepath
							.lastIndexOf("."));
					String newFilePath = filepath.substring(0,
							filepath.lastIndexOf("."));
					// 创建预览图
					ImageCompressUtil imageCompressUtil = new ImageCompressUtil(
							filepath, newFilePath + "_logo" + newFileType);
					imageCompressUtil.resizeFix(200, 200);
				}
			} catch (IllegalStateException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return completionUrl + path;
		}
		return "";
	}

	/**
	 * 保存上传文件，并将其相对路径返回
	 * 
	 * @param session
	 * @param logo
	 *            上传文件
	 * @return
	 * @throws IOException
	 */
	public static String saveFile(HttpSession session, MultipartFile logo,
			String path) throws IOException {
		if (!logo.isEmpty()) {
			try {
				String filepath = filePath(session, path);
				File file = new File(filepath);
				if (file.exists()) {
					file.delete();
				}
				logo.transferTo(file);

				String newFileType = filepath.substring(filepath
						.lastIndexOf("."));
				String newFilePath = filepath.substring(0,
						filepath.lastIndexOf("."));

				// 创建预览图
				ImageCompressUtil imageCompressUtil = new ImageCompressUtil(
						filepath, newFilePath + "_logo" + newFileType);
				imageCompressUtil.resizeFix(200, 200);
			} catch (IllegalStateException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return path;
		}
		return "";
	}

	public static String filePath(HttpSession session, String filename)
			throws Exception, IOException {

		if (filename.indexOf(".") > 0) {
			filename = filename.substring(0, filename.lastIndexOf("."))
					+ ".png";
		}
		int code = '/';
		if (filename.length() > 0 && filename.toCharArray()[0] == code) {
			// 以/开头 自动跳过
			filename = filename.substring(1);
		}
		String[] paths = filename.split("/");
		StringBuffer dirPath = new StringBuffer();
		dirPath.append(session.getServletContext().getRealPath("/"));
		dirPath.append(System.getProperty("file.separator"));
		dirPath.append("WEB-INF");
		dirPath.append(System.getProperty("file.separator"));
		dirPath.append("classes");
		dirPath.append(System.getProperty("file.separator"));
		dirPath.append("conf");
		dirPath.append(System.getProperty("file.separator"));
		dirPath.append("path.properties");
		Properties pps = new Properties();
		pps.load(new FileInputStream(dirPath.toString()));

		File dir = new File(pps.getProperty("path"));
		if (dir.exists() == false) {
			dir.mkdirs();
		}

		if (paths.length > 0) {

			dirPath = new StringBuffer(pps.getProperty("path"));

			for (int i = 0; i < paths.length - 1; i++) {
				if (paths[i].length() > 0) {
					dirPath.append(System.getProperty("file.separator"));
					dirPath.append(paths[i]);
				}
			}

			dir = new File(dirPath.toString());
			if (dir.exists() == false) {
				dir.mkdirs();
			}
			dirPath.append(System.getProperty("file.separator"));
			dirPath.append(paths[paths.length - 1]);
		}

		return dirPath.toString();
	}

	public static String filePathReservedSuffix(HttpSession session,
			String filename) throws Exception, IOException {

		String[] paths = filename.split("/");
		StringBuffer dirPath = new StringBuffer();
		dirPath.append(session.getServletContext().getRealPath("/"));
		dirPath.append(System.getProperty("file.separator"));
		dirPath.append("WEB-INF");
		dirPath.append(System.getProperty("file.separator"));
		dirPath.append("classes");
		dirPath.append(System.getProperty("file.separator"));
		dirPath.append("conf");
		dirPath.append(System.getProperty("file.separator"));
		dirPath.append("path.properties");
		Properties pps = new Properties();
		pps.load(new FileInputStream(dirPath.toString()));

		File dir = new File(pps.getProperty("path"));
		if (dir.exists() == false) {
			dir.mkdirs();
		}

		if (paths.length > 0) {

			dirPath = new StringBuffer(pps.getProperty("path"));

			for (int i = 0; i < paths.length - 1; i++) {
				if (paths[i].length() > 0) {
					dirPath.append(System.getProperty("file.separator"));
					dirPath.append(paths[i]);
				}
			}

			dir = new File(dirPath.toString());
			if (dir.exists() == false) {
				dir.mkdirs();
			}
			dirPath.append(System.getProperty("file.separator"));
			dirPath.append(paths[paths.length - 1]);
		}

		return dirPath.toString();
	}


	@RequestMapping("getimg.app")
	@ResponseBody
	public void getimg(HttpSession session, HttpServletResponse resp, String url)
			throws IOException {
		resp.setContentType("image/jpeg");
		String path = null;
		try {
			path = filePath(session, url);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		if (path == null) {
			return;
		}
		OutputStream os = null;
		try {
			byte[] buffer = null;
			File file = new File(path);
			FileInputStream fis = null;
			try {
				fis = new FileInputStream(file);
			} catch (Exception e) {
				return;
			}
			ByteArrayOutputStream bos = new ByteArrayOutputStream(1000);
			byte[] b = new byte[1000];
			int n;
			while ((n = fis.read(b)) != -1) {
				bos.write(b, 0, n);
			}
			try {
				fis.close();
				bos.close();
			} catch (Exception e) {
			}
			buffer = bos.toByteArray();
			os = resp.getOutputStream();
			if (buffer != null) {
				os.write(buffer);
				os.flush();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (os != null) {
				os.close();
			}
		}
	}

	/**
	 * 富文本编辑器回显专用请求，会自动指定到指定路径 name=图片名字
	 */
	@RequestMapping("getUeditorImg/{name:.*}")
	@ResponseBody
	public void getUeditorImg(HttpSession session, HttpServletResponse resp,
			@PathVariable String name) throws IOException {
		resp.setContentType("image/jpeg");
		String path = null;
		try {
			path = filePathReservedSuffix(session,
					"ueditor/image/getUeditorImg/" + name);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		if (path == null) {
			return;
		}

		OutputStream os = null;
		try {
			byte[] buffer = null;
			File file = new File(path);
			FileInputStream fis = null;
			try {
				fis = new FileInputStream(file);
			} catch (Exception e) {
				return;
			}
			ByteArrayOutputStream bos = new ByteArrayOutputStream(1000);
			byte[] b = new byte[1000];
			int n;
			while ((n = fis.read(b)) != -1) {
				bos.write(b, 0, n);
			}
			try {
				fis.close();
				bos.close();
			} catch (Exception e) {
			}
			buffer = bos.toByteArray();
			os = resp.getOutputStream();
			if (buffer != null) {
				os.write(buffer);
				os.flush();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (os != null) {
				os.close();
			}
		}
	}

	
	/**
	 * 删除对象关联的所有图片
	 * 
	 * @param obj
	 */
	public static void deleteObjImage(Object obj, HttpSession session) {
		Class cls = obj.getClass();
		Field[] fields = cls.getDeclaredFields();
		for (int i = 0; i < fields.length; i++) {
			Field f = fields[i];
			boolean baseAccessible = f.isAccessible();
			f.setAccessible(true);
			try {
				Object value = f.get(obj);
				if (value instanceof String) {
					String strVal = (String) value;
					if (strVal.indexOf(htmlPath) > -1) {
						if (strVal.substring(0, 1).equals("[")) {
							strVal = strVal.substring(1, strVal.length() - 1);
						}
						String[] imagePaths = strVal.split(",");
						for (String imagePath : imagePaths) {
							// 证明有图片路径 删除图片
							File image = new File(ImageController.filePath(
									session,
									ImageController.urlForPath(imagePath)));
							if (image != null && image.exists()) {
								System.out.println("删除文件:" + image.getPath());
								image.delete();
							}
						}

					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			f.setAccessible(baseAccessible);

		}
	}
}