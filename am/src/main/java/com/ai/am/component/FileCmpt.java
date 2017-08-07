package com.ai.am.component;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
@Lazy
public class FileCmpt {

	@Value("${app.ftp.path}")
	private String ftpPath;


	/**
	 * 
	 * @param fileName
	 *            文件名称
	 * @return 删除结果，成功true，失败false
	 * @throws Exception
	 *             操作失败信息
	 */
	public boolean removeFile(String fileName) throws Exception {
		// 文件删除结果标志
		boolean flag = true;
		// 文件路径
		String path = ftpPath + File.separator + fileName;
		// 要删除的文件
		File file = new File(path);
		if (!file.exists()) {
			throw new Exception("指定文件不存在！");
		}
		// 删除文件
		flag = file.delete();
		return flag;
	}

	/**
	 * 文件上传
	 * 
	 * @param file
	 * @param fileName
	 */
	public void uploadFile(MultipartFile file, String fileName) {
		// 如果名称不为“”,说明该文件存在，否则说明该文件不存在
		if (fileName.trim() != "") {
			// 重命名上传后的文件名
			String fileNameNew = fileName + new SimpleDateFormat("yyyyMMddHH24mmss").format(new Date());

			System.out.println("ftpPath" +ftpPath);
			System.out.println("ftpPath" + new File(ftpPath).exists());
			// 定义上传路径
			String path = ftpPath + File.separator + fileName;
			
			File localFile = new File(path);
	
			try {
				file.transferTo(localFile);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 获取下载文件
	 * 
	 * @param name
	 *            文件名
	 * @return 下载的文件
	 * @throws Exception
	 *             文件不存在异常
	 */
	public ResponseEntity<byte[]> downloadFile(String name) throws Exception {
		// 文件名，防止乱码
		String fileName = new String(name.getBytes("utf-8"), "utf-8");
		// 下载的后的文件名称
		String fileNameNew = "";
		if (fileName.indexOf("_") != -1) {
			fileNameNew = fileName.substring(0, fileName.indexOf("_"));
		} else {
			fileNameNew = fileName;
		}
		// 文件路径
		System.out.println("ftpPath" + new File(ftpPath).exists());
		String path = ftpPath + File.separator + fileName;

		File file = new File(path);
		if (!file.exists()) {
			throw new Exception("指定文件不存在！");
		}
		// 设置头信息
		HttpHeaders headers = new HttpHeaders();
		headers.setContentDispositionFormData("attachment", new String(fileNameNew.getBytes("gb2312"), "iso8859-1"));
		headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
		// 获得文件byte数组
		byte[] content = fileToByteArray(file);
		return new ResponseEntity<byte[]>(content, headers, HttpStatus.CREATED);
	}

	/**
	 * 根据要下载的文件名找到文件并转成byte数组
	 * 
	 * @param name
	 *            文件名
	 * @return 转换后的结果
	 * @throws Exception
	 *             文件不存在，溢出
	 */
	public byte[] fileToByteArray(File file) throws Exception {
		// 获得输入和输出流
		FileInputStream stream = null;
		ByteArrayOutputStream out = null;
		try {
			stream = new FileInputStream(file);
			out = new ByteArrayOutputStream(10000);
			byte[] content = new byte[10000];
			int length = 0;
			while ((length = stream.read(content)) != -1) {
				out.write(content, 0, length);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			stream.close();
			out.close();
		}
		return out.toByteArray();
	}

}
