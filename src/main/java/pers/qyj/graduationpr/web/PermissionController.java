package pers.qyj.graduationpr.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import pers.qyj.graduationpr.pojo.Permission;
import pers.qyj.graduationpr.pojo.Roomtype;
import pers.qyj.graduationpr.service.PermissionService;

@Controller
@RequestMapping("config/user")
public class PermissionController {
	@Autowired
	PermissionService permissionService;
	
	@RequestMapping("listPermission")
	public String list(Model model,
	           @RequestParam(value = "start", defaultValue = "0") int start,
	           @RequestParam(value = "size", defaultValue = "30") int size) 
	        		   throws Exception {
		PageHelper.startPage(start,size,"id");
		List<Permission> permissions = permissionService.list();
		PageInfo<Permission> page = new PageInfo<>(permissions);
		model.addAttribute("page", page); 
		model.addAttribute("permissions", permissions);
		
		model.addAttribute("requestUrl", "listPermission");
		
		return "config/user/listPermission";
	}

	@RequestMapping("editPermission")
	public String list(Model model, Integer id) {
		if(id==null){
			return "redirect:listPermission";
		}
		Permission permission = permissionService.get(id.longValue());
		model.addAttribute("permission", permission);
		return "config/user/editPermission";
	}

	@RequestMapping("updatePermission")
	public String update(Model model,Permission permission) {
		permissionService.update(permission);
		
		model.addAttribute("requestUrl", "listPermission");
		
		return "redirect:listPermission";
	}

	@RequestMapping("addPermission")
	public String list(Model model, Permission permission) {
		System.out.println(permission.getName());
		System.out.println(permission.getDesc_());
		permissionService.add(permission);
		
		model.addAttribute("requestUrl", "listPermission");
		
		return "redirect:listPermission";
	}

	@RequestMapping("deletePermission")
	public String delete(Model model, Integer id) {
		if(id!=null){
			permissionService.delete(id.longValue());
			
			model.addAttribute("requestUrl", "listPermission");
		}
		
		return "redirect:listPermission";
	}

}