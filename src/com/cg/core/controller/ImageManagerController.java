package com.cg.core.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("manager/images")
public class ImageManagerController {

	@RequestMapping("/load")
	public String loadImages(HttpServletRequest req) {
		List<String> all = loadAllImges(new ArrayList<String>(),
				new File(req.getSession().getServletContext().getRealPath("/") + "upload"));
		req.setAttribute("images", all);
		return "imagesManager/images";
	}

	/**
	 * 获取所有的图片的相对路径
	 * 
	 * @param all
	 * @param file
	 * @return 所有图片的相对路径
	 */
	private List<String> loadAllImges(List<String> all, File file) {
		if (file.isDirectory()) {
			for (File nextFile : file.listFiles()) {
				loadAllImges(all, nextFile);
			}
		} else {
			String path = file.getPath();
			all.add(path.substring(path.indexOf("upload"), path.length()).replaceAll("\\\\", "/"));
		}
		return all;
	}

	@RequestMapping("/delete")
	@ResponseBody
	public String delete(HttpServletRequest req) throws Exception {
		String[] urls = req.getParameterValues("urls");
		HttpSession session = req.getSession();
		for (String url : urls) {
			try {
				new File(filePath(session, url)).delete();
			} catch (Exception e) {
				throw e;
			}
		}
		return "0";
	}

	private String filePath(HttpSession session, String url) {
		return session.getServletContext().getRealPath("/") + url.substring(url.indexOf("upload"), url.length());
	}

	@RequestMapping("/download")
	public void download(HttpSession session, HttpServletResponse resp, String url) throws IOException {
		String path = filePath(session, url);
		File img = new File(path);
		resp.reset();
		resp.setHeader("Content-Disposition", "attachment; filename=" + img.getName());
		resp.setContentType("application/octet-stream; charset=utf-8");
		OutputStream os = resp.getOutputStream();
		try {
			os.write(fileToByteArray(img));
			os.flush();
		} catch (IOException e) {
			throw e;
		} finally {
			os.close();
		}
	}

	/**
	 * 将文件转成一个数组
	 * 
	 * @param file
	 *            需要转换的文件
	 * @return 转换后的byte数组
	 * @throws IOException
	 */
	private byte[] fileToByteArray(File file) throws IOException {
		byte[] buff = new byte[(int) file.length()];
		InputStream is = null;
		try {
			is = new FileInputStream(file);
			int offset = 0;
			int numRead = 0;
			while (offset < buff.length && (numRead = is.read(buff, offset, buff.length - offset)) >= 0) {
				offset += numRead;
			}
			return buff;
		} catch (IOException e) {
			throw e;
		} finally {
			if (is != null) {
				is.close();
			}
		}
	}
}