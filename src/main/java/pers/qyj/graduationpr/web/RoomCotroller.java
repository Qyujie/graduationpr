package pers.qyj.graduationpr.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import pers.qyj.graduationpr.pojo.Resource;
import pers.qyj.graduationpr.pojo.Roomtype;
import pers.qyj.graduationpr.service.ResourceService;
import pers.qyj.graduationpr.service.RoomtypeService;

@Controller
public class RoomCotroller {
	@Autowired
	RoomtypeService roomtypeService;
	@Autowired
	ResourceService resourceService;
	
	@RequestMapping("/room")
	public String room(Model model){
		
		List<Resource> resources = resourceService.list();
		List<Roomtype> roomtypes = roomtypeService.list();
		
		model.addAttribute("resources", resources);
		model.addAttribute("roomtypes", roomtypes);
		
		model.addAttribute("requestUrl", "room");
		return "room";
	} 
}