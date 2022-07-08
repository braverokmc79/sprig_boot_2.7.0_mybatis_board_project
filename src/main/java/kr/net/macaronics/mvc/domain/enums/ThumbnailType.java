package kr.net.macaronics.mvc.domain.enums;

public enum ThumbnailType {

	WEB_MAIN(500, 300),
	WIDTH_200(200, 100),
	WEB_SUB(150, 70);
	
	
	ThumbnailType(int width, int height){
		this.width=width;
		this.height=height;
	}
		
	private int width;
	private int height;
	
	public int width() {
		return width;
	}
	
	public int height() {
		return height;
	}
	
	
}
