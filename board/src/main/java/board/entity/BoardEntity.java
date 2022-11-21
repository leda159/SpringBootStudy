package board.entity;

import java.time.LocalDateTime;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;

//해당 클래스가 객체와 테이블의 매핑처리를 하는 클래스임을 선언
@Entity
//테이블을 생성하는 어노테이션
@Table(name="t_jpa_board")
//기본생성자를 자동으로 생성
@NoArgsConstructor
//Getter + Setter + toString
@Data
public class BoardEntity {

	//테이블의 Primary Key 선언
	@Id
	//선언된 데이터베이스의 특성에 따라 만약 mysql이면
	//Key값이 auto increment되고 오라클이면 sequence를
	//사용하여 자동 넘버링된다.
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int boardIdx;
	
	//테이블의 컬럼 선언  >  not null
	//제목
	@Column(nullable=false)
	private String title;
	
	//내용
	@Column(nullable=false)
	private String contents;
	
	//조회수
	@Column(nullable=false)
	private int hitCnt = 0;
	
	//작성자
	@Column(nullable=false)
	private String creatorId;
	
	//생성 시간
	@Column(nullable=false)
	private LocalDateTime createdDatetime = LocalDateTime.now();
	
	private String updaterId;
	
	private LocalDateTime updatedDatetime;
	
	
	//테이블의 상관 관계 (1:N, 1:1, M:N 등)
	//FetchType : JPA가 하나의 Entity를 조회하는 경우 상관 관계에 있는 객체를
	//			  어떻게 조회할것인지 결정하는 속성
	//fetch=FetchType.EAGER : 즉시 로딩 방식
	//						  t_jpa_board 테이블 조회시 t_jpa_file 테이블도
	//						  함께 초회처리를 하는 방식
	//fetch=FetchType.LAZY : 지연 로딩 방식
	//						 t_jpa_board 테이블 조회시 t_jpa_file 테이블도
	//						 함께 조회되는 것이 아닐 ㅏ실제 상요할 시점에 조회 처리
	
	
	//cascade 옵션 : Entity의 상태 변화를 전파하는 옵션
	//cascade=CascadeType.ALL : 상위 Entity에서 하위 Entity로 모든 작업을 전파함
	//cascade=CascadeType.PERSIST : 하위 Entity까지 영속성을 전파함
	//cascade=CascadeType.MERGE : 하위 Entity까지 병합 작업을 지속 처리
	//							  게시물 테이블과 게시물 첨부파일을 조회한 후 업데이트 처리
	//cascade=CascadeType.REMOVE : 상관관계에 있는 하위 Entity까지 제거처리
	@OneToMany(fetch=FetchType.EAGER,cascade=CascadeType.ALL)
	//foreign key 선언
	@JoinColumn(name="board_idx")
	//첨부파일 필드
	private Collection<BoardFileEntity> fileList;
}




