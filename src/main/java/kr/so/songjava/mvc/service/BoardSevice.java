package kr.so.songjava.mvc.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

import kr.so.songjava.configuration.BaseCodeLabelEnumJsonSerializer;
import kr.so.songjava.mvc.domain.dto.BoardDTO;
import kr.so.songjava.mvc.domain.entity.Board;
import kr.so.songjava.mvc.domain.enums.BaseCodeLabelEnum;
import kr.so.songjava.mvc.repository.BoardRepository;

/** 게시판 서비스 */
@Service
public class BoardSevice {

	@Autowired
	private BoardRepository boardRepository;

	/** 게시판 목록리턴 */
	public List<Board> getList(){
		return boardRepository.getList();
	}
	
	/** 게시판 상세보기 */
	public Board get(int boardSeq) {		
		return boardRepository.get(boardSeq);
	}

	/** 게시판 저장 */
	public int save(BoardDTO boardDto) {
		Board board =boardRepository.get(boardDto.getBoardSeq());
		if(board==null) {
			boardRepository.save(boardDto);
		}else{
			boardRepository.update(boardDto);
		}
		return boardDto.getBoardSeq();
	}
	
	/** 게시판 업데이트 */
	public int update(BoardDTO board) {
		return boardRepository.update(board);
	}
	
	/** 게시판 삭제 */
	public Boolean delete(int boardSeq) {
		return boardRepository.delete(boardSeq)==1?true:false;
	}
	
	
	/** 단순 반복문을 이용한 등록 처리 */
	public void saveList1(List<BoardDTO> list) {
		for(BoardDTO dto : list) {
			boardRepository.save(dto);
		}
	}
	
	
	/** 100개씩 배열에 담아서 일괄 등록 처리 **/
	public void saveList2(List<BoardDTO> boardList) {
		Map<String, Object> paramMap=new HashMap<>();
		paramMap.put("boardList", boardList);
		boardRepository.saveList(paramMap);
	}


}
