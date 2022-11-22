package board.common;

import java.io.File;
import java.io.IOException;
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
import board.entity.BoardFileEntity;

//Bean등록을 처리하는 어노테이션
@Component
public class FileUtils {

	public List<BoardFileDto> parseFileInfo(int boardIdx,MultipartHttpServletRequest multipartHttpServletRequest) throws IllegalStateException, IOException{
		
		//첨부파일이 없으면 null 처리
		if(ObjectUtils.isEmpty(multipartHttpServletRequest)) {
			return null;
		}
		
		//첨부파일을 대입하기 위한 ArrayList 선언
		List<BoardFileDto> fileList = new ArrayList<>();
		
		//현재시간을 원하는 형태로 변환
		DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyyMMdd");
		
		//현재날짜를 리턴
		ZonedDateTime current = ZonedDateTime.now();
		String path = "images/" + current.format(format);
		File file = new File(path);
		
		//업로드 폴더 생성
		if(file.exists() == false) {
			file.mkdirs();
		}
		
		//업로드 파일 내역을 Iterator 에 대입
		Iterator<String> iterator = multipartHttpServletRequest.getFileNames();
		
		String newFileName,originalFileExtension,contentType;
		
		while(iterator.hasNext()) {
			
			List<MultipartFile> list = multipartHttpServletRequest.getFiles(iterator.next());
			
			for(MultipartFile multipartFile : list) {
				
				if(multipartFile.isEmpty() == false) {
					//컨텐츠 타입을 변수에 대입
					contentType = multipartFile.getContentType();
					
					if(ObjectUtils.isEmpty(contentType)) {
						break;
					}else {
						//MIME에 따라 업로드 파일 확장자를 지정
						if(contentType.contains("image/jpeg")) {
							originalFileExtension = ".jpg";
						}else if(contentType.contains("image/png")) {
							originalFileExtension = ".png";
						}else if(contentType.contains("image/gif")) {
							originalFileExtension = ".gif";
						}else {
							break;
						}
					}
					
					//새로운 업로드 파일명 생성
					//중복을 배제하기 위해 시간을 파일명에 반영
					newFileName = Long.toString(System.nanoTime()) + originalFileExtension;
					
					BoardFileDto boardFile = new BoardFileDto();
					
					boardFile.setBoardIdx(boardIdx);//게시물번호
					boardFile.setFileSize(multipartFile.getSize());//업로드파일크기
					boardFile.setOriginalFileName(multipartFile.getOriginalFilename());//업로드파일명
					boardFile.setStoredFilePath(path + "/" + newFileName);//저장위치

					fileList.add(boardFile);
					
					file = new File(path + "/" + newFileName);
					
					//지정한 폴더에 업로드 처리
					multipartFile.transferTo(file);
					
				}
			}
			
		}
		
		return fileList;
		
	}//
	
	//p186
	public List<BoardFileEntity> parseFileInfo(MultipartHttpServletRequest multipartHttpServletRequest) throws IllegalStateException, IOException{
	
				
		//첨부파일이 없으면 null 처리
				if(ObjectUtils.isEmpty(multipartHttpServletRequest)) {
					return null;
				}
				
				//첨부파일을 대입하기 위한 ArrayList 선언
				List<BoardFileEntity> fileList = new ArrayList<>();
				
				//현재시간을 원하는 형태로 변환
				DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyyMMdd");
				
				//현재날짜를 리턴
				ZonedDateTime current = ZonedDateTime.now();
				String path = "images/" + current.format(format);
				File file = new File(path);
				
				//업로드 폴더 생성
				if(file.exists() == false) {
					file.mkdirs();
				}
				
				//업로드 파일 내역을 Iterator 에 대입
				Iterator<String> iterator = multipartHttpServletRequest.getFileNames();
				
				String newFileName,originalFileExtension,contentType;
				
				while(iterator.hasNext()) {
					
					List<MultipartFile> list = multipartHttpServletRequest.getFiles(iterator.next());
					
					for(MultipartFile multipartFile : list) {
						
						if(multipartFile.isEmpty() == false) {
							//컨텐츠 타입을 변수에 대입
							contentType = multipartFile.getContentType();
							
							if(ObjectUtils.isEmpty(contentType)) {
								break;
							}else {
								//MIME에 따라 업로드 파일 확장자를 지정
								if(contentType.contains("image/jpeg")) {
									originalFileExtension = ".jpg";
								}else if(contentType.contains("image/png")) {
									originalFileExtension = ".png";
								}else if(contentType.contains("image/gif")) {
									originalFileExtension = ".gif";
								}else {
									break;
								}
							}
							
							//새로운 업로드 파일명 생성
							//중복을 배제하기 위해 시간을 파일명에 반영
							newFileName = Long.toString(System.nanoTime()) + originalFileExtension;
							
							BoardFileEntity boardFile = new BoardFileEntity();

							boardFile.setFileSize(multipartFile.getSize());//업로드파일크기
							boardFile.setOriginalFileName(multipartFile.getOriginalFilename());//업로드파일명
							boardFile.setStoredFilePath(path + "/" + newFileName);//저장위치
							boardFile.setCreatorId("admin");
							
							fileList.add(boardFile);
							
							file = new File(path + "/" + newFileName);
							
							//지정한 폴더에 업로드 처리
							multipartFile.transferTo(file);
							
						}
					}
					
				}
				
				return fileList;
		
		
	}
	
}




