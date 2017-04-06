package com.cg.common.utils;

import java.io.BufferedOutputStream;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 文件复制工具
 * 
 * @author 周家雄
 * 
 */
public class FileUtil {

	public static final void copyfile(File fromFile, File toFile) {
		FileInputStream fosfrom = null;
		FileOutputStream fosto = null;
		try {
			fosfrom = new FileInputStream(fromFile);
			fosto = new FileOutputStream(toFile);
			byte bt[] = new byte[1024];
			int c;
			while ((c = fosfrom.read(bt)) > 0) {
				fosto.write(bt, 0, c); // 将内容写到新文件当中
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			try {
				fosfrom.close();
			} catch (IOException e) {
			}
			try {
				fosto.close();
			} catch (IOException e) {
			}
		}
	}

	public static final void copyfile(byte[] fromFile, File toFile) {
		FileOutputStream fosto = null;
		BufferedOutputStream bos = null;
		try {
			fosto = new FileOutputStream(toFile);
			bos = new BufferedOutputStream(fosto);
			bos.write(fromFile, 0, fromFile.length); // 将内容写到新文件当中

		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			try {
				fosto.close();
				bos.close();
			} catch (IOException e) {
			}
		}
	}

}
