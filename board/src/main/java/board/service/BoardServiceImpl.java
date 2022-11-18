package board.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import board.common.FileUtils;
import board.dto.BoardDto;
import board.dto.BoardFileDto;
import board.mapper.BoardMapper;

//구현객체
@Service
//모든 Task가 성공시 Commit하고 하나라도 실패하면
//Rollback 처리한다.
/* @Transactional */
public class BoardServiceImpl implements BoardService {

	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	//BoardMapper 인터페이스 자동 주입
	@Autowired
	private BoardMapper boardMapper;
	
	//p137
	@Autowired
	private FileUtils fileUtils;
	
	//오버라이딩
	@Override
	public List<BoardDto> selectBoardList() throws Exception {
		return boardMapper.selectBoardList();
	}

	//신규 게시물 추가 처리
	//MultipartHttpServletRequest?
	//게시물 등록 화면에서 input 태그의 type이
	//file인 속성의 이름을 가져오는등 업로드 파일 정보를
	//가짐
	@Override
	public void insertBoard(BoardDto board,
			MultipartHttpServletRequest multipartHttpServletRequest) throws Exception {
		
		boardMapper.insertBoard(board);
		
		List<BoardFileDto> list = fileUtils.parseFileInfo(board.getBoardIdx(),multipartHttpServletRequest);
		
		if(CollectionUtils.isEmpty(list) == false) {
			boardMapper.insertBoardFileList(list);
		}
		
	}

	//특정 게시물 상세보기
	@Override
	public BoardDto selectBoardDetail(int boardIdx) throws Exception {
		
	
		//특정 게시물 번호에 대한 내역 리턴
		BoardDto board = boardMapper.selectBoardDetail(boardIdx);
		
		//특정 게시물 번호에 대한 첨부파일 목록을 List구조의 변수에 대입
		List<BoardFileDto> fileList = boardMapper.selectBoardFileList(boardIdx);
		
		board.setFileList(fileList);
		
		//조회수 증가 처리
				boardMapper.updateHitCount(boardIdx);
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

	//특정 게시물 일련번호에 대한 첨부파일 내역가져오기
	@Override
	public BoardFileDto selectBoardFileInformation(int idx, int boardIdx) throws Exception {
		
		return boardMapper.selectBoardFileInformation(idx, boardIdx);
	}
	

}



