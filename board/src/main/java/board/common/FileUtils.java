package board.common;

import java.io.File;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import board.dto.BoardFileDto;


//Bean등록을 처리하는 어노테이션
@Component
public class FileUtils {

	public List<BoardFileDto> parseFileInfo(int boardIdx, 
			MultipartHttpServletRequest multipartHttpServletRequest) throws Exception{
		
		//첨부 파일이 없으면
		if(ObjectUtils.isEmpty(multipartHttpServletRequest)) {
			return null;
		}
		
		//첨부 파일이 있으면 ArrayList에 추가한다
		List<BoardFileDto> fileList = new ArrayList<>();
		//현재시간을 원하는 형태로 변환
		DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyyMMdd");
		//현재날짜를 리턴
		ZonedDateTime current = ZonedDateTime.now();
		String path = "images/" + current.format(format);
		File file = new File(path);
		
		//업로드 폴더를 자동 생성
		if(file.exists() == false) {
			file.mkdirs();
		}
		
		//업로드 파일 내역을 Iterator에 대입한다.
		Iterator<String> iterator = multipartHttpServletRequest.getFileNames();
		
		String newFileName, originalFileExtension, contentType;
		
		while(iterator.hasNext()){
			List<MultipartFile> list = multipartHttpServletRequest.getFiles(iterator.next());
			
			for(MultipartFile multipartFile : list) {
				//첨부 파일이 있으면
				if(multipartFile.isEmpty() == false) {
					//컨텐츠 타입을 가져온다
					contentType = multipartFile.getContentType();
					if(ObjectUtils.isEmpty(contentType)) {
						break;
					}
					else {
						//MIME에 따라 업로드 파일 확장자를 지정
						//파일 확장자를 .jpg로 만들어 줌
						if(contentType.contains("image/jpeg")) {
							originalFileExtension = ".jpg";
						}
						else if(contentType.contains("image/png")) {
							originalFileExtension = ".png";
						}
						else if(contentType.contains("image/gif")) {
							originalFileExtension = ".gif";
						}
						else {
							break;
						}
					}
					
					//새로운 업로드 파일명을 생성
					//중복을 배제하기 위해 시간을 파일명에 반영 시켜준다.
					newFileName = Long.toString(System.nanoTime()) +
							originalFileExtension;
					BoardFileDto boardFile = new BoardFileDto();
					
					boardFile.setBoardIdx(boardIdx);//게시물번호
					boardFile.setFileSize(multipartFile.getSize());//업로드 파일크기
					boardFile.setOrginalFileName(multipartFile.getOriginalFilename());//파일명
					boardFile.setStoredFilePath(path + "/" + newFileName);//저장위치
					
					file = new File(path + "/" + newFileName);
					//지정한 폴더에 업로드 처리
					multipartFile.transferTo(file);
				}
			}
		}
		return fileList;
	}
}








