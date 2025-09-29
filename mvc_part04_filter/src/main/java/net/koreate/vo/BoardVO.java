package net.koreate.vo;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BoardVO {
	
	private int num;
	private String title;
	private String writer;
	private String content;
	private Date regdate;
	
}
