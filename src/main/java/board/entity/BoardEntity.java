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

//@Entity 어노테이션은 해당 클래스가 JPA의 엔티티임을 나타냄
@Entity
@Table(name="t_jpa_board")
@NoArgsConstructor
@Data
public class BoardEntity {

	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO) //기본키 자동증가 
	private int boardIdx;
	
	@Column(nullable=false) // not null 속성을 지정
	private String title;
	
	@Column(nullable=false)
	private String contents;
	
	@Column(nullable=false)
	private int hitCnt=0;
	
	@Column(nullable=false)
	private String creatorId;
	
	@Column(nullable=false)
	private LocalDateTime createdDatetime = LocalDateTime.now(); //  작성시간의 초기값
	
	private String updaterId;
	
	private LocalDateTime updatedDatetime;
	
	//1:n의 관계를 표현하는 어노테이션
	@OneToMany(fetch=FetchType.EAGER, cascade=CascadeType.ALL)
	@JoinColumn(name="board_idx") //릴레이션 관계가 있는 테이블의 컬럼 지정
	private Collection<BoardFileEntity> fileList;
	
}
