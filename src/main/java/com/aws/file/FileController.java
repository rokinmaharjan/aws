package com.aws.file;

import java.io.IOException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/files")
public class FileController {

	@Autowired
	private FileService fileService;

	/**
	 * <p>
	 * API to upload a file of any type.
	 * </p>
	 * @param file
	 * @return path of the uploaded file
	 */
	@PostMapping("/upload")
	public Map<String, String> uploadFile(@RequestParam("file") MultipartFile file) {
		try {
			return this.fileService.uploadFileInS3(file);
		} catch (IOException e) {
			throw new InternalError(e.getMessage());
		}
	}
	
	/**
	 * <p>
	 * API to delete a file.
	 * </p>
	 * 
	 * @param fileName
	 * @return HttpStatus
	 */
	@DeleteMapping("/file")
	public ResponseEntity<HttpStatus> deleteFile(@RequestParam("file-name") String fileName) {
		fileService.deleteFileInS3(fileName);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
