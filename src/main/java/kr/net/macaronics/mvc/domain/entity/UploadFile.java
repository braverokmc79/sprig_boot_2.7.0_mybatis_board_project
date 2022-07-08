package kr.net.macaronics.mvc.domain.entity;

import java.util.Date;

import kr.net.macaronics.mvc.domain.enums.BoardType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UploadFile {
	
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
