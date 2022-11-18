package board.controller;

import java.io.File;
import java.net.URLEncoder;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import board.dto.BoardDto;
import board.dto.BoardFileDto;
import board.service.BoardService;

//Rest방식
@Controller
public class RestBoardController {

	//자동 주입 처리
	@Autowired
	private BoardService boardService;
	
	//게시물 목록 처리
	@RequestMapping(value="/board", method=RequestMethod.GET)
	public ModelAndView openBoardList() throws Exception{
		
		ModelAndView mv = new ModelAndView("/board/restBoardList");
		
		List<BoardDto> list = boardService.selectBoardList();
		mv.addObject("list",list);
		
		return mv;
	}
	
	//신규 게시물 등록 처리 화면
	@RequestMapping(value="/board/write", method=RequestMethod.GET)
	public String openBoardWrite() throws Exception{
		return "/board/restBoardWrite";
	}
	
	//게시물 등록 화면에서 등록버튼을 클릭 처리
	@RequestMapping(value="/board/write", method=RequestMethod.POST)
	public String insertBoard(BoardDto board, MultipartHttpServletRequest
			multipartHttpServletRequest) throws Exception{
		
		//신규 게시물 등록 처리
		boardService.insertBoard(board,multipartHttpServletRequest);
		
		//게시물 목록으로 이동
		return "redirect:/board";
	}
	
	//특정 게시물 번호 상세내역 처리
	@RequestMapping(value="/board/{boardIdx}", method=RequestMethod.GET)
	public  ModelAndView openBoardDetail(@PathVariable("boardIdx") 
			int boardIdx) throws Exception{
		
		//상세보기 화면 지정
		ModelAndView mv = new ModelAndView("/board/restBoardDetail");
		
		//특정 게시물 번호에 대한 내역을 변수에 대입
		BoardDto board = boardService.selectBoardDetail(boardIdx);
		
		return mv;
	}
	
	//특정 게시물 수정 처리
	@RequestMapping(value="/board/{boardIdx}", method=RequestMethod.PUT)
	public  String updateBoard(BoardDto board) throws Exception{
		
		//게시물 수정 처리
		boardService.updateBoard(board);
		
		//게시물 목록으로 이동
		return "redirect:/board";
	}
	
	//특정 게시물 삭제 처리
	@RequestMapping(value="/board/{boardIdx}", method=RequestMethod.DELETE)
	public  String deleteBoard(@PathVariable("boardIdx") 
			int boardIdx) throws Exception{
		
		//게시물 삭제 처리
		boardService.deleteBoard(boardIdx);
		
		//게시물 목록으로 이동
		return "redirect:/board";
	}
	
	//특정 파일 다운로드 처리
	@RequestMapping(value="/board/file",method=RequestMethod.GET)
	public void downloadBoardFile(
			@RequestParam int idx,
			@RequestParam int boardIdx,
			HttpServletResponse response) throws Exception {
		BoardFileDto boardFile = boardService.selectBoardFileInformation(idx, boardIdx);
		
		if(ObjectUtils.isEmpty(boardFile) == false) {
			
			String fileName = boardFile.getOriginalFileName();
			
			//첨부파일을 바이트단위로 읽어와서 배열에 대입
			byte[] files = FileUtils.readFileToByteArray(new File(boardFile.getStoredFilePath()));
			
			//첨부파일 다운로드 처리
			response.setContentType("application/octet-stream");
			response.setContentLength(files.length);
			response.setHeader("Content-Disposition","attachment; fileName=\"" + URLEncoder.encode(fileName,"UTF-8") + "\";");
			response.setHeader("Content-Transfer-Encoding","binary");
			
			response.getOutputStream().write(files);
			response.getOutputStream().flush();
			response.getOutputStream().close();
		
		}
	}	
	
}







