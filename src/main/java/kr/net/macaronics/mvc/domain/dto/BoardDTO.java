package kr.net.macaronics.mvc.domain.dto;

import kr.net.macaronics.mvc.domain.enums.BoardType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/** 게시판 데이터를 가져올시 파라미터 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class BoardDTO {
	
	private int boardSeq;
	private BoardType boardType;
	private String title;	
	private String contents;
	private String keyword;
	private boolean delYn;
	
	
}

  
