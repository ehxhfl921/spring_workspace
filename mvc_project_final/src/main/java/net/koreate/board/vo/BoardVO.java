package net.koreate.board.vo;

import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class BoardVO {
	
	private int bno;					// 게시글 번호
	private String title;				// 게시글 제목
	private String content;				// 게시글 내용
	private int origin;					// 원본글 질문글 번호
	private Date regdate;				// 게시글 등록 시간
	private Date updatedate;			// 게시글 수정 시간
	private int viewcnt;				// 조회수
	private String showboard;			// 게시글 삭제 여부
	private int u_no;					// 게시글 작성자 번호
	private String writer;				// 게시글 작성자 이름
	
	private List<String> files;			// 첨부파일 목록
}








