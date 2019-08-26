package board.common;

import javax.servlet.http.HttpServletRequest;


import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.ModelAndView;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
//예외 처리 클래스 어노테이션
@ControllerAdvice
@Slf4j
public class ExceptionHandler {
	
	private Logger log = LoggerFactory.getLogger(this.getClass());
	//어떤 예외를 처리할 껀지
	@org.springframework.web.bind.annotation.ExceptionHandler(Exception.class)
	public ModelAndView defaultExceptionHandler(HttpServletRequest request, Exception exception){
		//예외 처리 페이지
		ModelAndView mv = new ModelAndView("/error/error_default");
		mv.addObject("exception", exception);
		// 로그 출력
		log.error("defaultExceptionHandler", exception);
		
		return mv;
	}
}