package kr.so.songjava.mvc.domain.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {
  
	ROLE_GUEST("GUEST", "손님"),
	ROLE_USER("USER", "일반 사용자"),
	ROLE_MANAGER("MANAGER", "중간 관리자"),
	ROLE_ADMIN("ADMIN", "최고 관리자");

    private final String key;
    private final String title;

    
}
