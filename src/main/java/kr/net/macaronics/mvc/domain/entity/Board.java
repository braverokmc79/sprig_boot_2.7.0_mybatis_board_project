package kr.net.macaronics.mvc.domain.entity;

import java.util.Date;

import kr.net.macaronics.mvc.domain.enums.BoardType;
import lombok.Data;

@Data
public class Board {
	
	private int boardSeq;
	private BoardType boardType;
	private String title;
	private String contents;
	private boolean delYn;
	private Date regDate;
}

  
