package board.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import board.common.FileUtils;
import board.entity.BoardEntity;
import board.entity.BoardFileEntity;
import board.repository.JpaBoardRepository;

@Service
public class JpaBoardServiceImpl implements JpaBoardService {

	@Autowired
	JpaBoardRepository jpaBoardRepository;
	
	@Autowired
	FileUtils fileUtils;
	
	//게시물 목록 처리
	//findAllByOrderByBoardIdxDesc?
	//JPQL(Java Persistence Query Language)
	@Override
	public List<BoardEntity> selectBoardList() throws Exception {
		
		//JPQL을 이용해서 게시물번호 내림차순으로 게시물 목록을 리턴
		return jpaBoardRepository.findAllByOrderByBoardIdxDesc();
	}

	//신규 게시물 등록 & 수정 처리
	@Override
	public void saveBoard(BoardEntity board, MultipartHttpServletRequest multipartHttpServletRequest) throws Exception {
		
		board.setCreatorId("admin");
		
		//첨부파일을 리턴받아 List 변수에 대입
		List<BoardFileEntity> list = fileUtils.parseFileInfo(multipartHttpServletRequest);
		
		//List에 데이터가 존재하면
		if(CollectionUtils.isEmpty(list) == false) {
			board.setFileList(list);
		}
		
		//신규 게시물 등록 & 수정 처리
		jpaBoardRepository.save(board);
	}

	//특정 게시물 번호에 대한 상세내역 처리
	@Override
	public BoardEntity selectBoardDetail(int boardIdx) throws Exception {
		
		//Optional 사용 이유
		//Java 8버전 이후부터 Object의 
		//NullPointerException 예외 발생을 해결
		Optional<BoardEntity> optional = jpaBoardRepository.findById(boardIdx);
		
		//Object의 값이 존재하는지 미리 체크
		if(optional.isPresent()) {
			
			//조회수를 증가 처리
			BoardEntity board = optional.get();
			board.setHitCnt(board.getHitCnt() + 1);
			
			jpaBoardRepository.save(board);
			
			return board;
			
		}else {
			//강제로 예외 발생시킴
			throw new NullPointerException();
		}
	}

	//특정 게시물 번호에 대한 삭제 처리
	@Override
	public void deleteBoard(int boardIdx) throws Exception {
		
		jpaBoardRepository.deleteById(boardIdx);
	}

	//특정 게시물 번호,첨부파일 일련번호에 대한
	//첨부파일 상세내역 처리
	@Override
	public BoardFileEntity selectBoardFileInformation(int boardIdx, int idx) throws Exception {
		
		BoardFileEntity boardFile = jpaBoardRepository.findBoardFile(boardIdx,idx);
		
		return boardFile;
	}

}
