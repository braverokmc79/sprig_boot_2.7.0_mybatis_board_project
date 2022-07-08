package kr.net.macaronics.mvc.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.net.macaronics.mvc.domain.dto.ExampleParameter;
import kr.net.macaronics.mvc.domain.dto.ExampleRequestBodyUser;

@Controller
@RequestMapping("/example/parameter")
public class ExampleParameterController {

	/**
	 * 자료형으로 파라미터 받는 방법
	 * @param id
	 * @param code
	 * @param model
	 */
	@GetMapping("/example1")
	public void example1(@RequestParam String id, @RequestParam String code, Model model) {
		model.addAttribute("id" ,id);
		model.addAttribute("code" ,code);
	}
	
	/**
	 * Map을 활용한 파라미터 받는 방법
	 * @param paramMap
	 * @param model
	 */
	@GetMapping("/example2")
	public void example2(@RequestParam Map<String,Object> paramMap, Model model) {
		model.addAttribute("paramMap" ,paramMap);
	}
	
	/**
	 * Class를 활용한 파라메터 받는 방법
	 * @param parameter
	 * @param model
	 */
	@GetMapping("/example3")
	public void example3(ExampleParameter parameter, Model model) {
		model.addAttribute("parameter" ,parameter);
	}
	
	
	/**
	 * @PathVariable 을 활용한 파라메터 받는 방법
	 * @param id
	 * @param code
	 * @param model
	 * @return
	 */
	@GetMapping("/example4/{id}/{code}")
	public String example4(@PathVariable String id, @PathVariable String code, Model model) {
		model.addAttribute("id" ,id);
		model.addAttribute("code" ,code);
		return "/example/parameter/example4";
	}
	
	
	
	
	/**
	 * Stirng[] 배열을 받는방법
	 * @param ids
	 * @param model
	 * @return
	 */
	@GetMapping("/example5")
	public String example5(@RequestParam String[] ids, Model model) {
		model.addAttribute("ids" ,ids);		
		return "/example/parameter/example5";
	}
	
	
	
	/**
	 * example6 json Form 
	 */
	@GetMapping("/example6/form")
	public String example6() {
		return "/example/parameter/example6/form";
	}
	
	
	/**
	 * json Map 으로 받는 방법
	 * @param requestBody
	 * @param model
	 * @return
	 */
	@PostMapping("/example6/saveData")
	@ResponseBody
	public Map<String, Object> example6(@RequestBody Map<String,Object> requestBody, Model model) {
		Map<String, Object> resultMap=new HashMap<String, Object>();
		resultMap.put("requestBody", requestBody);
		resultMap.put("result", true);		
		return resultMap;
	}
	
	
	
	/**
	 * example7 json Form 
	 */
	@GetMapping("/example7/form")
	public String example7Form() {
		return "/example/parameter/example7/form";
	}
	
	/**
	 * json class 으로 받는 방법
	 * @param requestBody
	 * @param model
	 * @return
	 */
	@PostMapping("/example7/saveData")
	@ResponseBody
	public Map<String, Object> example7(@RequestBody ExampleRequestBodyUser requestBody, Model model) {
		Map<String, Object> resultMap=new HashMap<String, Object>();
		resultMap.put("requestBody", requestBody);
		resultMap.put("result", true);		
		return resultMap;
	}
	
	
	
	
	
}
