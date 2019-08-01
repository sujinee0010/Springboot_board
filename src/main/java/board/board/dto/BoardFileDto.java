package board.board.dto;

import lombok.Data;

@Data
public class BoardFileDto {
	
	private int idx;
	
	private int boardIdx;
	
	private String originalFileName;
	
	private String storedFilePath;
	
	private long fileSize;

	public int getIdx() {
		return idx;
	}

	public void setIdx(int idx) {
		this.idx = idx;
	}

	public int getBoardIdx() {
		return boardIdx;
	}

	public void setBoardIdx(int boardIdx) {
		this.boardIdx = boardIdx;
	}

	public String getOriginalFileName() {
		return originalFileName;
	}

	public void setOriginalFileName(String originalFileName) {
		this.originalFileName = originalFileName;
	}

	public String getStoredFilePath() {
		return storedFilePath;
	}

	public void setStoredFilePath(String storedFilePath) {
		this.storedFilePath = storedFilePath;
	}

	public long getFileSize() {
		return fileSize;
	}

	public void setFileSize(long fileSize) {
		this.fileSize = fileSize;
	}
}
