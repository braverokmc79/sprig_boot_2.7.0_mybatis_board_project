package kr.so.songjava.mvc.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import kr.so.songjava.mvc.domain.dto.BoardDto;
import kr.so.songjava.mvc.domain.entity.Board;
import kr.so.songjava.mvc.service.BoardSevice;

@RestController
@RequestMapping("/board")
@Api(tags="게시판 API")
public class BoardController {

	
	@Autowired
	private BoardSevice boardSevice;

	/** 게시판 목록리턴 */
	@GetMapping("/")
	@ApiOperation(value="목록조회", notes="게시물 번호에 해당하는 목록정보를 조회할수 있습니다.")
	public List<Board> getList(){
		return boardSevice.getList();
	}
	
	/** 게시판 상세보기 */
	@GetMapping("/{boardSeq}")
	@ApiOperation(value="상세조회", notes="게시물 번호에 해당하는 상세정보를 조회할수 있습니다.")
	@ApiImplicitParams({
		@ApiImplicitParam(name="boardSeq", value="게시물 번호", example = "1")
	})
	public Board get(@PathVariable int boardSeq) {		
		return boardSevice.get(boardSeq);
	}
	
	

	/** 게시판 등록/수정처리 */
	@PutMapping("/save")
	@ApiOperation(value="등록/수정처리", notes="신규 게시물 저장 및 기존 게시물 업데이트가 가능합니다.")
	@ApiImplicitParams({
		@ApiImplicitParam(name="boardSeq", value="게시물 번호", example = "1"),
		@ApiImplicitParam(name="title", value="제목", example = "spring"),
		@ApiImplicitParam(name="contents", value="내용", example="spring 강좌"),
	})
	public Integer save(BoardDto boardDto) {
		return boardSevice.save(boardDto);
	}
	
	

	/** 게시판 삭제처리 */
	@DeleteMapping("/{boardSeq}")
	@ApiOperation(value="게시판 삭제처리", notes="게시물 번호에 해당하는 정보를 삭제합니다.")
	@ApiImplicitParams({
		@ApiImplicitParam(name="boardSeq", value="게시물 번호", example = "1")
	})
	public int delete(@PathVariable int boardSeq) {
		return boardSevice.delete(boardSeq);
	}
	
	
	
}
