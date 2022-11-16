package board.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import board.dto.BoardDto;
import board.mapper.BoardMapper;

//구현객체
@Service
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
		
		//특정 게시물 번호에 대한 내역 리턴
		BoardDto board = boardMapper.selectBoardDetail(boardIdx);
		
		return board;
	}

}



