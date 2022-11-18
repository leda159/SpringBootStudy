package board.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import board.dto.BoardDto;
import board.dto.BoardFileDto;

//MyBatis 처리 Mapper 인터페이스를 선언
@Mapper
public interface BoardMapper {

	//추상메서드
	List<BoardDto> selectBoardList() throws Exception;
	
	//신규 게시물 추가 처리
	void insertBoard(BoardDto board) throws Exception;

	//p80 조회수 증가
	void updateHitCount(int boardIdx) throws Exception;

	//특정 게시물 번호에 대한 상세내역
	BoardDto selectBoardDetail(int boardIdx) throws Exception;

	//특정 게시물 수정 처리
	void updateBoard(BoardDto board) throws Exception;
	
	//특정 게시물 삭제 처리
	void deleteBoard(int boardIdx) throws Exception;
	
	//파일 업로드
	void insertBoardFileList(List<BoardFileDto> list) throws Exception; 
	
	//특정 게시물 번호에 대한 첨부파일 목록
	List<BoardFileDto> selectBoardFileList(int boardIdx) throws Exception;
	
	//특정 게시물 일련번호에 대한 첨부파일 내역가져오기
	//@Param : 매개변수를 url에서 가져온다.
	//@PathVariable, @RequestParam
	BoardFileDto selectBoardFileInformation(
			@Param("idx") int idx,
			@Param("boardIdx") int boardIdx);

}




