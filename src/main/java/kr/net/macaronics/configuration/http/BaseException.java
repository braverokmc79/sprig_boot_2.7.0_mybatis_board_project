package kr.net.macaronics.configuration.http;

import lombok.Getter;

@Getter
public class BaseException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public BaseException(String msg) {		
		super(msg);
	}
	

}
