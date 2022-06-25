package kr.so.songjava.mvc.domain.entity;

import java.util.Date;

import kr.so.songjava.mvc.domain.enums.BoardType;
import lombok.Data;

@Data
public class Board {
	
	private int boardSeq;
	private BoardType boardType;
	private String title;
	private String contents;
	private Date regDate;
}

  
