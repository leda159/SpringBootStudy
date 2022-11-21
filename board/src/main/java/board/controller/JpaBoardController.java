package board.controller;

import java.io.File;
import java.net.URLEncoder;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import board.entity.BoardEntity;
import board.entity.BoardFileEntity;

@Controller
public class JpaBoardController {

	@Autowired
	private JpaBoardService jpaBoardService;
	
	//게시물 목록
	@RequestMapping(value="/jpa/board", method=RequestMethod.GET)
	public ModelAndView openBoardList() throws Exception{
		ModelAndView mv = new ModelAndView("/board/jpaBoardList");
		
		List<BoardEntity> list = jpaBoardService.selectBoardList();
		mv.addObject("list",list);
		
		return mv;
	}
	
	//신규 게시물 입력 화면 처리
	@RequestMapping(value="/jpa/board/write",method=RequestMethod.GET)
	public String openBoardWrite() throws Exception{
		return "/board/jpaBoardWrite";
	}
	
	//신규 게시물 등록 처리
	@RequestMapping(value="/jpa/board/write", method=RequestMethod.POST)
	public String writeBoard(BoardEntity board,
			MultipartHttpServletRequest multipartHttpServletRequest) throws Exception{
		
		//신규 게시물 등록 처리
		jpaBoardService.saveBoard(board,multipartHttpServletRequest);
		
		//게시물 목록으로 이동
		return "redirect:/jpa/board";
	}
	
	//특정 게시물 상세보기 처리
	@RequestMapping(value="/jpa/board/{boardIdx}", method=RequestMethod.GET)
	public ModelAndView openBoardDetail(@PathVariable("boardIdx") int boardIdx)
			throws Exception{
		ModelAndView mv = new ModelAndView("/board/jpaBoardDetail");
		BoardEntity board = jpaBoardService.selectBoardDetail(boardIdx);
		mv.addObject("board",board);
		
		return mv;
	}
	
	//특정 게시물 수정 처리
	@RequestMapping(value="/jpa/board/{boardIdx}", method=RequestMethod.PUT)
	public String updateBoard(BoardEntity board) throws Exception{
		jpaBoardService.saveBoard(board,null);
		return "redircet:/jpa/board";
	}
	
	//특정 게시물 삭제 처리
	@RequestMapping(value="/jpa/board/{boardIdx}", method=RequestMethod.DELETE)
	public String deleteBoard(@PathVariable("boardIdx") int boardIdx)
			throws Exception{
		jpaBoardService.deleteBoard(boardIdx);
		return "redircet:/jpa/board";
	}
	
	//파일 업로드
	@RequestMapping(value="/jpa/board/file", method=RequestMethod.GET)
	public void downloadBoardFile(int boardIdx, int idx, HttpServletResponse response)
		throws Exception{
		BoardFileEntity file = jpaBoardService.selectBoardfileInformation(boardIdx, idx);
		
		byte[] files = FileUtils.readFileToByteArray(new File(file.getStoredFilePath()));
		

		byte[] files = FileUtils.readFileToByteArray(new File(file.getStoredFilePath()));
		
		response.setContentType("application/octet-stream");
		response.setContentLength(files.length);
		response.setHeader("Content-Disposition","attachment; fileName=\"" + URLEncoder.encode(fileName,"UTF-8") + "\";");
		response.setHeader("Content-Transfer-Encoding","binary");
		
		response.getOutputStream().write(files);
		response.getOutputStream().flush();
		response.getOutputStream().close();
	}
}















