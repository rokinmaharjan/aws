package com.aws.s3.service;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;

public class S3Service {
	private static final String SUFFIX = "/";
	
	public static void createFolder(AmazonS3 s3Client, String bucketName, String folderName) {
		// create meta-data for your folder and set content-length to 0
		ObjectMetadata metadata = new ObjectMetadata();
		metadata.setContentLength(0);
		// create empty content
		InputStream emptyContent = new ByteArrayInputStream(new byte[0]);
		// create a PutObjectRequest passing the folder name suffixed by /
		PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, folderName + SUFFIX, emptyContent,
				metadata);

		s3Client.putObject(putObjectRequest);
	}

//	@Async
	public static void uploadFileInS3(AmazonS3 s3Client, String bucketName, String folderName, File file) {
		s3Client.putObject(new PutObjectRequest(bucketName, folderName + SUFFIX + file.getName(), file)
				.withCannedAcl(CannedAccessControlList.PublicRead));
	}

	public static void deleteFileInS3(AmazonS3 s3Client, String bucketName, String folderName, String fileName) {
		s3Client.deleteObject(bucketName, folderName.concat("/").concat(fileName));
	}

}
