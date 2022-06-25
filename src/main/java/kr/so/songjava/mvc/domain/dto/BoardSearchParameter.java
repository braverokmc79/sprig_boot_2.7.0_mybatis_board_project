package kr.so.songjava.mvc.domain.dto;

import java.util.List;

import kr.so.songjava.mvc.domain.enums.BoardType;
import lombok.Data;

@Data
public class BoardSearchParameter {	
	private String keyword;
	private List<BoardType> boardTypes;  //배열로 다중 검색 처리를 위해 , NOTICE, FAQ,INQUIRY	
}
