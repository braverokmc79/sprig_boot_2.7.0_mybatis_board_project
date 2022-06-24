package kr.so.songjava.mvc.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import kr.so.songjava.mvc.domain.dto.BoardDto;
import kr.so.songjava.mvc.domain.entity.Board;

/** 게시판 Repository */
@Repository
public interface BoardRepository {

	public List<Board> getList();
	
	public Board get(int boardSeq);
	
	public int save(BoardDto boardDto);
	
	public int update(BoardDto boardDto);
	
	public int delete(int boardSeq);
		
}
