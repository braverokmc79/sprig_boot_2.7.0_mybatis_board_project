package kr.net.macaronics.utils.pagination;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString	
public class MySQLPageRequest {

	private int page;
	private int size;
	
	
	@JsonIgnore
	@ApiModelProperty(hidden=true)
	private int limit;
	
	
	@JsonIgnore
	@ApiModelProperty(hidden=true)
	private int offset;
	
	
}
