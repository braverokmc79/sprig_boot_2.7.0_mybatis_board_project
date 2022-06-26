package kr.net.macaronics.configuration.http;

import kr.net.macaronics.utils.pagination2.MysqlPageMaker;
import kr.net.macaronics.utils.pagination2.MysqlPageMakerResponse;
import lombok.Data;

@Data
public class BaseResponse<T> {

	private BaseResponseCode code;
	private String message;
	private MysqlPageMakerResponse pageMaker;
	private int displayPageNum;
	
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

	/** MysqlPageMaker 페이지 처리 메소드 */
	public BaseResponse(T data,  MysqlPageMaker mysqlPageMaker) {
		this.code=BaseResponseCode.SUCCESS;
		this.data=data;
		this.pageMaker=mysqlPageMaker.toResPageMaker(mysqlPageMaker);
	}
	
	
	
	
}
