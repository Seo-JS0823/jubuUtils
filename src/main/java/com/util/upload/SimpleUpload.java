package com.util.upload;

public interface SimpleUpload {

	String storeFile();
	
	String getOriginalFileName();
	
	boolean deleteFile(String filePath);
	
	String[] getTypes();
	
	String getMimeType();
	
	long getFileSize();
	
	/*
	 * 2025-10-26
	 * Argument : 원래 파일의 이름
	 * result   : 파일의 확장자가 Interface 구현체에서 지정한 확장자 목록과 일치하는지 확인
	 * 
	 * 지원하는 파일 타입인지 검증하는 Method
	 */
	default boolean validateFileType(String originFileName) {
		String file = originFileName.toLowerCase();
		
		String[] fileParser = file.split("[.]");
		if(fileParser.length < 2) throw new IllegalArgumentException("파일의 확장자가 없습니다.");
		
		int fileParserSize = fileParser.length;
		
		String fileType = fileParser[fileParserSize - 1];
		
		String[] types = this.getTypes();
		
		for(String type : types) {
			if(fileType.equals(type)) {
				return true;
			}
		}
		
		return false;
	}
	
	default String fileSizeFormat(long bytes) {
		int unit = 1024;
		
		if(bytes < unit) {
			return bytes + " B";
		}
		
		String[] prefixes = { "K", "M", "G", "T", "P", "E" };
		
		int exp = (int) (Math.log(bytes) / Math.log(unit));
		String prefix = prefixes[exp - 1];
		
		return String.format("%.2f %sB", bytes / Math.pow(unit, exp), prefix);
	}
}
