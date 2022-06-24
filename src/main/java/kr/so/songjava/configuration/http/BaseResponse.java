package kr.so.songjava.configuration.http;

import lombok.Data;

@Data
public class BaseResponse<T> {

	private BaseResponseCode code;
	private String message;
	private T data;

	public BaseResponse(T data) {		
		this.code=BaseResponseCode.SUCCESS;
		this.data=data;
	}
	
	public BaseResponse(String status, T data) {
		if(status.equals("error"))this.code=BaseResponseCode.ERROR;		
		else this.code=BaseResponseCode.SUCCESS;
		this.data=data;
	}
	
	public BaseResponse(String status, T data, String message) {
		if(status.equals("error"))this.code=BaseResponseCode.ERROR;		
		else this.code=BaseResponseCode.SUCCESS;
		this.data=data;
		this.message=message;
	}
}
