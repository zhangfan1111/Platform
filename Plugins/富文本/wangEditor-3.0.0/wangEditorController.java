package com.utils.wangeditor;

import java.io.File;
import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.memory.platform.common.util.DateUtil;
import com.utils.ImageHelper;

@Controller
@RequestMapping(value = "/whcd/manage/wangEditor")
public class wangEditorController {
	
	private static final Logger logger = LoggerFactory.getLogger(wangEditorController.class);
	
	/**
	 * @title wangediter3.0版本上传
	 * @author HeKunLin 2017年6月10日 下午7:41:28
	 * @param request
	 * @param response
	 * @param files
	 * @return
	 */
	@RequestMapping(value="/wangEditorUpload",method = {RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	public Map<String, Object> wangEditorUpload(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(required=true,value="file") MultipartFile files){
		Map<String, Object> map = new HashMap<>();
		try {
			MultipartFile file = files;
			response.setContentType("textml;charset=UTF-8");
	        request.setCharacterEncoding("UTF-8");
	        
	        long filesize = file.getSize();
			if(filesize/1024/1024 > 5){
				logger.info("图片不能大于5M,上传失败");
			}else if(filesize/1024 < 5){
				logger.info("图片不能小于5k,上传失败");
			}
			String fileName = UUID.randomUUID().toString().replaceAll("-", "") + "." + FilenameUtils.getExtension(file.getOriginalFilename());
		
			Date now = new Date();
			String datefolder = "/" + DateUtil.dateToString(now, "yyyy") + DateUtil.dateToString(now, "MM") + DateUtil.dateToString(now, "dd");		// 日期命名的文件夹
			String webParentPath = new File(request.getSession().getServletContext().getRealPath("/")).getParent().replace("\\", "/");		// 当前WEB环境的上层目录
			
			String realPath = webParentPath + "/DC_editer" + datefolder + "/" + fileName;		// 文件压缩到服务器的真实路径
			String IP = "http://" + request.getLocalAddr() + ":" + request.getLocalPort();
			String filePath = IP + "/DC_editer" + datefolder + "/" + fileName;
			
			// System.out.println(realPath);
			// System.out.println(filePath);
			
			File create = new File(realPath);		//文件不存在创建
			//判断目标文件所在的目录是否存在  
	        if(!create.getParentFile().exists()) {  
	            //如果目标文件所在的目录不存在，则创建父目录  
	            if(!create.getParentFile().mkdirs()) {
	                System.out.println("创建目标文件所在目录失败！");  
	                logger.info("创建目标文件所在目录失败！");
	            }
	        }
			
			CommonsMultipartFile cf= (CommonsMultipartFile)file;
	        DiskFileItem fi = (DiskFileItem)cf.getFileItem();
	        File f = fi.getStoreLocation();
			boolean upload =  ImageHelper.scaleImage(f, realPath, 0.8 , "jpg");
			if(!upload){
				logger.info("图片压缩失败");
			}
			logger.info(filePath);
			map.put("errno", 0);
			map.put("data", filePath);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("error on deploy process, because of file input stream", e);
			logger.info("系统异常");
		}
		return map;
	}
	
}
