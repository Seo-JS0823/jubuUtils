package com.util.upload;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class UploadTestController {

	@GetMapping("/test/upload")
	public String uploadRender() {
		return "test/upload";
	}
	
	@PostMapping("/test/upload/save")
	@ResponseBody
	public ResponseEntity<FileResponse> upload(@RequestParam("file") MultipartFile file) {
		ImageUploadImpl upload = new ImageUploadImpl("uploads/community", file);
		FileResponse response = upload.response();
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
}
