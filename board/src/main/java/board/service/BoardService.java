package board.service;

import java.util.List;

import org.springframework.web.multipart.MultipartHttpServletRequest;

import board.dto.BoardDto;
import board.dto.BoardFileDto;

//Controller 와 DAO 객체와의 결합도를 낮추어
//상호 의존성을 줄이기 위해 중간에 서비스 기능을 하는
//객체를 선언(loose coupling=느슨한 결합)
//모듈화를 통해 언제든지 재사용 가능
public interface BoardService {

	//게시물 전체목록 추상메서드
	List<BoardDto> selectBoardList() throws Exception;
	
	//신규 게시물 추가 처리 (파일)
	void insertBoard(BoardDto board,MultipartHttpServletRequest 
			multipartHttpServletRequest) throws Exception;
	
	//특정 게시물 상세 보기 처리
	BoardDto selectBoardDetail(int boardIdx) throws Exception;

	//특정 게시물 수정 처리
	void updateBoard(BoardDto board) throws Exception;
	
	//특정 게시물 삭제 처리
	void deleteBoard(int boardIdx) throws Exception;
	
	BoardFileDto selectBoardFileInformation(int idx, int boardIdx) throws Exception;
}






