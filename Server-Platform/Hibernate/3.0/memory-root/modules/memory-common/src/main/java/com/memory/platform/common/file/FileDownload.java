package com.memory.platform.common.file;

import java.io.File;
import java.io.FileInputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;

/**
 * 文件下载
 * @author norain
 *
 */
public class FileDownload {

	public void httpDownload(HttpServletRequest request, HttpServletResponse response,String filePath) throws Exception {
		java.io.FileInputStream fis = null;
		try {
			response.setContentType("text/html;charset=utf-8");
			request.setCharacterEncoding("UTF-8");
	
	//		String ctxPath = request.getSession().getServletContext().getRealPath("/")+ "\\" + "images\\";
			String downLoadPath = filePath;
			String fileName = filePath.split("/")[filePath.split("/").length-1];
			System.out.println(downLoadPath);
			
			fis =  new FileInputStream(downLoadPath);
			
			long fileLength = new File(downLoadPath).length();
			
			
			response.setContentType("application/x-msdownload;");
			response.setHeader("Content-disposition", "attachment; filename="
					+ new String(fileName.getBytes("utf-8"), "ISO8859-1"));
			response.setHeader("Content-Length", String.valueOf(fileLength));
			
			IOUtils.copy(fis, response.getOutputStream());
			
			response.flushBuffer();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (fis != null)
				fis.close();
		}
	}
}
