package board.service;

import java.util.List;

import org.springframework.web.multipart.MultipartHttpServletRequest;

import board.entity.BoardEntity;
import board.entity.BoardFileEntity;

public interface JpaBoardService {

	//게시물 목록처리
	List<BoardEntity> selectBoardList() throws Exception;
	
	//게시물 등록& 수정 처리
	void saveBoard(BoardEntity board, 
			MultipartHttpServletRequest multipartHttpServletRequest)
				throws Exception;
	
	//특정 게시물 번호 상세보기 처리
	BoardEntity selectBoardDetail(int boardIdx) throws Exception;
	
	//특정 게시물 삭제 처리
	void deleteBoard(int boardIdx);
	
	//특적 게시물 번호에 대한 특정첨부파일 내역
	BoardFileEntity selectBoardFileInformation(int boardIdx, int idx)
	throws Exception;
}
