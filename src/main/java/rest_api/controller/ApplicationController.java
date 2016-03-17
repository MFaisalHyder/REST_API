package rest_api.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/")
public class ApplicationController {

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView getIndexPage(ModelAndView mav) {
		return new ModelAndView("CustomerManagement");
	}

}
