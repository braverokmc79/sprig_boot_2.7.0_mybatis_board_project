package kr.net.macaronics.configuration.http;

import kr.net.macaronics.utils.pagination2.MysqlPageMaker;
import kr.net.macaronics.utils.pagination2.MysqlPageMakerResponse;
import lombok.Data;

@Data
public class BaseResponse<T> {

	/** success 또는 error 값만 반환  기본 설정 error */
	private String resState="success";
	
	private BaseResponseCode code;
	private String message;
	private MysqlPageMakerResponse pageMaker;
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
	
	
	public BaseResponse(BaseResponseCode code) {		
		this.code=code;	
	}


	public BaseResponse(String resState, String code, String message) {		
		this.resState=resState;
		try {
			this.code =BaseResponseCode.valueOf(code);	
		}catch (Exception e) {
			this.code=null;
		}			
		this.message=message;				
	}
	
	
	public BaseResponse(String resState, BaseResponseCode code, String message) {		
		this.resState=resState;
		this.code=code;		
		this.message=message;				
	}


	
	public BaseResponse(String resState, String message) {		
		this.resState=resState;	
		this.message=message;				
	}


	
	public BaseResponse(String resState, T data, String message) {
		if(resState.equals("error"))this.code=BaseResponseCode.ERROR;		
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
