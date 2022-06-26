package kr.net.macaronics.mvc.repository;

import java.util.List;
import java.util.Map;

import kr.net.macaronics.mvc.domain.dto.BoardDTO;
import kr.net.macaronics.mvc.domain.dto.BoardInsertDTO;
import kr.net.macaronics.mvc.domain.dto.BoardSearchParameter;
import kr.net.macaronics.utils.pagination.PageRequestParameter;
import kr.net.macaronics.utils.pagination2.MysqlPageMaker;
import org.springframework.stereotype.Repository;

import kr.net.macaronics.mvc.domain.entity.Board;

/** 게시판 Repository */
@Repository
public interface BoardRepository {

	public List<BoardDTO> getList(BoardSearchParameter boardSearchParameter) throws Exception;
	
	public Board get(int boardSeq) throws Exception;
	
	public int save(BoardInsertDTO boardInsertDTO) throws Exception;

	public void saveList(Map<String, Object> paramMap) throws Exception;
	
	public int update(BoardInsertDTO boardInsertDTO) throws Exception;
	
	public int delete(int boardSeq) throws Exception;

	public List<BoardDTO> paginationSearchList(PageRequestParameter<BoardSearchParameter> pageRequestParameter) throws Exception;

	public List<BoardDTO> paginationSearchList2(MysqlPageMaker pageMaker) throws Exception;

	public int getTotalCount(MysqlPageMaker pageMaker)  throws Exception;
	
	

		
}
