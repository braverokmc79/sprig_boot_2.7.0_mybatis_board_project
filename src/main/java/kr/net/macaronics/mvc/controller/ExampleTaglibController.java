package kr.net.macaronics.mvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.net.macaronics.mvc.domain.dto.BoardSearchParameter;
import kr.net.macaronics.mvc.domain.enums.BoardType;

@Controller
@RequestMapping("/example/taglib/")
public class ExampleTaglibController {

	@RequestMapping("/search")
	public void search(BoardSearchParameter parameter, Model model) {
		
		model.addAttribute("boardTypes", BoardType.values());
		model.addAttribute("boardTypeLength", BoardType.values().length);
		model.addAttribute("parameter", parameter);
	}
}
