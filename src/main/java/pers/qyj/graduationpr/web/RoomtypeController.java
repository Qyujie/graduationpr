package pers.qyj.graduationpr.web;

import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import pers.qyj.graduationpr.pojo.Bedtype;
import pers.qyj.graduationpr.pojo.Roomtype;
import pers.qyj.graduationpr.service.BedtypeService;
import pers.qyj.graduationpr.service.RoomtypeService;

@Controller
@RequestMapping("config/resource")
public class RoomtypeController {
	@Autowired
	RoomtypeService roomtypeService;
	@Autowired
	BedtypeService bedtypeService;
	
	@RequestMapping("listRoomtype")
	public String list(Model model,
			           @RequestParam(value = "start", defaultValue = "0") int start,
			           @RequestParam(value = "size", defaultValue = "10") int size) 
			        		   throws Exception {
		PageHelper.startPage(start,size,"name");
		List<Roomtype> roomtypes = roomtypeService.list();
		PageInfo<Roomtype> page = new PageInfo<>(roomtypes);
		model.addAttribute("page", page);  
		
		model.addAttribute("requestUrl", "listRoomtype");
		
		return "config/resource/listRoomtype";
	}

	@RequestMapping("editRoomtype")
	public String list(Model model, String name) {
		String update = "add";
		Roomtype roomtype = new Roomtype();

		// 获取所有床型列表
		List<Bedtype> bedtypes = bedtypeService.list();
		model.addAttribute("bedtypes", bedtypes);
		
		if (name.equals("")) {
			update = "update";
			roomtype = roomtypeService.get(name);
			}
		
		Subject subject = SecurityUtils.getSubject();
		subject.getSession().setAttribute("update", update);

		model.addAttribute("roomtype", roomtype);
		
		return "config/resource/editRoomtype";
	}

	@RequestMapping("updateRoomtype")
	public String update(Model model, Roomtype roomtype) {
		Subject subject = SecurityUtils.getSubject();
		String update = (String) subject.getSession().getAttribute("update");
		if (update.equals("add")) {
			roomtypeService.add(roomtype);
		} else {
			roomtypeService.update(roomtype);
		}
		
		model.addAttribute("requestUrl", "listRoomtype");
		
		return "redirect:listRoomtype";
	}

	@RequestMapping("addRoomtype")
	public String list(Model model, Roomtype roomtype) {
		return "redirect:editRoomtype";
	}

	@RequestMapping("deleteRoomtype")
	public String delete(Model model, String name) {
		roomtypeService.delete(name);
		
		model.addAttribute("requestUrl", "listRoomtype");
		
		return "redirect:listRoomtype";
	}
}