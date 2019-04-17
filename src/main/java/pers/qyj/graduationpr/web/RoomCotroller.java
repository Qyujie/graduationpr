package pers.qyj.graduationpr.web;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import pers.qyj.graduationpr.pojo.Breakfast;
import pers.qyj.graduationpr.pojo.Facility;
import pers.qyj.graduationpr.pojo.Policy;
import pers.qyj.graduationpr.pojo.Resource;
import pers.qyj.graduationpr.pojo.Roomtype;
import pers.qyj.graduationpr.service.BreakfastService;
import pers.qyj.graduationpr.service.FacilityService;
import pers.qyj.graduationpr.service.PolicyService;
import pers.qyj.graduationpr.service.ResourceService;
import pers.qyj.graduationpr.service.RoomtypeService;

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
	
	@RequestMapping("/room")
	public String room(Model model){
		
		List<Resource> resources = resourceService.list();
		List<Roomtype> roomtypes = roomtypeService.list();
		
		model.addAttribute("resources", resources);
		model.addAttribute("roomtypes", roomtypes);
		
		model.addAttribute("requestUrl", "room");
		return "room";
	} 
	
	@RequestMapping("/roomInformation")
	public String roomInformation(Model model, Integer id){
		
		List<Roomtype> roomtypes = roomtypeService.list();
		List<Breakfast> breakfasts = breakfastService.list();
		List<Policy> policies = policyService.list();
		List<Facility> facilities = facilityService.list();
		
		
		model.addAttribute("roomtypes", roomtypes);
		model.addAttribute("breakfasts", breakfasts);
		model.addAttribute("policies", policies);
		model.addAttribute("facilities", facilities);
		
		List<Resource> resources = resourceService.list();
		model.addAttribute("resources", resources);

		Map<String, List<Facility>> resource_facilities = new HashMap<>();
		for (Resource resource : resources) {
			List<Facility> rfacilities = facilityService.listFacilities(resource);
			resource_facilities.put(resource.getId() + "", rfacilities);
		}
		model.addAttribute("resource_facilities", resource_facilities);

		Map<String, List<Policy>> resource_policies = new HashMap<>();
		for (Resource resource : resources) {
			List<Policy> rpolicies = policyService.listPolicies(resource);
			resource_policies.put(resource.getId() + "", rpolicies);
		}
		model.addAttribute("resource_policies", resource_policies);
		
		Map<String, String> resource_imgurl = new HashMap<>();
		for (Resource resource : resources) {
			for(Roomtype roomtype:roomtypes)
			if(resource.getRoomtype().equals(roomtype.getName())){
				resource_imgurl.put(resource.getId() + "", roomtype.getImgurl());
				break;
			}
		}
		model.addAttribute("resource_imgurl", resource_imgurl);
		
		return "roomInformation";
	} 
	
	@RequestMapping("/searchRoomInformation")
	@ResponseBody
	public String searchRoomInformation(String arrive,String departure,Integer adults,Integer children,
			String roomtype,String breakfast,String[] facility,String[] policy){
		System.out.println(arrive);
		System.out.println(departure);
		System.out.println(adults);
		System.out.println(children);
		System.out.println(roomtype);
		System.out.println(breakfast);
		System.out.println(facility.length);
		System.out.println(policy.length);
		return "";
	}  
	
}