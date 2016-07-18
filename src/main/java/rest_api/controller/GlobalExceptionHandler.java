package rest_api.controller;

import java.io.IOException;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(IOException.class)
	  public ModelAndView myError(Exception message) {	    
		
		ModelAndView mavIO = new ModelAndView();
		mavIO.addObject("exception", message);
		mavIO.setViewName("ErrorPage");
		return mavIO;
	  }
		
	@ExceptionHandler(NullPointerException.class)
	public ModelAndView userNotFound(Exception message) {		

		ModelAndView mavNP = new ModelAndView();
		mavNP.addObject("exception", message);
		mavNP.setViewName("ErrorPage");
		return mavNP;
	}
	
	@ExceptionHandler(ClassNotFoundException.class)
	public ModelAndView classNotFound(Exception message) {
		
		ModelAndView mavCNF = new ModelAndView();
		mavCNF.addObject("exception", message);
		mavCNF.setViewName("ErrorPage");
		return mavCNF;
	}
}
