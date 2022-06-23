package kr.so.songjava.mvc.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import kr.so.songjava.mvc.domain.Board;

/** 게시판 Repository */
@Repository
public interface BoardRepository {

	public List<Board> getList();
	
	public Board get(int boardSeq);
	
	public int save(Board board);
	
	public int update(Board board);
	
	public int delete(int boardSeq);
		
}
