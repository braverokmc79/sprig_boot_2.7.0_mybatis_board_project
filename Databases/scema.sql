create user `restapi1`@`localhost` identified by '1111';    
create database restapi1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci;  
grant all privileges on restapi1.* to `restapi1`@`localhost` ;

use restapi1;



CREATE TABLE T_BOARD(
	`BOARD_SEQ` bigint AUTO_INCREMENT PRIMARY KEY COMMENT '게시판테이블 PK', 
	`BOARD_TYPE` varchar(255) COMMENT '게시판 종류', 
	`TITLE` varchar(255) COMMENT '제목', 
	`CONTENTS` text  COMMENT '내용',
	`DEL_YN` char(1) default 'N' COMMENT '삭제여부',
	`REG_DATE` timestamp NOT null DEFAULT CURRENT_TIMESTAMP() COMMENT '등록일'
)ENGINE=InnoDB  COLLATE='utf8mb4_unicode_ci' COMMENT='게시판';


INSERT INTO T_BOARD (TITLE,CONTENTS) VALUES ('제목','내용');




CREATE TABLE `T_UPLOAD_FILE` (
  `UPLOAD_FILE_SEQ` bigint NOT NULL AUTO_INCREMENT COMMENT '파일테이블 PK', 
  `BOARD_SEQ` bigint null COMMENT '게시판 테이블 PK',
  `BOARD_TYPE` varchar(255) DEFAULT 'NONE' COMMENT '게시판 종류', 
  `PATHNAME` VARCHAR(100) NOT NULL COMMENT '전체경로', 
  `FILENAME` VARCHAR(50) NOT NULL COMMENT '파일명' , 
  `ORIGINAL_FILENAME` VARCHAR(100) NOT NULL COMMENT '원본 파일명' , 
  `SIZE` INT(11) NOT NULL COMMENT '파일크기',
  `CONTENT_TYPE` VARCHAR(50) NOT NULL COMMENT '컨텐츠 종류' , 
  `RESOURCE_PATHNAME` VARCHAR(100) NOT NULL DEFAULT '' COMMENT '리소스 파일경로' , 
  `THUMBNAIL_PATHNAME` VARCHAR(100) NULL COMMENT '썸네일 전체경로',
  `THUMBNAIL_RESOURCE_PATHNAME` VARCHAR(100) NULL DEFAULT '' COMMENT '썸네일 리소스파일경로' ,  
  `REG_DATE` timestamp NOT null DEFAULT CURRENT_TIMESTAMP() COMMENT '등록일',
  PRIMARY KEY (`UPLOAD_FILE_SEQ`) USING BTREE   
)ENGINE=InnoDB  COLLATE='utf8mb4_unicode_ci' COMMENT='파일';




