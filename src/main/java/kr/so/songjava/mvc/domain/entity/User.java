package kr.so.songjava.mvc.domain.entity;

import kr.so.songjava.mvc.domain.enums.Role;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
public class User  extends BaseTimeEntity{


	private long id;
	private String username;
	
	private String password;
	private String email;
	

	private Role role;

	private String provider;
	private String providerId;
	
	
	@Builder
	public User(String username, String password, String email, Role role, String provider, String providerId) {
		this.username = username;
		this.password = password;
		this.email = email;
		this.role = role;
		this.provider=provider;
		this.providerId=providerId;
	}
	
	
	
	
}


