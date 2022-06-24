package kr.so.songjava.mvc.domain.dto;

import kr.so.songjava.mvc.domain.entity.Role;
import kr.so.songjava.mvc.domain.entity.User;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
public class UserDTO {


	private long id;
	private String username;
	private String password;
	private String email;
	private Role role;
	
	
	@Builder
	public UserDTO(String username, String password, String email, Role role) {
		this.username = username;
		this.password = password;
		this.email = email;
		this.role = role;
	}
	
	
	public User toEntitiy(UserDTO userDTO) {		
		return User.builder()
				.username(userDTO.getUsername())
				.password(userDTO.getPassword())
				.email(userDTO.getEmail())
				.role(userDTO.getRole())
				.build();						
	}
	
}


