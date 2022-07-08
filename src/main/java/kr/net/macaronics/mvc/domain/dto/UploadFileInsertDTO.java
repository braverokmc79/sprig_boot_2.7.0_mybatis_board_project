package kr.net.macaronics.mvc.domain.dto;

import kr.net.macaronics.mvc.domain.enums.BoardTypeInsert;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**  등록시 파라미터 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UploadFileInsertDTO {

	
	private int uploadFileSeq;  //파일테이블 PK
	private Integer boardSeq;   //게시판 테이블 PK
	private BoardTypeInsert boardType;  //게시판 종류
	private String pathname; //전체경로
	private String filename;  //파일명
	private String originalFilename;  //원본 파일명
	private int size; //파일크기
	private String contentType;  //컨텐츠 종류
	private String resourcePathname; //리소스 파일경로
	private String thumbnailPathname; //썸네일 전체경로
	private String thumbnailResourcePathname; //썸네일 리소스파일경로
	
	
	@Builder
	public UploadFileInsertDTO (Integer boardSeq ,BoardTypeInsert boardType, String pathname, String filename,
			String originalFilename, int size, String contentType, String resourcePathname, String thumbnailPathname,
			String thumbnailResourcePathname) {
		this.boardSeq = boardSeq;
		this.boardType = boardType;
		this.pathname = pathname;
		this.filename = filename;
		this.originalFilename = originalFilename;
		this.size = size;
		this.contentType = contentType;
		this.resourcePathname = resourcePathname;
		this.thumbnailPathname = thumbnailPathname;
		this.thumbnailResourcePathname = thumbnailResourcePathname;
	}	
}




