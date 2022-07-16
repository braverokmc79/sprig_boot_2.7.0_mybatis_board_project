package kr.net.macaronics.mvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/board")
public class BoardController {

	
	/** 게시판 목록 페이지 */
	@GetMapping("/list")
	public String list() {
		return "board/board_list";
	}
	
	
	/** 게시판 등록 폼페이지 */
	@GetMapping("/saveForm")
	public String saveForm() {
		return "board/saveForm";
	}
	

	
	
	
}
