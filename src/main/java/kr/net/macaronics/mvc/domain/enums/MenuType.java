package kr.net.macaronics.mvc.domain.enums;

/**
 * 메뉴 종류
 *
 */
public enum MenuType {
	
	community(BoardType.COMMUNITY),
    notice(BoardType.NONE),
    faq(BoardType.FAQ),
    inquiry(BoardType.INQUIRY)
    ;
	
	private BoardType boardType;
	
	private MenuType(BoardType boardType) {
		this.boardType=boardType;
	}
}
