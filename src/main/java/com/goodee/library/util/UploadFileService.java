package com.goodee.library.util;

import java.io.File;
import java.util.UUID;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class UploadFileService {

	public String upload(MultipartFile file) {
		String result ="";
		//1. 파일을 서버 저장
		String fileOriName = file.getOriginalFilename();
		String fileExtension = fileOriName.substring(fileOriName.lastIndexOf("."),fileOriName.length()); //확장자
		String uploadDir = "/var/lib/tomcat9/webapps/upload/";
		
		UUID uuid = UUID.randomUUID();
		String uniqueName = uuid.toString().replaceAll("-", "");
		
		File saveFile = new File(uploadDir+uniqueName+fileExtension);
		
		if(!saveFile.exists()) {
			saveFile.mkdirs();
		}
		
		try {
			file.transferTo(saveFile);
			result = uniqueName+fileExtension;
		} catch (Exception e) {
			
			e.printStackTrace();
		}
	
		return result;
	}
}
