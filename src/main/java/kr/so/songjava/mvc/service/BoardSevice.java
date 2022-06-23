package kr.so.songjava.mvc.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.so.songjava.mvc.domain.Board;
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
	public int save(Board parameter) {
		Board board =boardRepository.get(parameter.getBoardSeq());
		if(board==null) {
			boardRepository.save(parameter);
		}else{
			boardRepository.update(parameter);
		}
		return parameter.getBoardSeq();
	}
	
	/** 게시판 업데이트 */
	public int update(Board board) {
		return boardRepository.update(board);
	}
	
	/** 게시판 삭제 */
	public int delete(int boardSeq) {
		return boardRepository.delete(boardSeq);
	}
	
	
	
}
