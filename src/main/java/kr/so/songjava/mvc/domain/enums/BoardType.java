package kr.so.songjava.mvc.domain.enums;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**게시판 종류*/
@JsonFormat(shape = Shape.OBJECT) // 추가
@AllArgsConstructor
@Getter
public enum BoardType {

	NOTICE("NOTICE", "공지사항"),
	FAQ("FAQ" ,"자주묻는질문"),
	INQUIRY("INQUIRY" ,"1:1문의")
	;
		
	//@JsonValue // 추가
	private String code;
	private String label;
		
}
