package pers.qyj.graduationpr.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
	public String roomInformation(Model model,
			 @RequestParam(value = "roomName", defaultValue = "") String roomName){
		
		List<Roomtype> roomtypes = roomtypeService.list();
		List<Breakfast> breakfasts = breakfastService.list();
		List<Policy> policies = policyService.list();
		List<Facility> facilities = facilityService.list();
		
		model.addAttribute("roomtypes", roomtypes);
		model.addAttribute("breakfasts", breakfasts);
		model.addAttribute("policies", policies);
		model.addAttribute("facilities", facilities);
		

		List<Resource> resources = new ArrayList<>();
		if(roomName.equals("")){
			resources = resourceService.list();
		}else{
			resources = resourceService.list(roomName);
		}

		Map<String, List<Facility>> resource_facilities = new HashMap<>();
		for (Resource resource : resources) {
			List<Facility> rfacilities = facilityService.listFacilities(resource);
			resource_facilities.put(resource.getId() + "", rfacilities);
		}

		Map<String, List<Policy>> resource_policies = new HashMap<>();
		for (Resource resource : resources) {
			List<Policy> rpolicies = policyService.listPolicies(resource);
			resource_policies.put(resource.getId() + "", rpolicies);
		}
		
		Map<String, String> resource_imgurl = new HashMap<>();
		for (Resource resource : resources) {
			for(Roomtype roomtype:roomtypes)
			if(resource.getRoomtype().equals(roomtype.getName())){
				resource_imgurl.put(resource.getId() + "", roomtype.getImgurl());
				break;
			}
		}
		
		model.addAttribute("resources", resources);
		model.addAttribute("resource_facilities", resource_facilities);
		model.addAttribute("resource_policies", resource_policies);
		model.addAttribute("resource_imgurl", resource_imgurl);
		
		
		return "roomInformation";
	} 
	
	@RequestMapping("/searchRoomInformation")
	@ResponseBody
	public List<Object> searchRoomInformation(Model model,String arrive,String departure,Integer adults,Integer children,
			String roomtype,String breakfast,Integer[] facility,Integer[] policy){
		List<Resource> resources = new ArrayList<>();
		
		resources = resourceService.list(arrive,departure,adults,children,roomtype,breakfast,facility,policy);
		
		Map<String, List<Facility>> resource_facilities = new HashMap<>();
		for (Resource resource : resources) {
			List<Facility> rfacilities = facilityService.listFacilities(resource);
			resource_facilities.put(resource.getId() + "", rfacilities);
		}

		Map<String, List<Policy>> resource_policies = new HashMap<>();
		for (Resource resource : resources) {
			List<Policy> rpolicies = policyService.listPolicies(resource);
			resource_policies.put(resource.getId() + "", rpolicies);
		}

		List<Roomtype> roomtypes = roomtypeService.list();
		Map<String, String> resource_imgurl = new HashMap<>();
		for (Resource resource : resources) {
			for(Roomtype rroomtype:roomtypes)
			if(resource.getRoomtype().equals(rroomtype.getName())){
				resource_imgurl.put(resource.getId() + "", rroomtype.getImgurl());
				break;
			}
		}
		
		ArrayList<Object> list = new ArrayList<>();
		list.add(resources);
		list.add(resource_facilities);
		list.add(resource_policies);
		list.add(resource_imgurl);
		
		return list;
	}  
	
}