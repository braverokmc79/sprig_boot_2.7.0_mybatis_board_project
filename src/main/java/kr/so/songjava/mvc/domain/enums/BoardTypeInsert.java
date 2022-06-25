package kr.so.songjava.mvc.domain.enums;

/**게시판 등록/수정시 파라미터 enum*/
public enum BoardTypeInsert {
 
    NOTICE("공지사항"),
    FAQ("자주묻는질문"),
    INQUIRY("1:1문의"),
    ;
     
    private String code;
    private String label;
     
    BoardTypeInsert(String label) {
        this.code=name();
        this.label=label;
    }
     
         
}