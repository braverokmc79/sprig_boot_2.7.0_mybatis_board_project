create user `songjava`@`localhost` identified by '1111';    
create database songjava CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci;  
grant all privileges on songjava.* to `songjava`@`localhost` ;

use songjava;



CREATE TABLE t_board(
	`board_seq` int auto_increment primary key COMMENT 't_board 테이블 pk', 
	`board_type` varchar(255) COMMENT '게시판 종류', 
	`title` varchar(255) COMMENT '제목', 
	`contents` text  COMMENT '내용',
	`reg_date` timestamp NOT null default current_timestamp() COMMENT '등록일'
)ENGINE=InnoDB  DEFAULT CHARSET=utf8;


INSERT INTO T_BOARD (TITLE,CONTENTS) VALUES ('제목','내용');
