package pers.qyj.graduationpr.web;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import javax.servlet.http.Cookie;

import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.fasterxml.jackson.databind.ObjectMapper;

import pers.qyj.graduationpr.pojo.User;

//专门用于显示页面的控制器
@Controller
@RequestMapping("")
public class PageController {

	@RequestMapping("/login")
	public String login(HttpServletRequest req) throws Exception {
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
	
	@RequestMapping("/orderSubmission")
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
