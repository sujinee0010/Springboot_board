package board.controller;

import java.io.File;
import java.net.URLEncoder;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import board.entity.BoardEntity;
import board.entity.BoardFileEntity;
import board.service.JpaBoardService;

@Controller
public class JpaBoardController {
	
	@Autowired
	private JpaBoardService jpaBoardService;
	
	@RequestMapping(value="/jpa/board" , method=RequestMethod.GET)
	public ModelAndView openBoardList() throws Exception{
		
		ModelAndView mv = new ModelAndView("/board/jpaBoardList");
		List<BoardEntity> list = jpaBoardService.selectBoardList();
		mv.addObject("list",list);
		
		return mv;
	}
	
	//게시글 작성 화면 
	@GetMapping("/jpa/board/write")
	public String openBoardWrite() throws Exception{
		
		return"/board/jpaBoardWrite";
	}
	//게시글작성 
	@PostMapping("/jpa/board/write")
	public String insertBoard(BoardEntity board,MultipartHttpServletRequest multipartHttpServletRequest) throws Exception {
		jpaBoardService.saveBoard(board, multipartHttpServletRequest);
		return "redirect:/jpa/board";
		
	}
	
	//게시글 상세화면
	@RequestMapping(value="/jpa/board/{boardIdx}", method=RequestMethod.GET)
	public ModelAndView opendBoardDetail(@PathVariable("boardIdx") int boardIdx) throws Exception{
		ModelAndView mv = new ModelAndView("/board/jpaBoardDetail");
		BoardEntity board = jpaBoardService.selectBoardDetail(boardIdx);
		mv.addObject("board",board);
		return mv;
	}
	//게시글 수정
	@PutMapping("/jpa/board/{boardIdx}")
	public String updateBoard(BoardEntity board) throws Exception {
		jpaBoardService.saveBoard(board,null);
		return "redirect:/jpa/board";
	}
	//게시글 삭제
	
	@DeleteMapping("/jpa/board/{boardIdx}")
	public String deleteBoard(@PathVariable("boardIdx") int boardIdx) throws Exception{
		jpaBoardService.deleteBoard(boardIdx);
		return "redirect:/jpa/board";
		
	}
	//첨부파일 다운로드
	@GetMapping("/jpa/board/file")
	public void downloadBoardFile( int idx,  int boardIdx, HttpServletResponse response) throws Exception{
		
		BoardFileEntity file = jpaBoardService.selectBoardFileInformation(idx, boardIdx);
		
			
			byte[] files = FileUtils.readFileToByteArray(new File (file.getStoredFilePath()));
			
			response.setContentType("application/octet-stream");
			response.setContentLength(files.length);
			response.setHeader("Content-Disposition", "attachment; fileName=\"" + URLEncoder.encode(file.getOriginalFileName(),"UTF-8")+"\";");
			response.setHeader("Content-Transfer-Encoding", "binary");
			
			response.getOutputStream().write(files);
			response.getOutputStream().flush();
			response.getOutputStream().close();
		}
	

}
