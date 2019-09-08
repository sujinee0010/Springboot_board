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

import board.dto.BoardDto;
import board.dto.BoardFileDto;
import board.service.BoardService;

@Controller
public class RestBoardController {
	
	
	@Autowired
	private BoardService boardService;
	
	@RequestMapping(value="/board" , method=RequestMethod.GET)
	public ModelAndView openBoardList() throws Exception{
		
		ModelAndView mv = new ModelAndView("/board/restBoardList");
		List<BoardDto> list = boardService.selectBoardList();
		mv.addObject("list",list);
		
		return mv;
	}
	
	//게시글 작성 화면 
	@GetMapping("/board/write")
	public String openBoardWrite() throws Exception{
		
		return"/board/restBoardWrite";
	}
	//게시글작성 
	@PostMapping("/board/write")
	public String insertBoard(BoardDto board,MultipartHttpServletRequest multipartHttpServletRequest) throws Exception {
		boardService.insertBoard(board, multipartHttpServletRequest);
		return "redirect:/board";
		
	}
	
	//게시글 상세화면
	@RequestMapping(value="/board/{boardIdx}", method=RequestMethod.GET)
	public ModelAndView opendBoardDetail(@PathVariable("boardIdx") int boardIdx) throws Exception{
		ModelAndView mv = new ModelAndView("/board/restBoardDetail");
		BoardDto board = boardService.selectBoardDetail(boardIdx);
		mv.addObject("board",board);
		return mv;
	}
	//게시글 수정
	@PutMapping("/board/{boardIdx}")
	public String updateBoard(BoardDto board) throws Exception {
		boardService.updateBoard(board);
		return "redirect:/board";
	}
	//게시글 삭제
	
	@DeleteMapping("/board/{boardIdx}")
	public String deleteBoard(@PathVariable("boardIdx") int boardIdx) throws Exception{
		boardService.deleteBoard(boardIdx);
		return "redirect:/board";
		
	}
	//첨부파일 다운로드
	@GetMapping("/board/file")
	public void downloadBoardFile(@RequestParam int idx, @RequestParam int boardIdx, HttpServletResponse response) throws Exception{
		
		BoardFileDto boardFile = boardService.selectBoardFileInformation(idx, boardIdx);
		if(ObjectUtils.isEmpty(boardFile)==false) {
			
			String fileName = boardFile.getOriginalFileName();
			
			byte[] files = FileUtils.readFileToByteArray(new File (boardFile.getStoredFilePath()));
			
			response.setContentType("application/octet-stream");
			response.setContentLength(files.length);
			response.setHeader("Content-Disposition", "attachment; fileName=\"" + URLEncoder.encode(fileName,"UTF-8")+"\";");
			response.setHeader("Content-Transfer-Encoding", "binary");
			
			response.getOutputStream().write(files);
			response.getOutputStream().flush();
			response.getOutputStream().close();
		}
	}

}
