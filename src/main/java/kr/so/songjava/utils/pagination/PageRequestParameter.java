package kr.so.songjava.utils.pagination;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/** 페이지 요청정보 파라미터 정보 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PageRequestParameter<T> {

	private MySQLPageRequest pageRequest;
	private T parameter;
	
}
