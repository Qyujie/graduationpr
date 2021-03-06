package pers.qyj.graduationpr.web;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import pers.qyj.graduationpr.pojo.Breakfast;
import pers.qyj.graduationpr.pojo.Facility;
import pers.qyj.graduationpr.pojo.Policy;
import pers.qyj.graduationpr.pojo.Resource;
import pers.qyj.graduationpr.pojo.Role;
import pers.qyj.graduationpr.pojo.Roomtype;
import pers.qyj.graduationpr.pojo.ShoppingCart;
import pers.qyj.graduationpr.service.BreakfastService;
import pers.qyj.graduationpr.service.FacilityService;
import pers.qyj.graduationpr.service.PolicyService;
import pers.qyj.graduationpr.service.ResourceService;
import pers.qyj.graduationpr.service.SignService;
import pers.qyj.graduationpr.service.RoomtypeService;
import pers.qyj.graduationpr.service.ShoppingCartService;
import pers.qyj.graduationpr.service.UserService;

@Controller
public class RoomCotroller {
	@Autowired
	ResourceService resourceService;
	@Autowired
	RoomtypeService roomtypeService;
	@Autowired
	BreakfastService breakfastService;
	@Autowired
	PolicyService policyService;
	@Autowired
	FacilityService facilityService;
	@Autowired
	UserService userService;
	@Autowired
	ShoppingCartService shoppingCartService;
	@Autowired
	SignService signService;

	@RequestMapping("/room")
	public String room(Model model) {

		List<Resource> resources = resourceService.list();
		List<Roomtype> roomtypes = roomtypeService.list();

		model.addAttribute("resources", resources);
		model.addAttribute("roomtypes", roomtypes);

		model.addAttribute("requestUrl", "room");
		return "room";
	}

	@RequestMapping("/roomInformation")
	public String roomInformation(Model model, 
			@RequestParam(value = "roomName", defaultValue = "") String roomName,
			@RequestParam(value = "arrival", defaultValue = "")String arrival, 
			@RequestParam(value = "depature", defaultValue = "")String depature,
			@RequestParam(value = "adults", defaultValue = "0") Integer adults,
			@RequestParam(value = "children", defaultValue = "0") Integer children,
			@RequestParam(value = "start", defaultValue = "0") int start,
			@RequestParam(value = "size", defaultValue = "10") int size) throws Exception {

		List<Roomtype> roomtypes = roomtypeService.list();

		List<Breakfast> breakfasts = breakfastService.list();
		List<Policy> policies = policyService.list();
		List<Facility> facilities = facilityService.list();

		model.addAttribute("roomtypes", roomtypes);
		model.addAttribute("breakfasts", breakfasts);
		model.addAttribute("policies", policies);
		model.addAttribute("facilities", facilities);

		// 资源
		List<Resource> resources = new ArrayList<>();
		
		if (roomName.equals("")) {
			if (adults != 0 || children != 0) {
				resources = resourceService.list(adults, children, "", "", null, null, start, size);
			} else {
				PageHelper.startPage(start, size, "id");
				resources = resourceService.list();
			}
		} else {
			PageHelper.startPage(start, size, "id");
			resources = resourceService.list(roomName);
		}

		PageInfo<Resource> page = new PageInfo<>(resources);
		model.addAttribute("page", page);

		// 日期
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Map<Integer, Integer> resource_remain = new HashMap<>();
		if (!arrival.equals("") || !depature.equals("")) {
			java.sql.Date arrivalDate = new java.sql.Date(sdf.parse(arrival).getTime());
			java.sql.Date depatureDate = new java.sql.Date(sdf.parse(depature).getTime());

			resource_remain = signService.listRemain(resources, arrivalDate, depatureDate);
		} else {
			Calendar calendar = new GregorianCalendar();
			calendar.setTime(new Date());
			calendar.add(Calendar.DATE, 1);
			java.sql.Date arrivalDate = new java.sql.Date(sdf.parse(sdf.format(calendar.getTime())).getTime());

			calendar.add(Calendar.DATE, 1);
			java.sql.Date depatureDate = new java.sql.Date(sdf.parse(sdf.format(calendar.getTime())).getTime());

			resource_remain = signService.listRemain(resources, arrivalDate, depatureDate);
		}

		// 设施
		Map<Integer, List<Facility>> resource_facilities = new HashMap<>();
		for (Resource resource : resources) {
			List<Facility> rfacilities = facilityService.listFacilities(resource);
			resource_facilities.put(resource.getId(), rfacilities);
		}

		// 政策
		Map<Integer, List<Policy>> resource_policies = new HashMap<>();
		for (Resource resource : resources) {
			List<Policy> rpolicies = policyService.listPolicies(resource);
			resource_policies.put(resource.getId(), rpolicies);
		}

		// 房型
		Map<Integer, Roomtype> resource_roomtype = new HashMap<>();
		for (Resource resource : resources) {
			for (Roomtype roomtype : roomtypes)
				if (resource.getRoomtype().equals(roomtype.getName())) {
					resource_roomtype.put(resource.getId(), roomtype);
					break;
				}
		}

		model.addAttribute("resources", resources);
		model.addAttribute("resource_facilities", resource_facilities);
		model.addAttribute("resource_policies", resource_policies);
		model.addAttribute("resource_roomtype", resource_roomtype);
		model.addAttribute("resource_remain", resource_remain);

		// 购物车
		try {
			Subject subject = SecurityUtils.getSubject();
			String currentUser = subject.getPrincipal().toString();
			Long uid = userService.getByName(currentUser).getId();
			List<ShoppingCart> shoppingCarts = shoppingCartService.list(uid);

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
			System.out.println("-1");
		}

		return "roomInformation";
	}

	@RequestMapping("/searchRoomInformation")
	@ResponseBody
	public List<Object> searchRoomInformation(Model model, String arrival, String depature, Integer adults,
			Integer children, String roomtype, String breakfast, Integer[] facility, Integer[] policy,
			@RequestParam(value = "start", defaultValue = "0") int start,
			@RequestParam(value = "size", defaultValue = "5") int size) throws Exception {
		List<Resource> resources = new ArrayList<>();

		resources = resourceService.list(adults, children, roomtype, breakfast, facility, policy, start, size);

		Map<Integer, List<Facility>> resource_facilities = new HashMap<>();
		for (Resource resource : resources) {
			List<Facility> rfacilities = facilityService.listFacilities(resource);
			resource_facilities.put(resource.getId(), rfacilities);
		}

		Map<Integer, List<Policy>> resource_policies = new HashMap<>();
		for (Resource resource : resources) {
			List<Policy> rpolicies = policyService.listPolicies(resource);
			resource_policies.put(resource.getId(), rpolicies);
		}

		List<Roomtype> roomtypes = roomtypeService.list();
		Map<Integer, Roomtype> resource_roomtype = new HashMap<>();
		for (Resource resource : resources) {
			for (Roomtype rroomtype : roomtypes)
				if (resource.getRoomtype().equals(rroomtype.getName())) {
					resource_roomtype.put(resource.getId(), rroomtype);
					break;
				}
		}

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		java.sql.Date arrivalDate = new java.sql.Date(sdf.parse(arrival).getTime());
		java.sql.Date depatureDate = new java.sql.Date(sdf.parse(depature).getTime());

		Map<Integer, Integer> resource_remain = signService.listRemain(resources, arrivalDate, depatureDate);

		ArrayList<Object> list = new ArrayList<>();
		list.add(resources);
		list.add(resource_facilities);
		list.add(resource_policies);
		list.add(resource_roomtype);
		list.add(resource_remain);

		return list;
	}

}