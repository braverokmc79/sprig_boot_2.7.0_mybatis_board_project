package kr.so.songjava.mvc.repository;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import kr.so.songjava.mvc.domain.dto.BoardDTO;
import kr.so.songjava.mvc.domain.dto.BoardInsertDTO;
import kr.so.songjava.mvc.domain.dto.BoardSearchParameter;
import kr.so.songjava.mvc.domain.entity.Board;

/** 게시판 Repository */
@Repository
public interface BoardRepository {

	public List<BoardDTO> getList(BoardSearchParameter boardSearchParameter);
	
	public Board get(int boardSeq);
	
	public int save(BoardInsertDTO boardInsertDTO);

	public void saveList(Map<String, Object> paramMap);
	
	public int update(BoardInsertDTO boardInsertDTO);
	
	public int delete(int boardSeq);
	

		
}
