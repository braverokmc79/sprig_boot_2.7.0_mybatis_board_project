package kr.net.macaronics.configuration.http;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public enum BaseResponseCode {

	SUCCESS(200), // 성공
	ERROR(500), // 실패
	LOGIN_REQUIRED(403), 
	NOT_FOUND(404),
	DATA_IS_NULL,
	IllegalArgumentException(404),
	UPLOAD_FILE_IS_NULL,	
	VALIDATE_REQUIRED;
	
	private int status;

	BaseResponseCode(int status) {
		this.status = status;
	}

	public int status() {
		return status;
	}

}