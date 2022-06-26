package kr.net.macaronics.mvc.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kr.net.macaronics.mvc.domain.dto.BoardDTO;
import kr.net.macaronics.mvc.domain.dto.BoardInsertDTO;
import kr.net.macaronics.mvc.domain.dto.BoardSearchParameter;
import kr.net.macaronics.mvc.repository.BoardRepository;
import kr.net.macaronics.utils.pagination.PageRequestParameter;
import kr.net.macaronics.utils.pagination2.MysqlPageMaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.net.macaronics.mvc.domain.entity.Board;

/** 게시판 서비스 */
@Service
public class BoardSevice {

	@Autowired
	private BoardRepository boardRepository;

	/** 게시판 목록리턴 */
	public List<BoardDTO> getList(BoardSearchParameter boardSearchParameter) throws Exception{
		return boardRepository.getList(boardSearchParameter);
	}
	
	/** 게시판 상세보기 */
	public Board get(int boardSeq) throws Exception {		
		return boardRepository.get(boardSeq);
	}

	/** 게시판 저장 */
	public int save(BoardInsertDTO boardInsertDTO) throws Exception {
		Board board =boardRepository.get(boardInsertDTO.getBoardSeq());
		if(board==null) {
			boardRepository.save(boardInsertDTO);
		}else{
			boardRepository.update(boardInsertDTO);
		}
		return boardInsertDTO.getBoardSeq();
	}
	
	/** 게시판 업데이트 */
	public int update(BoardInsertDTO boardInsertDTO)throws Exception {
		return boardRepository.update(boardInsertDTO);
	}
	
	/** 게시판 삭제 */
	public Boolean delete(int boardSeq) throws Exception{
		return boardRepository.delete(boardSeq)==1?true:false;
	}
	
	
	/** 단순 반복문을 이용한 등록 처리 */
	public void saveList1(List<BoardInsertDTO> list) throws Exception{
		for(BoardInsertDTO dto : list) {
			boardRepository.save(dto);
		}
	}
	
	
	/** 100개씩 배열에 담아서 일괄 등록 처리 **/
	public void saveList2(List<BoardInsertDTO> boardList) throws Exception{
		Map<String, Object> paramMap=new HashMap<>();
		paramMap.put("boardList", boardList);
		boardRepository.saveList(paramMap);
	}

	
	public List<BoardDTO> paginationSearchList(PageRequestParameter<BoardSearchParameter> pageRequestParameter) throws Exception {
		return boardRepository.paginationSearchList(pageRequestParameter);
	}

	
	/** MysqlPageMaker 사용 */
	public List<BoardDTO> paginationSearchList2(MysqlPageMaker pageMaker) throws Exception{
		return boardRepository.paginationSearchList2(pageMaker);
	}

	/** 전체 갯수구함  */
	public int getTotalCount(MysqlPageMaker pageMaker) throws Exception {
		return boardRepository.getTotalCount(pageMaker);
	}


}
