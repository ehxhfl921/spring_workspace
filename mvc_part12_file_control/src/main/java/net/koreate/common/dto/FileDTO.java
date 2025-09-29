package net.koreate.common.dto;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

/**
 * DTO(Data Transfer Object)
 * 계층간 데이터를 전달하기 위한 객체
 */
public class FileDTO {

	private String author;
	private String content;
	private MultipartFile profile;
	private List<MultipartFile> files;
	
	public String getAuthor() {
		return author;
	}
	
	public void setAuthor(String author) {
		this.author = author;
	}
	
	public String getContent() {
		return content;
	}
	
	public void setContent(String content) {
		this.content = content;
	}
	
	public MultipartFile getProfile() {
		return profile;
	}
	
	public void setProfile(MultipartFile profile) {
		this.profile = profile;
	}
	
	public List<MultipartFile> getFiles() {
		return files;
	}
	
	public void setFiles(List<MultipartFile> files) {
		this.files = files;
	}

	@Override
	public String toString() {
		return "FileDTO [author=" + author + ", content=" + content + ", profile=" + profile + ", files=" + files + "]";
	}
	
}
