package pers.qyj.graduationpr.web;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import pers.qyj.graduationpr.service.UserinformationService;
import pers.qyj.graduationpr.pojo.Userinformation;

@Controller
public class CommonController {

	@Autowired
	UserinformationService userinformationService;

	@RequestMapping("/getHeadPortrait")
	@ResponseBody
	public String getHeadPortrait(HttpServletRequest req) {
		Subject subject = SecurityUtils.getSubject();
		// 当前用户
		String currentUser = null;
		try {
			currentUser = subject.getPrincipal().toString();
			Userinformation userinformation = userinformationService.getUserInformation(currentUser);
			if (userinformation.getHeadPortrait() != null)
				return userinformation.getHeadPortrait();
			else
				return "nullHeadPortrait";
		} catch (NullPointerException e) {
			return "null";
		}
	}
}