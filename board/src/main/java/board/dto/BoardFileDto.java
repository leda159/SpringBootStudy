package board.dto;

import lombok.Data;

@Data
public class BoardFileDto {
	
	private int idx;//일련번호
	private int boardIdx;//게시물번호
	private String originalFileName;//원본파일명
	private String storedFilePath;//파일저장경로
	private long fileSize;//파일크기
}
