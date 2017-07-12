package com.ai.aiga.util;



import java.io.File;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.ai.aiga.component.FileCmpt;
import com.ai.aiga.util.spring.ApplicationContextUtil;
/**
 * 文件操作工具类
 * @author liuxx
 * @date 2017年5月9日23:31:15
 */
public final class FileUtil {
	
//	private final static String FILE_PATH = "d:";  //ftp://20.26.17.182/tm-web/tm-web-domain/servers/tm-web-cs-srv01/aiga3-file
//	private final static String FILE_PATH = "ftp://20.26.17.182/tm-web/tm-web-domain/servers/tm-web-cs-srv01/aiga3-file";

	
	/**
	 * 
	 * @param fileName 文件名称
	 * @return 删除结果，成功true，失败false
	 * @throws Exception 操作失败信息
	 */
	public static boolean removeFile(String fileName) throws Exception{
		
		FileCmpt fileCmpt = ApplicationContextUtil.getBean(FileCmpt.class);
		return fileCmpt.removeFile(fileName);
		
	}
	
	
	
	/**
	 * 文件上传
	 * @param file
	 * @param fileName
	 */
	public static void uploadFile(MultipartFile file , String fileName){
		FileCmpt fileCmpt = ApplicationContextUtil.getBean(FileCmpt.class);
		fileCmpt.uploadFile(file, fileName);
	}
	
	/**
	 * 获取下载文件
	 * @param name 文件名
	 * @return 下载的文件
	 * @throws Exception 文件不存在异常
	 */
	public static ResponseEntity<byte[]> downloadFile(String name) throws Exception{
		FileCmpt fileCmpt = ApplicationContextUtil.getBean(FileCmpt.class);
		return fileCmpt.downloadFile(name);
	}
	
	
	
	/**
	 * 将文件转成byte数组
	 * @param name 文件名
	 * @return 转换后的结果
	 * @throws Exception 文件不存在，溢出
	 */
	public static byte[] fileToByteArray(File file) throws Exception{
		FileCmpt fileCmpt = ApplicationContextUtil.getBean(FileCmpt.class);
		return fileCmpt.fileToByteArray(file);
	}
}
