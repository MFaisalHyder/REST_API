package rest_api.controller;

import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Controller
@RequestMapping("/")
@Configuration
public class ApplicationController extends WebMvcConfigurerAdapter {

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

	@RequestMapping(value = "login", method = RequestMethod.GET)
	public ModelAndView getUserLoginPage() {
		return new ModelAndView("Login");
	}

	@RequestMapping(value = "register", method = RequestMethod.GET)
	public ModelAndView getUserRegisterPage() {
		return new ModelAndView("Registration");
	}

}
