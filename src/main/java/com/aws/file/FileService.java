package com.aws.file;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.aws.s3.service.S3Service;

@Service
public class FileService {

	@Value("${aws.s3.bucket.name}")
	private String bucketName;

	@Value("${aws.s3.base.url}")
	private String s3BaseUrl;

	@Autowired
	AmazonS3 s3Client;

	public static final Logger logger = LoggerFactory.getLogger(FileService.class);
	private static final String FOLDER_NAME = "test";

	public Map<String, String> uploadFileInS3(MultipartFile multipartFile) throws IOException {
		logger.info("Uploading file with name '{}' in S3.", multipartFile.getOriginalFilename());

		File file = new File(multipartFile.getOriginalFilename());
		try {
			file.createNewFile();
			FileOutputStream fos = new FileOutputStream(file);
			fos.write(multipartFile.getBytes());
			fos.close();
		} catch (IOException e) {
			logger.info("Exception occured. Exception : {}", e.getMessage());
			throw e;
		}

		S3Service.createFolder(s3Client, bucketName, FOLDER_NAME);
		S3Service.uploadFileInS3(s3Client, bucketName, FOLDER_NAME, file);

		Map<String, String> fileLocation = new HashMap<>();
		String filePath = s3BaseUrl.concat(bucketName).concat("/").concat(FOLDER_NAME).concat("/")
				.concat(file.getName());
		fileLocation.put("file", filePath);

		file.delete();

		return fileLocation;
	}
	
	public void deleteFileInS3(String fileName) {
		logger.info("Deleting file with name '{}' in S3", fileName);
		S3Service.deleteFileInS3(s3Client, bucketName, FOLDER_NAME, fileName);
	}
}
