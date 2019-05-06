package pers.qyj.graduationpr.web;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import pers.qyj.graduationpr.mapper.UserinformationMapper;
import pers.qyj.graduationpr.mapper.UserMapper;
import pers.qyj.graduationpr.pojo.Order;
import pers.qyj.graduationpr.pojo.Resource;
import pers.qyj.graduationpr.pojo.Sign;
import pers.qyj.graduationpr.pojo.Userinformation;
import pers.qyj.graduationpr.service.OrderService;
import pers.qyj.graduationpr.service.ResourceService;
import pers.qyj.graduationpr.service.SignService;
import pers.qyj.graduationpr.service.UserinformationService;

@Controller
@RequestMapping("user")
public class UserinformationController {
	@Autowired
	UserMapper userMapper;
	@Autowired
	UserinformationService userinformationService;
	@Autowired
	OrderService orderService;
	@Autowired
	ResourceService resourceService;
	@Autowired
	SignService signService;

	@RequestMapping("userInformation")
	public String userInformation(Model m) {
		Subject subject = SecurityUtils.getSubject();
		// 当前用户
		String currentUser = subject.getPrincipal().toString();
		// System.out.println("当前登录的用户是："+currentUser);
		Userinformation userinformation = userinformationService.getUserInformation(currentUser);

		// 传递name
		m.addAttribute("name", userinformation.getName());

		// 传递headPortrait
		m.addAttribute("headPortrait", userinformation.getHeadPortrait());

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
		m.addAttribute("realName", userinformation.getRealName());

		// 传递Id_card(身份证)
		m.addAttribute("idCard", userinformation.getIdCard());

		return "user/userInformation";

	}

	@RequestMapping(value = "upload", method = RequestMethod.POST)
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

			String oldfilePath = userinformationService.getUserInformation(currentUser).getHeadPortrait();
			File oldFile = new File(uplaodPath + oldfilePath);
			oldFile.delete();

			Userinformation userinformation = new Userinformation();
			userinformation.setHeadPortrait(filePath);
			userinformation.setName(currentUser);
			userinformationService.updateHeadPortrait(filePath,currentUser);

			return filePath;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return "上传失败," + e.getMessage();
		} catch (IOException e) {
			e.printStackTrace();
			return "上传失败," + e.getMessage();
		} catch (NullPointerException e) {
			e.printStackTrace();
			return "-1";
		}
	}

	@RequestMapping("updateUserInformation")
	@ResponseBody
	public String updateUserInformation(HttpServletRequest req, Userinformation userInformation) throws Exception {
		Subject subject = SecurityUtils.getSubject();
		// 当前用户
		String currentUser = subject.getPrincipal().toString();
		if (!currentUser.equals("") && currentUser != null) {// 防止非法操作攻击，比如前端去掉readonly来修改别人的信息
			
			userinformationService.updateUserInformation(userInformation, currentUser);
			return "Success";
		} else {
			return "error";// 这里随便啥都行，只要不是Success
		}
	}

	@RequestMapping("userOrder")
	public String userOrder(Model model, @RequestParam(value = "start", defaultValue = "0") int start,
			@RequestParam(value = "size", defaultValue = "10") int size) throws Exception {
		Subject subject = SecurityUtils.getSubject();
		String currentUser = subject.getPrincipal().toString();

		PageHelper.startPage(start, size, "id");
		List<Order> orders = orderService.getByUserName(currentUser);
		model.addAttribute("orders", orders);
		PageInfo<Order> page = new PageInfo<>(orders);
		model.addAttribute("page", page);

		Map<String, List<Resource>> order_resources = new HashMap<>();
		Map<Integer, Integer> resource_number = new HashMap<>();

		for (Order order : orders) {
			List<Resource> resources = resourceService.list(order);
			List<Sign> signs = signService.list(order);
			order_resources.put(order.getSign(), resources);
			for (Resource resource : resources) {
				int num = 0;
				for (Sign sign : signs) {
					if (resource.getId().equals(sign.getReid())) {
						num++;
					}
				}
				if (num > 0) {
					resource_number.put(resource.getId(), num);
				}
			}
		}
		model.addAttribute("order_resources", order_resources);
		model.addAttribute("resource_number", resource_number);
		return "user/userOrder";
	}

}
