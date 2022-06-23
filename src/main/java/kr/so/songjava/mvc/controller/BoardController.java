package kr.so.songjava.mvc.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import kr.so.songjava.mvc.domain.Board;
import kr.so.songjava.mvc.service.BoardSevice;

@RestController
@RequestMapping("/board")
public class BoardController {

	
	@Autowired
	private BoardSevice boardSevice;

	/** 게시판 목록리턴 */
	@GetMapping({"","/"})
	@ResponseBody
	public List<Board> getList(){
		return boardSevice.getList();
	}
	
	/** 게시판 상세보기 */
	@GetMapping("/{boardSeq}")
	public Board get(@PathVariable int boardSeq) {		
		return boardSevice.get(boardSeq);
	}

	/** 게시판 등록 수정처리 */
	@GetMapping("/save")
	public Integer save(Board board) {
		return boardSevice.save(board);
	}
	
	

	/** 게시판 삭제 */
	@GetMapping("/delete/{boardSeq}")
	public int delete(@PathVariable int boardSeq) {
		return boardSevice.delete(boardSeq);
	}
	
	
	
}
