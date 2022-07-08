package kr.net.macaronics.mvc.domain.dto;

import java.util.Date;

import kr.net.macaronics.mvc.domain.enums.BoardType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/** 파일 데이터를 가져올시 파라미터 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UploadFileDTO {

	private int uploadFileSeq;  //파일테이블 PK
	private Integer boardSeq;   //게시판 테이블 PK
	private BoardType boardType;  //게시판 종류
	private String pathname; //전체경로
	private String filename;  //파일명
	private String originalFilename;  //원본 파일명
	private int size; //파일크기
	private String contentType;  //컨텐츠 종류
	private String resourcePathname; //리소스 파일경로
	private String thumbnailPathname; //썸네일 전체경로
	private String thumbnailResourcePathname; //썸네일 리소스파일경로
	private Date regDate; //등록일
	
	
	
}

