package pers.qyj.graduationpr.web;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import pers.qyj.graduationpr.mapper.UserInformationMapper;
import pers.qyj.graduationpr.pojo.UserInformation;

@Controller
public class CommonController {

	@Autowired
	UserInformationMapper userInformationMapper;

	@RequestMapping("/getHeadPortrait")
	@ResponseBody
	public String getHeadPortrait(HttpServletRequest req) {
		Subject subject = SecurityUtils.getSubject();
		// 当前用户
		String currentUser = null;
		try {
			currentUser = subject.getPrincipal().toString();
			UserInformation userinformation = userInformationMapper.getUserInformation(currentUser);
			if (userinformation.getHead_portrait() != null)
				return userinformation.getHead_portrait();
			else
				return "nullHeadPortrait";
		} catch (NullPointerException e) {
			return "null";
		}
	}
}