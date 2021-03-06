package pers.qyj.graduationpr.web;

import java.util.Arrays;
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

import pers.qyj.graduationpr.pojo.Permission;
import pers.qyj.graduationpr.pojo.Policy;
import pers.qyj.graduationpr.pojo.Role;
import pers.qyj.graduationpr.service.PermissionService;
import pers.qyj.graduationpr.service.RolePermissionService;
import pers.qyj.graduationpr.service.RoleService;

@Controller
@RequestMapping("config/user")
public class RoleController {
	@Autowired
	RoleService roleService;
	@Autowired
	RolePermissionService rolePermissionService;
	@Autowired
	PermissionService permissionService;

	@RequestMapping("listRole")
	public String list(Model model,
	           @RequestParam(value = "start", defaultValue = "0") int start,
	           @RequestParam(value = "size", defaultValue = "10") int size) {
		PageHelper.startPage(start,size,"id");
		List<Role> roles = roleService.list();
		model.addAttribute("roles", roles);
		
		PageInfo<Role> page = new PageInfo<>(roles);
		model.addAttribute("page", page); 

		Map<String, List<Permission>> role_permissions = new HashMap<>();

		for (Role role : roles) {
			List<Permission> permissions = permissionService.list(role);
			role_permissions.put(role.getName(), permissions);
		}
		model.addAttribute("role_permissions", role_permissions);

		model.addAttribute("requestUrl", "listRole");
		
		return "config/user/listRole";
	}

	@RequestMapping("editRole")
	public String list(Model model, Integer id) {
		
		if(id==null){
			return "redirect:listRole";
		}
		
		Role role = roleService.get(id.longValue());
		model.addAttribute("role", role);

		List<Permission> permissions = permissionService.list();
		model.addAttribute("permissions", permissions);
		
		List<Permission> currentPermissions = permissionService.list(role);
		
		Boolean hasPermission = false;
		Map<String, Boolean> role_hasPermission = new HashMap<>();
		
		for(Permission permission:permissions){
			for(Permission currentPermission:currentPermissions)
			if(permission.getName().equals(currentPermission.getName())){
				hasPermission=true;
				role_hasPermission.put(permission.getName(), hasPermission);
			}
		}
		
		model.addAttribute("role_hasPermission", role_hasPermission);

		return "config/user/editRole";
	}

	@RequestMapping("updateRole")
	public String update(Model model, Role role, long[] permissionIds) {
		rolePermissionService.setPermissions(role, permissionIds);
		roleService.update(role);
		
		model.addAttribute("requestUrl", "listRole");
		
		return "redirect:listRole";
	}

	@RequestMapping("addRole")
	public String list(Model model, Role role) {
		roleService.add(role);
		
		model.addAttribute("requestUrl", "listRole");
		
		return "redirect:listRole";
	}

	@RequestMapping("deleteRole")
	public String delete(Model model, Integer id) {
		if(id!=null){
			roleService.delete(id.longValue());
		}
		return "redirect:listRole";
	}

}