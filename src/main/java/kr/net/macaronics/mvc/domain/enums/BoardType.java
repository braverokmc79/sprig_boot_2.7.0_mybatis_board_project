package kr.net.macaronics.mvc.domain.enums;

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
         
    /** 한개의 데이터만 가져올경우 @JsonFormat 제거후 해당 변수에 @JsonValue 어노테이션 추가
	   "boardType": "FAQ", 형태로 반환 
	 */
    //@JsonValue 
	private String code;
    private String label;
         
}

