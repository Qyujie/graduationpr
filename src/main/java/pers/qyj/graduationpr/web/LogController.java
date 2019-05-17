package pers.qyj.graduationpr.web;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import pers.qyj.graduationpr.pojo.Log;
import pers.qyj.graduationpr.service.LogService;
import pers.qyj.graduationpr.service.UserService;

@Controller
public class LogController {
	@Autowired
	LogService logService;
	@Autowired
	UserService userService;

	@RequestMapping("/log")
	public String log(Model model) {
		List<Log> logs = logService.list();
		model.addAttribute("logs", logs);
		return "log";
	}

	@RequestMapping("/logSubmit")
	public String logSubmit(Model model, Log lg) throws Exception {
		try {
			Subject subject = SecurityUtils.getSubject();
			String currentUser = subject.getPrincipal().toString();
			Long uid = userService.getByName(currentUser).getId();

			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date nowDate = new Date();
			Date date = sdf.parse(sdf.format(nowDate));
			lg.setUid(uid);
			lg.setTime(date);
			logService.add(lg);
			return "redirect:log";
		} catch (NullPointerException e) {
			return "redirect:login";
		}
	}
}
