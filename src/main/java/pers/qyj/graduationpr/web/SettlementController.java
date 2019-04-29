package pers.qyj.graduationpr.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import pers.qyj.graduationpr.pojo.Resource;
import pers.qyj.graduationpr.pojo.Roomtype;
import pers.qyj.graduationpr.pojo.ShoppingCart;
import pers.qyj.graduationpr.pojo.SystemConfiguration;
import pers.qyj.graduationpr.service.ResourceService;
import pers.qyj.graduationpr.service.RoomtypeService;
import pers.qyj.graduationpr.service.ShoppingCartService;
import pers.qyj.graduationpr.service.SystemConfigurationService;
import pers.qyj.graduationpr.service.UserService;

@Controller
public class SettlementController {
	@Autowired
	ShoppingCartService shoppingCartService;
	@Autowired
	UserService userService;
	@Autowired
	ResourceService resourceService;
	@Autowired
	RoomtypeService roomtypeService;
	@Autowired
	SystemConfigurationService systemConfigurationService;

	@RequestMapping("/settlement")
	public String settlement(Model model) {
		try {
			Subject subject = SecurityUtils.getSubject();
			String currentUser = subject.getPrincipal().toString();
			Long uid = userService.getByName(currentUser).getId();
			// 获取购物车中已经勾选的
			List<ShoppingCart> shoppingCarts = shoppingCartService.list(uid, true);

			model.addAttribute("shoppingCarts", shoppingCarts);

			
			Map<Integer, Resource> id_resource = new HashMap<>();
			Map<Integer, Roomtype> id_roomtype = new HashMap<>();
			for (ShoppingCart shoppingCart : shoppingCarts) {
				id_resource.put(shoppingCart.getId(), resourceService.get(shoppingCart.getRid()));
				id_roomtype.put(shoppingCart.getId(),
						roomtypeService.get(resourceService.get(shoppingCart.getRid()).getRoomtype()));
			}
			model.addAttribute("shoppingCarts", shoppingCarts);
			model.addAttribute("id_resource", id_resource);
			model.addAttribute("id_roomtype", id_roomtype);
			
			String arrivalTime = systemConfigurationService.getArrivalTime();
			String depatureTime = systemConfigurationService.getDepatureTime();
			model.addAttribute("arrivalTime", arrivalTime);
			model.addAttribute("depatureTime", depatureTime);

		} catch (NullPointerException e) {
			return "login";
		}

		return "settlement";
	}
	
	@RequestMapping("/settlement2")
	public String settlements(Model model) {
		try {
			Subject subject = SecurityUtils.getSubject();
			String currentUser = subject.getPrincipal().toString();
			Long uid = userService.getByName(currentUser).getId();
			// 获取购物车中已经勾选的
			List<ShoppingCart> shoppingCarts = shoppingCartService.list(uid, true);

			model.addAttribute("shoppingCarts", shoppingCarts);

			
			Map<Integer, Resource> id_resource = new HashMap<>();
			Map<Integer, Roomtype> id_roomtype = new HashMap<>();
			for (ShoppingCart shoppingCart : shoppingCarts) {
				id_resource.put(shoppingCart.getId(), resourceService.get(shoppingCart.getRid()));
				id_roomtype.put(shoppingCart.getId(),
						roomtypeService.get(resourceService.get(shoppingCart.getRid()).getRoomtype()));
			}
			model.addAttribute("shoppingCarts", shoppingCarts);
			model.addAttribute("id_resource", id_resource);
			model.addAttribute("id_roomtype", id_roomtype);

		} catch (NullPointerException e) {
			return "login";
		}

		return "settlement2";
	}
}
