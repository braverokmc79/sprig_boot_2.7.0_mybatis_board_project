package kr.net.macaronics.mvc.controller;

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
import kr.net.macaronics.annotation.RequestConfig;
import kr.net.macaronics.configuration.http.BaseResponse;
import kr.net.macaronics.mvc.domain.dto.BoardDTO;
import kr.net.macaronics.mvc.domain.dto.BoardInsertDTO;
import kr.net.macaronics.mvc.domain.dto.BoardSearchParameter;
import kr.net.macaronics.mvc.domain.entity.Board;
import kr.net.macaronics.mvc.domain.enums.BoardTypeInsert;
import kr.net.macaronics.mvc.service.BoardSevice;
import kr.net.macaronics.utils.pagination.MySQLPageRequest;
import kr.net.macaronics.utils.pagination.PageRequestParameter;
import kr.net.macaronics.utils.pagination2.MysqlPageMaker;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/board")
@Api(tags="게시판 API")
@Slf4j
public class BoardApiController {

	
	@Autowired
	private BoardSevice boardService;

	/** 1.게시판 검색처리목록리턴 페이징 처리 x */
	@GetMapping({"","/"})
	@ApiOperation(value="목록조회", notes="1.게시판 검색처리목록리턴 페이징 처리 x")
	public BaseResponse<List<BoardDTO>> getList(@ApiParam BoardSearchParameter boardSearchParameter) throws Exception{
		return new BaseResponse<List<BoardDTO>>(boardService.getList(boardSearchParameter));
	}
	
	
	/**2.게시판 페이징 검색처리 목록리턴  - 페이징 검색처리 첫번째 방법 (WebMvcConfig 에 페이지 리졸버 등록 방식) */
	@GetMapping({"/pageSearchList"})
	@ApiOperation(value="목록조회 - WebMvcConfig 에 페이지 리졸버 등록 방식", notes="2.게시판 페이징 검색처리 목록리턴 ")
	public BaseResponse<List<BoardDTO>> paginationSearchList(
			@ApiParam MySQLPageRequest pageRequest,
			@ApiParam  BoardSearchParameter parameter
			) throws Exception{
		log.info("1.pageRequest :" , pageRequest);
		PageRequestParameter<BoardSearchParameter> pageRequestParameter=new PageRequestParameter<BoardSearchParameter>(pageRequest, parameter);
		log.info("2.pageRequestParameter :" , pageRequestParameter);
		return new BaseResponse<List<BoardDTO>>(boardService.paginationSearchList(pageRequestParameter));
	}

	
	
	/** 3. 게시판 페이징 검색처리 목록리턴 - 페이징 검색처리 두번째방법 (전체 갯수 구함) */
	@GetMapping({"/pageSearchList2"})
	@ApiOperation(value="목록조회 - MysqlPageMaker 사용 ", notes="3.게시판 페이징 검색처리 목록리턴 ")
	public BaseResponse<List<BoardDTO>> paginationSearchList2(
			@ApiParam MysqlPageMaker pageMaker
			) throws Exception{
		
		int totalCount =boardService.getTotalCount(pageMaker);
		pageMaker.setTotalCount(totalCount);
		
		log.info("1.pageMaker :  {} " , pageMaker);
		log.info("2.totalCount :  {}" , totalCount);		
		return new BaseResponse<List<BoardDTO>>(boardService.paginationSearchList2(pageMaker), pageMaker);
	}
	

	
	
	
	
	
	
	/** 게시판 상세보기 */
	@GetMapping("/{boardSeq}")
	@ApiOperation(value="상세조회", notes="게시물 번호에 해당하는 상세정보를 조회할수 있습니다.")
	@ApiImplicitParams({
		@ApiImplicitParam(name="boardSeq", value="게시물 번호", example = "1")
	})
	public BaseResponse<Board> get(@PathVariable int boardSeq) throws Exception {		
		return new BaseResponse<Board>(boardService.get(boardSeq));
	}
	
	

	/** 게시판 등록/수정처리 */
	@PutMapping("/save")
	@RequestConfig(loginCheck = true)
	@ApiOperation(value="등록/수정처리", notes="신규 게시물 저장 및 기존 게시물 업데이트가 가능합니다.")
	@ApiImplicitParams({
		@ApiImplicitParam(name="boardSeq", value="게시물번호", example = "1"),
		@ApiImplicitParam(name="boardType", value="게시판종류", example = "NOTICE"),
		@ApiImplicitParam(name="title", value="제목", example = "spring"),
		@ApiImplicitParam(name="contents", value="내용", example="spring 강좌"),
	})
	public BaseResponse<Integer> save(BoardInsertDTO boardInsertDTO)  throws Exception {
		boardService.save(boardInsertDTO);
		return new BaseResponse<Integer>(boardInsertDTO.getBoardSeq());
	}
	
	

	/** 게시판 삭제처리 */
	@DeleteMapping("/{boardSeq}")
	@ApiOperation(value="게시판 삭제처리", notes="게시물 번호에 해당하는 정보를 삭제합니다.")
	@ApiImplicitParams({
		@ApiImplicitParam(name="boardSeq", value="게시물 번호", example = "1")
	})
	public BaseResponse<Boolean>  delete(@PathVariable int boardSeq)  throws Exception{
		return  new BaseResponse<Boolean>(boardService.delete(boardSeq));
	}
	
	
	/**대용량 등록 처리1 **/
	@ApiOperation(value="대용량 등록처리1" , notes="대용량 등록처리1")
	@PutMapping("/saveList1")
	public BaseResponse<Boolean> saveList1() throws Exception{
		int count=0;
		//테스트를 위한 랜덤 1000 건의 데이터를 생성
		List<BoardInsertDTO> list=new ArrayList<BoardInsertDTO>();
		while(true){
			count++;
			String title=RandomStringUtils.randomAlphabetic(10);
			String contents=RandomStringUtils.randomAlphabetic(10);
			BoardTypeInsert boardTypeInsert=null;
			if(count%4==0) {
				boardTypeInsert=BoardTypeInsert.NOTICE;
			}if(count%5==0)  {
				boardTypeInsert=BoardTypeInsert.INQUIRY;
			}else {
				if(count%2==0) {
					boardTypeInsert=BoardTypeInsert.FAQ;	
				}else {
					boardTypeInsert=BoardTypeInsert.NOTICE;	
				}				
			}
			list.add(BoardInsertDTO.builder().boardType(boardTypeInsert).title(title).contents(contents).build());
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
	public BaseResponse<Boolean> saveList2() throws Exception{
		int count=0;
		//테스트를 위한 랜덤 1000 건의 데이터를 생성
		List<BoardInsertDTO> list=new ArrayList<BoardInsertDTO>();
		while(true){
			count++;
			String title=RandomStringUtils.randomAlphabetic(10);
			String contents=RandomStringUtils.randomAlphabetic(10);
			
			BoardTypeInsert boardTypeInsert=null;
			if(count%4==0) {
				boardTypeInsert=BoardTypeInsert.NOTICE;
			}if(count%5==0)  {
				boardTypeInsert=BoardTypeInsert.INQUIRY;
			}else {
				if(count%2==0) {
					boardTypeInsert=BoardTypeInsert.FAQ;	
				}else {
					boardTypeInsert=BoardTypeInsert.NOTICE;	
				}				
			}
			
			list.add(BoardInsertDTO.builder().boardType(boardTypeInsert).title(title).contents(contents).build());
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
