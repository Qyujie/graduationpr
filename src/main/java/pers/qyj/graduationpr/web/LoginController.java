package pers.qyj.graduationpr.web;


import java.net.URLEncoder;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import pers.qyj.graduationpr.mapper.UserInformationMapper;
import pers.qyj.graduationpr.mapper.UserMapper;
import pers.qyj.graduationpr.pojo.User;
import pers.qyj.graduationpr.pojo.UserInformation;
import pers.qyj.graduationpr.service.UserRoleService;


@Controller
@RequestMapping("")
public class LoginController {

	@Autowired
	UserMapper userMapper;
	@Autowired
	UserInformationMapper userInformationMapper;
	@Autowired
	UserRoleService userRoleService;
	
	@RequestMapping("/addUser")
	@ResponseBody
	public String listUser(User c) throws Exception {
		User result = userMapper.checkName(c);
		if (result != null) {
			return "已注册";
		} else {
			String salt = new SecureRandomNumberGenerator().nextBytes().toString();
			int times = 2;
			String algorithmName = "md5";

			String encodedPassword = new SimpleHash(algorithmName, c.getPassword(), salt, times).toString();

			User u = new User();
			u.setName(c.getName());
			u.setPassword(encodedPassword);
			u.setSalt(salt);
			userMapper.save(u);
			
			User user = userMapper.getUserByName(c.getName());
					
			long[] roleIds = {(long) 3};
			userRoleService.setRoles(user, roleIds);
			
			UserInformation userinformation = new UserInformation();
			userinformation.setName(c.getName());
			userInformationMapper.saveUserInformation(userinformation);
			return "registerSuccess";
		}
		
		
	}

	
	@RequestMapping("/verificationUser")
	@ResponseBody
	public String verificationUser(User user,HttpServletResponse  res,boolean rememberme) {
		
		Subject subject = SecurityUtils.getSubject();
		try {
			UsernamePasswordToken token = new UsernamePasswordToken(user.getName(), user.getPassword());
			token.setRememberMe(rememberme);
			subject.login(token);
			System.out.println("登陆成功");
			System.out.println("rememberme:"+rememberme);
			subject.getSession().setAttribute("id", user.getId());
	        	
			return "登录成功";
			
		}catch (Exception e) {
        	System.out.println(e.getMessage());
			return "登录失败";
		}
	}

}