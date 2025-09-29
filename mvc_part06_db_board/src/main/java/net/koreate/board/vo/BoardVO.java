package net.koreate.board.vo;

import java.util.Date;

import lombok.Data;

/**
 *  test_board table 데이터 교환을 위한 class
 */

@Data
public class BoardVO {

	private int bno;
	private String title;
	private String content;
	private String writer;
	private Date regdate;
	private int viewcnt;
	
}
