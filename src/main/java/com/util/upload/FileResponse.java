package com.util.upload;

public class FileResponse {

	private String originalFileName;
	private String fileSize;
	private String saveFileName;
	
	public FileResponse() {}
	
	public FileResponse(String originalFileName, String fileSize, String saveFileName) {
		this.originalFileName = originalFileName;
		this.fileSize = fileSize;
		this.saveFileName = saveFileName;
	}
	
	public void setOriginalFileName(String originalFileName) {
		this.originalFileName = originalFileName;
	}
	
	public void setFileSize(String fileSize) {
		this.fileSize = fileSize;
	}
	
	public void setSaveFileName(String saveFileName) {
		this.saveFileName = saveFileName;
	}
	
	public String getOriginalFileName() {
		return this.originalFileName;
	}
	
	public String getFileSize() {
		return this.fileSize;
	}
	
	public String getSaveFileName() {
		return this.saveFileName;
	}
}
