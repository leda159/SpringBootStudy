package board.controller;

import java.io.File;
import java.net.URLEncoder;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import board.dto.BoardDto;
import board.dto.BoardFileDto;
import board.service.BoardService;

@Controller
public class BoardController {

	//로깅 선언
	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	//서비스 기능을 수행하는 클래스 선언
	@Autowired
	private BoardService boardService;
	
	//http://localhost:8080/board/openBoardList.do
	//ModelAndView?
	//실행하려는 URL + Model 데이터 선언
	@RequestMapping("/board/openBoardList.do")
	public ModelAndView openBoardList() throws Exception {
		
		
		log.debug("openBoardList.do 실행");
		
		//templates 폴더 밑에 board폴더밑에
		//boardList.html을 찾는다.
		ModelAndView mv = 
				new ModelAndView("/board/boardList");
		
		//게시물 목록을 리턴받아서 List 구조의 변수에 대입
		List<BoardDto> list = 
				boardService.selectBoardList();
		
		//속성을 지정
		mv.addObject("list",list);
		
		return mv;
		
	}
	
	//p75 게시물 등록 처리
	@RequestMapping("/board/openBoardWrite.do")
	public String openBoardWrite() {
		return "/board/boardWrite";
	}
	
	//MultipartHttpServletRequest?
	//ServletRequest를 상속받아 구현된 인터페이스로
	//파일 업로드와 관련된 여러 메서드 제공
	@RequestMapping("/board/insertBoard.do")
	public String insertBoard(BoardDto board, MultipartHttpServletRequest 
			multipartHttpServletRequest) throws Exception {
		
		//신규 게시물 등록 처리
		boardService.insertBoard(board, multipartHttpServletRequest );
		
		//게시물 목록 리스트로 이동
		return "redirect:/board/openBoardList.do";
	}
	
	//p79 게시물 상세보기 처리
	@RequestMapping("/board/openBoardDetail.do")
	public ModelAndView openBoardDetail(
			@RequestParam("board_idx") int boardIdx) throws Exception {
		
		ModelAndView mv = 
			new ModelAndView("/board/boardDetail");
		
		//특정 게시물번호에 대한 게시물내역을 변수에 대입
		BoardDto board = boardService.selectBoardDetail(boardIdx);
		
		//board 속성 지정
		mv.addObject("board",board);
		
		return mv;
	}
	
	//게시물 수정 처리
	@RequestMapping("/board/updateBoard.do")
	public String updateBoard(BoardDto board) throws Exception {

		boardService.updateBoard(board);
		
		//게시물 목록으로 이동
		return "redirect:/board/openBoardList.do";
	}
	
	//게시물 삭제 처리
	@RequestMapping("/board/deleteBoard.do")
	public String deleteBoard(int boardIdx) throws Exception {
		
		boardService.deleteBoard(boardIdx);
		
		return "redirect:/board/openBoardList.do";
	}
	
	//첨부파일 다운로드 처리
	@RequestMapping("/board/downloadBoardFile.do")
	public void downloadBoardFile(
			@RequestParam int idx, //특정 첨부파일 일련번호
			@RequestParam int boardIdx, //특정 게시물 번호
			
			//사용자에게 응답처리할 데이터를 가지고 있는 객체
			HttpServletResponse response)throws Exception{
		
		//특정 첨부파일 내역을 가져와서 변수에 대입
		BoardFileDto boardFile = boardService.selectBoardFileInformation(idx, boardIdx);
		
		if(ObjectUtils.isEmpty(boardFile) == false) {
			String fileName = boardFile.getOriginalFileName();
			
			//첨부파일을 byte단위로 읽어와서 배열에 대입을 한다.
			byte[] files = FileUtils.readFileToByteArray(new File(boardFile.getStoredFilePath()));
			
			//다운로드 처리
			response.setContentType("application/octet-stream");
			response.setContentLength(files.length);
			response.setHeader("Content-Disposition", "attachment; fileName=\"" +
					URLEncoder.encode(fileName, "UTF-8")+"\";");
			response.setHeader("Content-Transfer-Encoding","binary");
			response.getOutputStream().write(files);
			response.getOutputStream().flush();
			response.getOutputStream().close();
			}
	}
}









