package com.cg.common.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class CopyFileUtil {  
	  
    private static String MESSAGE = "";  
  
    /** 
     * 复制单个文件 
     * @return 如果复制成功返回true，否则返回false 
     * @throws IOException 
     */  
    public static boolean copyFile(InputStream in, String destFileName) throws IOException {  
   
        // 判断目标文件是否存在  
        File destFile = new File(destFileName);  
        if (!destFile.exists()) {  
            // 如果目标文件所在目录不存在，则创建目录  
            if (!destFile.getParentFile().exists()) {  
                // 目标文件所在目录不存在  
                if (!destFile.getParentFile().mkdirs()) {  
                    // 复制文件失败：创建目标文件所在目录失败  
                    return false;  
                }  
            }  
            destFile.createNewFile();
        }  
        // 复制文件  
        int byteread = 0; // 读取的字节数  
        OutputStream out = null;  
        try {  
            out = new FileOutputStream(destFile);  
            byte[] buffer = new byte[1024];  
            while ((byteread = in.read(buffer)) != -1) {  
                out.write(buffer, 0, byteread);  
            }  
            out.close();
            return true;  
        } catch (Exception e) {  
        	e.printStackTrace();
            return false;  
        } finally {  
            try {  
                if (out != null)  
                    out.close();  
            } catch (IOException e) {  
                e.printStackTrace();  
            }  
        }  
    }  
  
    
 
 
    
    
    
}  