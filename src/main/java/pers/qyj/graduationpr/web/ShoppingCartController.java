package pers.qyj.graduationpr.web;

import java.util.ArrayList;
import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
	public List<Object> addShopping(Integer rid) {
		try {
			Subject subject = SecurityUtils.getSubject();
			String currentUser = subject.getPrincipal().toString();
			Long uid = userService.getByName(currentUser).getId();
			return shoppingCartService.add(uid,rid);
		} catch (NullPointerException e) {
			List<Object> message = new ArrayList<>();
			message.add(-1);
			return message;
		}
	}
	
	@RequestMapping("/deleteShopping")
	@ResponseBody
	public int deleteShopping(Integer rid) {
		try {
			Subject subject = SecurityUtils.getSubject();
			String currentUser = subject.getPrincipal().toString();
			Long uid = userService.getByName(currentUser).getId();
			shoppingCartService.delete(uid,rid);
			return 0;
		} catch (NullPointerException e) {
			return -1;
		}
		
	}
	
	@RequestMapping("/updateShopping")
	@ResponseBody
	public int updateShopping(Integer num,Integer rid) {
		try {
			Subject subject = SecurityUtils.getSubject();
			String currentUser = subject.getPrincipal().toString();
			Long uid = userService.getByName(currentUser).getId();
			shoppingCartService.update(uid,rid,num);
			return 0;
		} catch (NullPointerException e) {
			return -1;
		}
	}
}
