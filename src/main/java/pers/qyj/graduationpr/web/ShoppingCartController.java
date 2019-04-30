package pers.qyj.graduationpr.web;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import pers.qyj.graduationpr.service.SignService;
import pers.qyj.graduationpr.service.ShoppingCartService;
import pers.qyj.graduationpr.service.UserService;

@Controller
public class ShoppingCartController {
	@Autowired
	ShoppingCartService shoppingCartService;
	@Autowired
	UserService userService;
	@Autowired
	SignService resourceSignService;

	@RequestMapping("/addShopping")
	@ResponseBody
	public List<Object> addShopping(Integer rid, String arrival, String depature) throws Exception {
		try {
			Subject subject = SecurityUtils.getSubject();
			String currentUser = subject.getPrincipal().toString();
			Long uid = userService.getByName(currentUser).getId();

			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date arrivalDate = new Date(sdf.parse(arrival).getTime());
			Date depatureDate = new Date(sdf.parse(depature).getTime());

			return shoppingCartService.add(uid, rid, arrivalDate, depatureDate);
		} catch (NullPointerException e) {
			List<Object> message = new ArrayList<>();
			message.add(-1);
			return message;
		}
	}

	@RequestMapping("/deleteShopping")
	@ResponseBody
	public int deleteShopping(Integer id) {
		try {
			Subject subject = SecurityUtils.getSubject();
			String currentUser = subject.getPrincipal().toString();
			Long uid = userService.getByName(currentUser).getId();
			shoppingCartService.delete(id);
			return 0;
		} catch (NullPointerException e) {
			return -1;
		}

	}

	@RequestMapping("/updateShoppingNumber")
	@ResponseBody
	public int updateShoppingNumber(Integer number, Integer id) {
		try {
			Subject subject = SecurityUtils.getSubject();
			String currentUser = subject.getPrincipal().toString();
			shoppingCartService.updateNumber(id, number);
			return 0;
		} catch (NullPointerException e) {
			return -1;
		}
	}

	@RequestMapping("/updateShoppingChecked")
	@ResponseBody
	public int updateShoppingChecked(Integer[] id) {
		try {
			if (id.length > 0) {
				Subject subject = SecurityUtils.getSubject();
				String currentUser = subject.getPrincipal().toString();
				Long uid = userService.getByName(currentUser).getId();
				shoppingCartService.updateChecked(uid,id);
				return 0;
			} else {
				return -2;// 未选择
			}
		} catch (NullPointerException e) {
			return -1;
		}
	}

	@RequestMapping("/updateShoppingDate")
	@ResponseBody
	public List<Object> updateShoppingDate(String arrival, String depature, Integer number, Integer id) throws Exception {
		List<Object> message = new ArrayList<>();
		try {
			Subject subject = SecurityUtils.getSubject();
			String currentUser = subject.getPrincipal().toString();
			Long uid = userService.getByName(currentUser).getId();

			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			java.sql.Date arrivalDate = new java.sql.Date(sdf.parse(arrival).getTime());
			java.sql.Date depatureDate = new java.sql.Date(sdf.parse(depature).getTime());
			
			int rid = shoppingCartService.getRidByid(id);
			int remain = resourceSignService.getRemainByReid(rid, arrivalDate, depatureDate);
			if (remain >= number) {
				shoppingCartService.updateDate(id, arrivalDate, depatureDate);
				if(remain>=6){
					message.add(0);
					message.add(remain);
				}else{
					message.add(1);
					message.add(remain);
				}
			} else {
				message.add(-2);// 剩余不足
				message.add(remain);
			}
			return message;
		} catch (NullPointerException e) {
			message.add(-1);
			return message;
		}
	}
}
