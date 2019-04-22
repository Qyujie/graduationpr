package pers.qyj.graduationpr.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

//专门用于显示页面的控制器
@Controller
@RequestMapping("")
public class PageController {

	@RequestMapping("/login")
	public String login() {
		return "login";
	}

	@RequestMapping("/register")
	public String register() {
		return "register";
	}

	@RequestMapping("/repassword")
	public String repassword() {
		return "repassword";
	}

	@RequestMapping("/")
	public String login_null() {
		return "redirect:login";
	}

	@RequestMapping("/about")
	public String about() {
		return "about";
	}
	
	@RequestMapping("/contact")
	public String contact() {
		return "contact";
	}
	
	@RequestMapping("/restaurants")
	public String restaurants() {
		return "restaurants";
	}
	
	@RequestMapping("/index")
	public String index() {
		return "index";
	}
	
	@RequestMapping(value = "/settlement", method = RequestMethod.GET)
	public String settlement() {
		return "settlement";
	}
	
	@RequestMapping(value = "/orderSubmission", method = RequestMethod.GET)
	public String orderSubmission() {
		return "orderSubmission";
	}
	
	@RequestMapping("unauthorized")
	public String noPerms() {
		return "unauthorized";
	}

	@RequestMapping("config")
	public String config() {
		return "redirect:/config/user/listUser";
	}
	
	@RequestMapping("config/user")
	public String listUser() {
		return "redirect:user/listUser";
	}
	
	@RequestMapping("config/resource")
	public String listResource() {
		return "redirect:resource/listResource";
	}

	@RequestMapping("config/order")
	public String listOrder() {
		return "redirect:order/listOrder";
	}
}
