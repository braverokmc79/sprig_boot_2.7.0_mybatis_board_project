package kr.net.macaronics.mvc.domain.dto;

import kr.net.macaronics.mvc.domain.enums.BoardTypeInsert;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/** 등록/수정시 파라미터 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class BoardInsertDTO {
	
	private int boardSeq;
	private BoardTypeInsert boardType;
	private String title;	
	private String contents;
	private String delYn;
		
}

  
