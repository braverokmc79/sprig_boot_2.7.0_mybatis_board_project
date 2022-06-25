package kr.so.songjava.configuration;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.MediaType;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;

import kr.so.songjava.mvc.domain.enums.BaseCodeLabelEnum;

/**
 *JSON 변환시 BaseCodeLabelEnum 클래스에 대한 변환을 동일하게 처리
 */
public class BaseCodeLabelEnumJsonSerializer  extends JsonSerializer<BaseCodeLabelEnum>{

	
	@Override
	public void serialize(BaseCodeLabelEnum value, JsonGenerator jsonGenerator, SerializerProvider serializers)
			throws IOException , JsonProcessingException {
		Map<String, Object> map=new HashMap<>();
		map.put("code", value.code());
		map.put("label", value.label());
		jsonGenerator.writeObject(map);
	}

	
	
}
