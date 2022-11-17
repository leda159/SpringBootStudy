package board.service;



import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import board.common.FileUtils;
import board.dto.BoardDto;
import board.dto.BoardFileDto;
import board.mapper.BoardMapper;

//구현객체
@Service
//모든 Task가 성공시 Commit하고 하나라도 실패하면 Rollback 처리한다.
@Transactional
public class BoardServiceImpl implements BoardService {
	
	//로깅 선언
	private Logger log = LoggerFactory.getLogger(this.getClass());

	//BoardMapper 인터페이스 자동 주입
	@Autowired
	private BoardMapper boardMapper;
	
	@Autowired
	private FileUtils fileUtils;
	
	//오버라이딩
	@Override
	public List<BoardDto> selectBoardList() throws Exception {
		return boardMapper.selectBoardList();
	}

	//신규 게시물 추가 처리
	//MultipartHttpServletRequest?
	//게시물 등록 화면에서 input 태그의 type이 file인 속성의 이름을
	//가져오는 등 업로드 파일 정보를 가짐
	@Override
	public void insertBoard(BoardDto board , 
		MultipartHttpServletRequest multipartHttpServletRequest) throws Exception {
		boardMapper.insertBoard(board);
		List<BoardFileDto> list = fileUtils.parseFileInfo(board.getBoardIdx(),
				multipartHttpServletRequest);
		if(CollectionUtils.isEmpty(list) == false) {
			boardMapper.insertBoardFileList(list);
		}
		
		
		//첨부파일 처리
		if(ObjectUtils.isEmpty(multipartHttpServletRequest) == false) {
			
			//Collection의 데이터를 순회하면서 가져온다.
			Iterator<String> iterator = multipartHttpServletRequest.getFileNames();
			String name;
			
			while(iterator.hasNext()) {
				//next : 다음 업로드 파일 내역을 가져온다.
				name = iterator.next();
				
			log.debug("file tag name : "+ name);
			
			List<MultipartFile> list = multipartHttpServletRequest.getFiles(name);
			
			for(MultipartFile multipartFile : list) {
				
				log.debug("파일 정보 시작");
				log.debug("파일 이름:" + multipartFile.getOriginalFilename());
				log.debug("파일 사이즈" + multipartFile.getSize() );
				log.debug("컨텐츠 타입" + multipartFile.getContentType());
				log.debug("파일 정보 종료.\n");
				
			}
		  }
		}
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



