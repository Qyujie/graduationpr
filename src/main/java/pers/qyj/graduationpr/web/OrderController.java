package pers.qyj.graduationpr.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import pers.qyj.graduationpr.pojo.Resource;
import pers.qyj.graduationpr.pojo.Roomtype;
import pers.qyj.graduationpr.pojo.Sign;
import pers.qyj.graduationpr.pojo.Order;
import pers.qyj.graduationpr.service.ResourceService;
import pers.qyj.graduationpr.service.SignService;
import pers.qyj.graduationpr.service.OrderService;

@Controller
@RequestMapping("config/order")
public class OrderController {
	@Autowired
	OrderService orderService;
	@Autowired
	SignService signService;
	@Autowired
	ResourceService resourceService;

	@RequestMapping("listOrder")
	public String list(Model model,
	           @RequestParam(value = "start", defaultValue = "0") int start,
	           @RequestParam(value = "size", defaultValue = "10") int size) {
		PageHelper.startPage(start,size,"id");
		
		List<Order> orders = orderService.list();
		model.addAttribute("orders", orders);
		
		PageInfo<Order> page = new PageInfo<>(orders);
		model.addAttribute("page", page);  
		
		Map<String, List<Resource>> order_resources = new HashMap<>();
		Map<Integer, Integer> resource_number = new HashMap<>();

		for (Order order : orders) {
			List<Resource> resources = resourceService.list(order);
			List<Sign> signs = signService.list(order);
			order_resources.put(order.getSign(), resources);
			for (Resource resource : resources) {
				int num = 0;
				for (Sign sign : signs) {
					if(resource.getId().equals(sign.getReid())){
						num++;
					}
				}
				if(num>0){
					resource_number.put(resource.getId(), num);
				}
			}
		}
		model.addAttribute("order_resources", order_resources);
		model.addAttribute("resource_number", resource_number);

		return "config/order/listOrder";
	}
	
	 @RequestMapping("deleteOrder")
	 public String delete(Model model, Integer id) {
	 orderService.delete(id);
	 return "redirect:listOrder";
	 }
	
	@RequestMapping("addOrder")
	public String add(Model model, Order order) {
		return "redirect:editOrder";
	}
	
	
	
	@RequestMapping("updateOrder")
	public String edit(Model model, Order order) {
		System.out.println(order.getSign());
		System.out.println(order.getDate());
		//orderService.add(order);
		return "redirect:listOrder";
	}
	

	// @Autowired
	// OrderService orderService;
	// @Autowired
	// OrderResourceService orderResourceService;
	// @Autowired
	// ResourceService resourceService;
	//
	// @RequestMapping("listOrder")
	// public String list(Model model) {
	// List<Order> orders = orderService.list();
	// model.addAttribute("orders", orders);
	//
	// Map<String, List<Resource>> order_resources = new HashMap<>();
	//
	// for (Order order : orders) {
	// List<Resource> resources = resourceService.list(order);
	// order_resources.put(order.getName(), resources);
	// }
	// model.addAttribute("order_resources", order_resources);
	//
	// return "config/listOrder";
	// }
	//
	// @RequestMapping("editOrder")
	// public String list(Model model, long id) {
	// Order order = orderService.get(id);
	// model.addAttribute("order", order);
	//
	// List<Resource> resources = resourceService.list();
	// model.addAttribute("resources", resources);
	//
	// List<Resource> currentResources = resourceService.list(order);
	//
	// Boolean hasResource = false;
	// Map<String, Boolean> order_hasResource = new HashMap<>();
	//
	// for(Resource resource:resources){
	// for(Resource currentResource:currentResources)
	// if(resource.getName().equals(currentResource.getName())){
	// hasResource=true;
	// order_hasResource.put(resource.getName(), hasResource);
	// }
	// }
	//
	// model.addAttribute("order_hasResource", order_hasResource);
	//
	// return "config/editOrder";
	// }
	//
	// @RequestMapping("updateOrder")
	// public String update(Order order, long[] resourceIds) {
	// orderResourceService.setResources(order, resourceIds);
	// orderService.update(order);
	// return "redirect:listOrder";
	// }
	//

	//
	// @RequestMapping("deleteOrder")
	// public String delete(Model model, long id) {
	// orderService.delete(id);
	// return "redirect:listOrder";
	// }

}
