package board.entity;

import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.NoArgsConstructor;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


@Entity
@Table(name="t_jpa_file")
@NoArgsConstructor
@Data
public class BoardFileEntity {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int idx;
	
	@Column(nullable=false)
	private String originalFileName;
	
	@Column(nullable=false)
	private String storedFilePath;
	
	@Column(nullable=false)
	private long fileSize;
	
	@Column(nullable=false)
	private String creatorId;
	
	@Column(nullable=false)
	private LocalDateTime createdDatetime = LocalDateTime.now();
	
	private String updatorId;
	
	private LocalDateTime updateDateTime;
	
}
