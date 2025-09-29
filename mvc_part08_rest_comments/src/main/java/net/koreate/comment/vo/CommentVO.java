package net.koreate.comment.vo;

import java.util.Date;

import lombok.Data;

@Data
public class CommentVO {

	private int cno;			// 댓글 번호
	private int bno;			// 댓글 등록 게시글 번호
	private String content;		// 댓글 내용
	private String author;		// 댓글 작성자
	private Date regdate;		// 댓글 작성 시간
	private Date updatedate;	// 댓글 수정 시간
	
}
