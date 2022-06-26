package kr.net.macaronics.mvc.domain.entity;

import java.time.LocalDateTime;

import lombok.Getter;

/** 등록/수정 일자  */
@Getter
public class BaseTimeEntity {

    private LocalDateTime createdDate;

    private LocalDateTime modifiedDate;
}



