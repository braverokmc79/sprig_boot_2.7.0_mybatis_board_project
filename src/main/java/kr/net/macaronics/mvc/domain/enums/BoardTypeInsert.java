package kr.net.macaronics.mvc.domain.enums;

import lombok.Getter;

/**게시판 등록/수정시 파라미터 enum*/
@Getter
public enum BoardTypeInsert {
 
    NOTICE("공지사항"),
    FAQ("자주묻는질문"),
    INQUIRY("1:1문의"),
    NONE("none")
    ;
     
    private String code;
    private String label;
     
    BoardTypeInsert(String label) {
        this.code=name();
        this.label=label;
    }
     
         
}