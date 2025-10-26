package com.util.upload;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;

public class ImageUploadImpl implements SimpleUpload {
	
	private final String storageRoot;
	private final MultipartFile file;
	
	private final List<String> types = List.of(
		"jpg", "jpeg", "jfif", "png"
	);
	
	/*
	 * 2025-10-26
	 * 
	 * 파일이 저장될 경로 예) uploads/community/ 라면
	 * storageRoot = "uploads/community" => 마지막 슬래시(/) 제외
	 * 
	 * MultipartFile file = JavaScript FormData 로 넘어온 파일
	 */
	public ImageUploadImpl(String storageRoot, MultipartFile file) {
		this.storageRoot = storageRoot;
		this.file = file;
	}
	
	/*
	 * 2025-10-26
	 * 
	 * 클라이언트로 보낼 파일에 대한 Response를 모두 한 번에 처리합니다.
	 * 모든 로직이 성공적으로 완료되면 클라이언트는 JSON 으로 받아 처리할 수 있습니다.
	 */
	public FileResponse response() {
		FileResponse response = new FileResponse();
		response.setOriginalFileName(this.getOriginalFileName());
		response.setFileSize(this.fileSizeFormat(this.getFileSize()));
		response.setSaveFileName(this.storeFile());
		return response;
	}
	
	/*
	 * 2025-10-26
	 * 
	 * 생성자에서 주입한 파일이 저장될 폴더 경로를 반환합니다.
	 * 마지막 경로의 슬래시(/)는 제외합니다.
	 */
	public String getStorage() {
		return this.storageRoot;
	}
	
	/*
	 * 2025-10-26
	 * 
	 * 생성자에서 주입한 파일이 저장될 폴더에 파일을 저장합니다.
	 * 파일의 저장이 성공적으로 완료되면 해당 파일의 이름을 반환합니다.
	 */
	@Override
	public String storeFile() {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String now = format.format(new Date());
		String uuid = UUID.randomUUID().toString();
		
		String[] fileParser = this.getOriginalFileName().split("[.]");
		int fileParserSize = fileParser.length;
		
		String fileType = fileParser[fileParserSize - 1];
		this.validateFileType(this.getOriginalFileName());
		
		String savedFileName = new StringBuffer().append(now + "-")
												 .append(uuid + ".")
												 .append(fileType)
												 .toString();
		
		Path path = Paths.get(storageRoot).toAbsolutePath().normalize().resolve(savedFileName);
		
		try(InputStream inputStream = file.getInputStream()) {
			Files.copy(inputStream, path, StandardCopyOption.REPLACE_EXISTING);
			
			return savedFileName;
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException("File Save Error > message : " + e.getMessage());
		}
	}

	/*
	 * 2025-10-26
	 * 
	 * 요청으로 넘어온 실제 파일의 이름을 반환합니다.
	 */
	@Override
	public String getOriginalFileName() {
		String originalFileName = file.getOriginalFilename();
		
		return originalFileName;
	}

	/*
	 * 2025-10-26
	 * 
	 * DB에서 가져온 파일의 Path를 파라미터로 주면
	 * 해당 파일이 존재하는지 확인한 후 파일을 삭제합니다.
	 */
	@Override
	public boolean deleteFile(String filePath) {
		File deletedFile = new File(filePath);
		
		boolean validateFile = deletedFile.exists();
		
		if(validateFile) {
			deletedFile.delete();
			return true;
		} else {
			throw new IllegalArgumentException("존재하지 않는 파일입니다.");
		}
	}
	
	/*
	 * 2025-10-26
	 * 
	 * 지원하는 파일 타입을 String[] 형태로 반환
	 * 해당 로직은 인터페이스 구현 대상인 SimpleUpload Interface의
	 * validateFileType Method 에서 사용됌
	 */
	@Override
	public String[] getTypes() {
		return this.types.toArray(new String[0]);
	}
	
	/*
	 * 2025-10-26
	 * 
	 * 생성자에서 주입한 MultipartFile에 들어있는 파일의 MIME Type을 반환합니다.
	 */
	@Override
	public String getMimeType() {
		return this.file.getContentType();
	}
	
	/*
	 * 2025-10-26
	 * 
	 * 생성자에서 주입한 MultipartFile에 들어있는 파일의 사이즈 반환
	 */
	@Override
	public long getFileSize() {
		long fileSize = file.getSize();
		
		return fileSize;
	}
	
}
