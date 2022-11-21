package board.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import board.dto.BoardDto;
import board.service.BoardService;

/*Rest API 방식으로 게시판 구현
(Representational State Transfer
 Application programming Interface)
- Rest 구조의 제약조건을 준수하는 API방식
*/

//@RestController?
//@Controller + @ResponseBody
//@ResponseBody?
//클라이언트의 요청에 대한 응답시 데이터를 JSON형태로 
//변환하여 응답처리한다.
@RestController
public class RestBoardApiController {

	@Autowired
	private BoardService boardService;
	
	//Rest API 방식의 게시물 목록
	@RequestMapping(value="/api/board",method=RequestMethod.GET)
	public List<BoardDto> openboardList() throws Exception{
		return boardService.selectBoardList();
	}
	
	//신규 게시물 추가 처리
	//@RequestBody?
	//클라이언트에서 전송하는 JSON형태의 데이터를
	//Java 객체에 매핑하여 전달해주는 어노테이션
	@RequestMapping(value="/api/board/write",method=RequestMethod.POST)
	public void insertBoard(@RequestBody BoardDto board) throws Exception {
		boardService.insertBoard(board,null);
	}
	
	//특정 게시물 번호에 대한 상세내역 처리
	@RequestMapping(value="/api/board/{boardIdx}",method=RequestMethod.GET)
	public BoardDto openBoardDetail(@PathVariable("boardIdx") int boardIdx) throws Exception {
		return boardService.selectBoardDetail(boardIdx);
	}
	
	//특정 게시물 수정 처리
	@RequestMapping(value="/api/board/{boardIdx}",method=RequestMethod.PUT)
	public String updateBoard(@RequestBody BoardDto board) throws Exception {
		boardService.updateBoard(board);
		
		return "redirect:/api/board";
	}
	
	//특정 게시물 삭제 처리
	@RequestMapping(value="/api/board/{boardIdx}",method=RequestMethod.DELETE)
	public String deleteBoard(@PathVariable("boardIdx") int boardIdx) throws Exception {
		boardService.deleteBoard(boardIdx);
		
		return "redirect:/api/board";
	}
}






