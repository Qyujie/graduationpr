package pers.qyj.graduationpr.web;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import pers.qyj.graduationpr.mapper.UserInformationMapper;
import pers.qyj.graduationpr.mapper.UserMapper;
import pers.qyj.graduationpr.pojo.UserInformation;

@Controller
public class UserInformationController {
	@Autowired
	UserMapper userMapper;
	@Autowired
	UserInformationMapper userInformationMapper;

	@RequestMapping("/userInformation")
	public String userInformation(Model m) {
		Subject subject = SecurityUtils.getSubject();
		// 当前用户
		String currentUser = null;
		try {
			currentUser = subject.getPrincipal().toString();
//			 System.out.println("当前登录的用户是："+currentUser);
			UserInformation userinformation = userInformationMapper.getUserInformation(currentUser);

			// 传递name
			m.addAttribute("name", userinformation.getName());

			// 传递headPortrait
			m.addAttribute("headPortrait", userinformation.getHead_portrait());

			// 传递birthday
			String[] birthday = userinformation.getBirthday().split("-");
			if (birthday[0].equals("0"))
				birthday[0] = "";
			if (birthday[1].equals("0"))
				birthday[1] = "";
			if (birthday[2].equals("0"))
				birthday[2] = "";

			m.addAttribute("birthdayYear", birthday[0]);
			m.addAttribute("birthdayMonth", birthday[1]);
			m.addAttribute("birthdayDay", birthday[2]);

			// 传递Sex
			m.addAttribute("sex", userinformation.getSex());

			// 传递Phone
			m.addAttribute("phone", userinformation.getPhone());

			// 传递Prefecture(地区)
			m.addAttribute("prefecture", userinformation.getPrefecture());

			// 传递Real_name(真实姓名)
			m.addAttribute("realName", userinformation.getReal_name());

			// 传递Id_card(身份证)
			m.addAttribute("idCard", userinformation.getId_card());
			
			return "userInformation";

		} catch (NullPointerException e) {
			return "userInformation";
		}

	}

	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	@ResponseBody
	public String upload(@RequestParam("file") MultipartFile file) {
		try {
			Subject subject = SecurityUtils.getSubject();
			// 当前用户
			String currentUser = subject.getPrincipal().toString();

			final String uplaodPath = "D:/uploadFiles/";
			final String namePath = "/" + currentUser + "/";
			String fileName = System.currentTimeMillis() + file.getOriginalFilename();
			String filePath = namePath + fileName;
			String destFileName = uplaodPath + filePath;
			File destFile = new File(destFileName);
			destFile.getParentFile().mkdirs();
			file.transferTo(destFile);

			String oldfilePath = userInformationMapper.getUserInformation(currentUser).getHead_portrait();
			File oldFile = new File(uplaodPath + oldfilePath);
			oldFile.delete();

			UserInformation userinformation = new UserInformation();
			userinformation.setHead_portrait(filePath);
			userinformation.setName(currentUser);
			userInformationMapper.updateHeadPortrait(userinformation);

			return filePath;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return "上传失败," + e.getMessage();
		} catch (IOException e) {
			e.printStackTrace();
			return "上传失败," + e.getMessage();
		}
	}

	@RequestMapping("/updateUserInformation")
	@ResponseBody
	public String updateUserInformation(HttpServletRequest req, UserInformation userInformation) throws Exception {
		Subject subject = SecurityUtils.getSubject();
		// 当前用户
		String currentUser = subject.getPrincipal().toString(); 
		if(!currentUser.equals("") && currentUser!=null){//防止非法操作攻击，比如前端去掉readonly来修改别人的信息
			userInformationMapper.updateUserInformation(userInformation,currentUser);
			return "Success";
		}else{
			return "error";//这里随便啥都行，只要不是Success
		}
	}
}
