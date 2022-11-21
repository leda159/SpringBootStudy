package board.entity;

import java.time.LocalDateTime;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="t_jpa_file")
@NoArgsConstructor
@Data
public class BoardFileEntity {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int idx;
	
	//업로드 파일명
	@Column(nullable=false)
	private String originalFileName;
	
	//저장위치
	@Column(nullable=false)
	private String storedFilePath;
	
	
	@Column(nullable=false)
	private long fileSize;
	
	@Column(nullable=false)
	private String creatorId;
	
	@Column(nullable=false)
	private LocalDateTime createdDatetime = LocalDateTime.now();
	
	private String updaterId;
	
	private LocalDateTime updatedDatetime;
	
	
}





