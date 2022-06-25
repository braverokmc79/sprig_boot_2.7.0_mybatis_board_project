package kr.so.songjava.mvc.domain.dto;

import kr.so.songjava.mvc.domain.enums.BoardType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class BoardDTO {
	
	private int boardSeq;
	private BoardType boardType;
	private String title;
	private String contents;
		
}

  
