/*package rest_api.controller;

import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
@Configuration
public class ApplicationController {

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView getHomePage() {
		return new ModelAndView("Home");
	}

	@RequestMapping(value = "admin", method = RequestMethod.GET)
	public ModelAndView getIndexPage() {
		return new ModelAndView("Admin");
	}

	@RequestMapping(value = "panel", method = RequestMethod.GET)
	public ModelAndView getCustomerPage() {
		return new ModelAndView("UserManagement");
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
*/