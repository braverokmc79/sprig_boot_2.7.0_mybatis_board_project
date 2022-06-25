package kr.so.songjava.mvc.repository;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import kr.so.songjava.mvc.domain.dto.BoardDTO;
import kr.so.songjava.mvc.domain.entity.Board;

/** 게시판 Repository */
@Repository
public interface BoardRepository {

	public List<Board> getList();
	
	public Board get(int boardSeq);
	
	public int save(BoardDTO boardDto);

	public void saveList(Map<String, Object> paramMap);
	
	public int update(BoardDTO boardDto);
	
	public int delete(int boardSeq);
	

		
}
