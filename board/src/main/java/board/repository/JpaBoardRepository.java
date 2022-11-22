package board.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import board.entity.BoardEntity;
import board.entity.BoardFileEntity;

//Spring JPA Repository interface 

/*
Repository<T,ID>

CrudRepository<T,ID>
- Create,Read,Update,Delete 기능을 기본적으로 제공

PagingAndSortingRepository<T,ID>
- CrudRepository 기능 + 페이징처리 + Sort(정렬)

JpaRepository<T,ID>
- PagingAndSortingRepository + JPA 특수 기능
*/
public interface JpaBoardRepository 
		extends CrudRepository<BoardEntity,Integer> {

	//게시물번호 내림차순으로 게시판 목록 검색 처리
	List<BoardEntity> findAllByOrderByBoardIdxDesc();
	
	//특정 게시물 번호에 대한 특정 첨부파일 내역을 가져온다.
	@Query("select file from BoardFileEntity file where board_idx = :boardIdx and idx = :idx")
	BoardFileEntity findBoardFile(@Param("boardIdx") int boardIdx,@Param("idx") int idx);
}



