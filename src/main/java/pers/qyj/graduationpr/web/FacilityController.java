package pers.qyj.graduationpr.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import pers.qyj.graduationpr.pojo.Facility;
import pers.qyj.graduationpr.pojo.Permission;
import pers.qyj.graduationpr.service.FacilityService;

@Controller
@RequestMapping("config/resource")
public class FacilityController {
	@Autowired
	FacilityService facilityService;
	
	@RequestMapping("listFacility")
	public String list(Model model,
	           @RequestParam(value = "start", defaultValue = "0") int start,
	           @RequestParam(value = "size", defaultValue = "10") int size) {
		PageHelper.startPage(start,size,"id");
		List<Facility> facilities = facilityService.list();
		model.addAttribute("facilities", facilities);
		
		PageInfo<Facility> page = new PageInfo<>(facilities);
		model.addAttribute("page", page); 
		
		model.addAttribute("requestUrl", "listFacility");
		
		return "config/resource/listFacility";
	}

	@RequestMapping("editFacility")
	public String list(Model model, int id) {
		Facility facility = facilityService.get(id);
		model.addAttribute("facility", facility);
		return "config/resource/editFacility";
	}

	@RequestMapping("updateFacility")
	public String update(Model model, Facility facility) {
		System.out.println(facility.getId());
		System.out.println(facility.getName());
		System.out.println(facility.getDesc());
		facilityService.update(facility);
		
		model.addAttribute("requestUrl", "listFacility");
		
		return "redirect:listFacility";
	}

	@RequestMapping("addFacility")
	public String list(Model model, Facility facility) {
		System.out.println(facility.getName());
		System.out.println(facility.getDesc());
		facilityService.add(facility);
		
		model.addAttribute("requestUrl", "listFacility");
		
		return "redirect:listFacility";
	}

	@RequestMapping("deleteFacility")
	public String delete(Model model, int id) {
		facilityService.delete(id);
		
		model.addAttribute("requestUrl", "listFacility");
		
		return "redirect:listFacility";
	}
}
