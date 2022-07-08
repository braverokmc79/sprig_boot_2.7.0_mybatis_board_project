package kr.net.macaronics.mvc.domain.dto;

import java.util.List;

import kr.net.macaronics.mvc.domain.enums.BoardTypeInsert;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class BoardSearchParameter {	
	
	private String keyword;
	private List<BoardTypeInsert> boardTypes;  //배열로 다중 검색 처리를 위해 , NOTICE, FAQ,INQUIRY	
}
