package board.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class BoardDto {
	
	private int boardIdx;//게시물번호
	private String title;//제목
	private String contents;//내용
	private int hitCnt;//조회수
	private String creatorId;//작성자
	private LocalDateTime createdDatetime;//작성일
	private String updaterId;//수정자
	private LocalDateTime updatedDatetime;//수정일
	
}




