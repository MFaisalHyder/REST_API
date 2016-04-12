package rest_api.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/")
public class ApplicationController {

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView getIndexPage() {
		return new ModelAndView("Index");
	}

	@RequestMapping(value = "panel", method = RequestMethod.GET)
	public ModelAndView getCustomerPage() {
		return new ModelAndView("CustomerManagement");
	}

	@RequestMapping(value = "file", method = RequestMethod.GET)
	public ModelAndView getFilePage() {
		return new ModelAndView("FileManagement");
	}
}
