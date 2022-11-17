package board.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import board.dto.BoardDto;
import board.mapper.BoardMapper;

//구현객체
@Service
//모든 Task가 성공시 Commit하고 하나라도 실패하면 Rollback 처리한다.
@Transactional
public class BoardServiceImpl implements BoardService {

	//BoardMapper 인터페이스 자동 주입
	@Autowired
	private BoardMapper boardMapper;
	
	//오버라이딩
	@Override
	public List<BoardDto> selectBoardList() throws Exception {
		return boardMapper.selectBoardList();
	}

	//신규 게시물 추가 처리
	@Override
	public void insertBoard(BoardDto board) throws Exception {
		boardMapper.insertBoard(board);
	}

	//특정 게시물 상세보기
	@Override
	public BoardDto selectBoardDetail(int boardIdx) throws Exception {
		
		
		//조회수 증가 처리
		boardMapper.updateHitCount(boardIdx);
		
		//강제로 예외 발생시킴
		int i = 10 / 0 ;
		System.out.println(i);
		
		//특정 게시물 번호에 대한 내역 리턴
		BoardDto board = boardMapper.selectBoardDetail(boardIdx);
		
		return board;
	}

	//특정 게시물 수정 처리
	@Override
	public void updateBoard(BoardDto board) throws Exception {
		
		boardMapper.updateBoard(board);
	}

	//특정 게시물 삭제 처리
	@Override
	public void deleteBoard(int boardIdx) throws Exception {
		
		boardMapper.deleteBoard(boardIdx);
	}

}



