package pers.qyj.graduationpr.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import pers.qyj.graduationpr.pojo.Role;
import pers.qyj.graduationpr.pojo.User;
import pers.qyj.graduationpr.service.RoleService;
import pers.qyj.graduationpr.service.UserRoleService;
import pers.qyj.graduationpr.service.UserService;

@Controller
@RequestMapping("config/user")
public class UserController {
	@Autowired
	UserRoleService userRoleService;
	@Autowired
	UserService userService;
	@Autowired
	RoleService roleService;

	@RequestMapping("listUser")
	public String list(Model model) {
		List<User> users = userService.list();
		model.addAttribute("users", users);
		Map<String, List<Role>> user_roles = new HashMap<>();
		for (User user : users) {
			List<Role> roles = roleService.listRoles(user);
			user_roles.put(user.getName(), roles);
		}
		model.addAttribute("user_roles", user_roles);

		model.addAttribute("requestUrl", "listUser");
		
		return "config/user/listUser";
	}

	@RequestMapping("editUser")
	public String edit(Model model, Integer id) {
		if(id==null){
			return "redirect:listUser";
		}
		//获取用户
		User user = userService.get(id.longValue());
		model.addAttribute("user", user);
		
		//获取该用户的角色
		List<Role> currentRoles = roleService.listRoles(user);
		
		//获取当前登录用户
		Subject subject = SecurityUtils.getSubject();
		String currentUser = subject.getPrincipal().toString();
		
		//该用户是否是超级管理员或普通管理员
		Boolean isAdmin = false;
		Boolean isManager = false;
		
		for(Role currentRole:currentRoles){
			if(currentRole.getName().equals("admin")){
				isAdmin=true;
			}
			if(currentRole.getName().equals("manager")){
				isManager=true;
			}
		}
		
		//当前用户是否为超级管理员
		boolean iAmAdmin = false;
		//获取当前用户拥有的角色的名字列表
		Set<String> roleNames = roleService.listRoleNames(currentUser);
		//遍历当前用户是否拥有超级管理员的角色
		for(String roleName :roleNames){
			if(roleName.equals("admin")){
				iAmAdmin = true;
			}
		}
		
		//是否为本人
		boolean isMe = false;
		if(isAdmin || isManager){
			if(currentUser.equals(user.getName())){
				isMe=true;
			}
		}
		
		/*
		 * 能编辑该用户的条件有3条
		 * 1.该用户既不是普通管理员也不是超级管理员
		 * 2.该用户是普通管理员，那么当前用户就必须是本人或者是超级管理员
		 * 3.该用户是超级管理员，那么当前用户就必须是本人
		 * */
		if((!isManager&&!isAdmin) || (isManager&&(isMe||iAmAdmin)) || (isAdmin&&isMe)){
			//获取所有角色的列表
			List<Role> roles = roleService.list();
			model.addAttribute("roles", roles);
			
			//该用户是否含有对应角色
			Boolean hasRole = false;
			Map<String, Boolean> user_hasRole = new HashMap<>();
			for(Role role:roles){
				for(Role currentRole:currentRoles){
					if(role.getName().equals(currentRole.getName())){
						hasRole=true;
						user_hasRole.put(role.getName(), hasRole); 
					}
				}
			}
			model.addAttribute("user_hasRole", user_hasRole);
			
			model.addAttribute("hasEditRolePermission", iAmAdmin);
			
			return "config/user/editUser";
		}else{
			UnauthorizedException ex = new UnauthorizedException("当前用户没有修改该账号的权限");
			subject.getSession().setAttribute("ex", ex);
			return "unauthorized";
		}
	}

	@RequestMapping("deleteUser")
	public String delete(Model model, Integer id) {
		if(id!=null){
			userService.delete(id.longValue());
			
			model.addAttribute("requestUrl", "listUser");
		}
		
		return "redirect:listUser";
	}

	@RequestMapping("updateUser")
	public String update(Model model, User user, long[] roleIds) {
		userRoleService.setRoles(user, roleIds);

		String password = user.getPassword();
		// 如果在修改的时候没有设置密码，就表示不改动密码
		if (user.getPassword().length() != 0) {
			String salt = new SecureRandomNumberGenerator().nextBytes().toString();
			int times = 2;
			String algorithmName = "md5";
			String encodedPassword = new SimpleHash(algorithmName, password, salt, times).toString();
			user.setSalt(salt);
			user.setPassword(encodedPassword);
		} else
			user.setPassword(null);

		userService.update(user);
		
		model.addAttribute("requestUrl", "listUser");

		return "redirect:listUser";

	}

	@RequestMapping("addUser")
	public String add(Model model, String name, String password) {

		String salt = new SecureRandomNumberGenerator().nextBytes().toString();
		int times = 2;
		String algorithmName = "md5";

		String encodedPassword = new SimpleHash(algorithmName, password, salt, times).toString();

		User u = new User();
		u.setName(name);
		u.setPassword(encodedPassword);
		u.setSalt(salt);
		userService.add(u);
		
		model.addAttribute("requestUrl", "listUser");
		
		return "redirect:listUser";
	}

}