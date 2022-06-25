package kr.so.songjava.mvc.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.RandomStringUtils;
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
import io.swagger.annotations.ApiParam;
import kr.so.songjava.configuration.http.BaseResponse;
import kr.so.songjava.mvc.domain.dto.BoardDTO;
import kr.so.songjava.mvc.domain.dto.BoardInsertDTO;
import kr.so.songjava.mvc.domain.dto.BoardSearchParameter;
import kr.so.songjava.mvc.domain.entity.Board;
import kr.so.songjava.mvc.service.BoardSevice;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/board")
@Api(tags="게시판 API")
@Slf4j
public class BoardApiController {

	
	@Autowired
	private BoardSevice boardService;

	/** 게시판 목록리턴 */
	@GetMapping({"","/"})
	@ApiOperation(value="목록조회", notes="게시물 번호에 해당하는 목록정보를 조회할수 있습니다.")
	public BaseResponse<List<BoardDTO>> getList(@ApiParam BoardSearchParameter boardSearchParameter){
		return new BaseResponse<List<BoardDTO>>(boardService.getList(boardSearchParameter));
	}
	
	
	/** 게시판 상세보기 */
	@GetMapping("/{boardSeq}")
	@ApiOperation(value="상세조회", notes="게시물 번호에 해당하는 상세정보를 조회할수 있습니다.")
	@ApiImplicitParams({
		@ApiImplicitParam(name="boardSeq", value="게시물 번호", example = "1")
	})
	public BaseResponse<Board> get(@PathVariable int boardSeq) {		
		return new BaseResponse<Board>(boardService.get(boardSeq));
	}
	
	

	/** 게시판 등록/수정처리 */
	@PutMapping("/save")
	@ApiOperation(value="등록/수정처리", notes="신규 게시물 저장 및 기존 게시물 업데이트가 가능합니다.")
	@ApiImplicitParams({
		@ApiImplicitParam(name="boardSeq", value="게시물번호", example = "1"),
		@ApiImplicitParam(name="boardType", value="게시판종류", example = "NOTICE"),
		@ApiImplicitParam(name="title", value="제목", example = "spring"),
		@ApiImplicitParam(name="contents", value="내용", example="spring 강좌"),
	})
	public BaseResponse<Integer> save(BoardInsertDTO boardInsertDTO) {
		boardService.save(boardInsertDTO);
		return new BaseResponse<Integer>(boardInsertDTO.getBoardSeq());
	}
	
	

	/** 게시판 삭제처리 */
	@DeleteMapping("/{boardSeq}")
	@ApiOperation(value="게시판 삭제처리", notes="게시물 번호에 해당하는 정보를 삭제합니다.")
	@ApiImplicitParams({
		@ApiImplicitParam(name="boardSeq", value="게시물 번호", example = "1")
	})
	public BaseResponse<Boolean>  delete(@PathVariable int boardSeq) {
		return  new BaseResponse<Boolean>(boardService.delete(boardSeq));
	}
	
	
	/**대용량 등록 처리1 **/
	@ApiOperation(value="대용량 등록처리1" , notes="대용량 등록처리1")
	@PutMapping("/saveList1")
	public BaseResponse<Boolean> saveList1(){
		int count=0;
		//테스트를 위한 랜덤 1000 건의 데이터를 생성
		List<BoardInsertDTO> list=new ArrayList<BoardInsertDTO>();
		while(true){
			count++;
			String title=RandomStringUtils.randomAlphabetic(10);
			String contents=RandomStringUtils.randomAlphabetic(10);
			list.add(BoardInsertDTO.builder().title(title).contents(contents).build());
			if(count >1000) {
				break;
			}
		}
		
		long start =System.currentTimeMillis();
		boardService.saveList1(list);
		long end =System.currentTimeMillis();
		log.info("실행 시간 : {}",  (end-start) / 1000.0);
		
		return new BaseResponse<Boolean>(true); 
	}
	
	
	
	/**대용량 등록 처리2 **/
	@ApiOperation(value="대용량 등록처리2" , notes="대용량 등록처리2")
	@PutMapping("/saveList2")
	public BaseResponse<Boolean> saveList2(){
		int count=0;
		//테스트를 위한 랜덤 1000 건의 데이터를 생성
		List<BoardInsertDTO> list=new ArrayList<BoardInsertDTO>();
		while(true){
			count++;
			String title=RandomStringUtils.randomAlphabetic(10);
			String contents=RandomStringUtils.randomAlphabetic(10);
			list.add(BoardInsertDTO.builder().title(title).contents(contents).build());
			if(count >1000) {
				break;
			}
		}
		
		long start =System.currentTimeMillis();
		boardService.saveList2(list);
		long end =System.currentTimeMillis();
		log.info("실행 시간 : {}",  (end-start) / 1000.0);
		
		return new BaseResponse<Boolean>(true); 
	}
	
	
	
	
}
