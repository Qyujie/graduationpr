package pers.qyj.graduationpr.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import pers.qyj.graduationpr.pojo.Roomtype;
import pers.qyj.graduationpr.service.RoomtypeService;

@Controller
public class HomeController {
	@Autowired
	RoomtypeService roomtypeService;
	
	@RequestMapping("/home")
	public String home(Model model){
		List<Roomtype> roomtypesL = roomtypeService.list();
		Roomtype[] roomtypes = new Roomtype[roomtypesL.size()];
		roomtypesL.toArray(roomtypes);
		model.addAttribute("roomtypes", roomtypes);
		
		model.addAttribute("requestUrl", "home");
		return "home";
	}
}
