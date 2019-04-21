package pers.qyj.graduationpr.web;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import pers.qyj.graduationpr.service.ShoppingCartService;
import pers.qyj.graduationpr.service.UserService;

@Controller
public class ShoppingCartController {
	@Autowired
	ShoppingCartService shoppingCartService;
	@Autowired
	UserService userService;
	
	@RequestMapping("/addShopping")
	@ResponseBody
	public int[] addShopping(Integer id) {
		Subject subject = SecurityUtils.getSubject();
		String currentUser = subject.getPrincipal().toString();
		Long uid = userService.getByName(currentUser).getId();
		return shoppingCartService.add(uid,id);
	}
	
	@RequestMapping("/deleteShopping") 
	@ResponseBody
	public String deleteShopping(Integer id) {
		return null;
		
	}
}
