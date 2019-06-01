package pers.qyj.graduationpr.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import pers.qyj.graduationpr.pojo.Order;
import pers.qyj.graduationpr.pojo.Roomtype;
import pers.qyj.graduationpr.pojo.Sign;
import pers.qyj.graduationpr.pojo.SignUser;
import pers.qyj.graduationpr.service.OrderService;
import pers.qyj.graduationpr.service.ResourceService;
import pers.qyj.graduationpr.service.SignService;
import pers.qyj.graduationpr.service.SignUserService;
import pers.qyj.graduationpr.service.UserService;


@Controller
@RequestMapping("config/resource")
public class SignController {
	@Autowired
	SignService signService;
	@Autowired
	UserService userService;
	@Autowired
	OrderService orderService;
	@Autowired
	ResourceService resourceService;
	@Autowired
	SignUserService signUserService;
	
	@RequestMapping("listSign")
	public String list(Model model,
	           @RequestParam(value = "start", defaultValue = "0") int start,
	           @RequestParam(value = "size", defaultValue = "10") int size,
	           @RequestParam(value = "search", defaultValue = "") String search) {
		List<Sign> signs = new ArrayList<>();
		if(search.equals("")){
			PageHelper.startPage(start,size,"id");
			signs = signService.list();
		}else{
			signs = signService.getBySign(search);
			if(signs.size()==0){
				signs = signService.getByOrderSign(search);
			}
		}
		
		model.addAttribute("signs", signs);
		PageInfo<Sign> page = new PageInfo<>(signs);
		model.addAttribute("page", page);

		Map<Integer, String> signId_roomtype = new HashMap<>();
		Map<Integer, String> signId_orderSign = new HashMap<>();
		Map<Integer, String> signId_userName = new HashMap<>();
		for (Sign sign : signs) {
			int sid = sign.getId();
			if(sign.getStatus()==1){
				Order order = orderService.getById(sign.getOid());
				String orderSign = order.getSign();
				
				signId_orderSign.put(sid,orderSign);
				String userName = order.getUserName();
				signId_userName.put(sid, userName);
			}
			System.out.println(sign.getReid());
			String roomtype = resourceService.get(sign.getReid()).getRoomtype();
			signId_roomtype.put(sid, roomtype);
		} 
		model.addAttribute("signId_roomtype", signId_roomtype);
		model.addAttribute("signId_orderSign", signId_orderSign);
		model.addAttribute("signId_userName", signId_userName);

		model.addAttribute("requestUrl", "listSign");

		return "config/resource/listSign";
	}
	
	
	 @RequestMapping("deleteSign")
	 public String deleteSign(Model model, int id) {
		 signService.delete(id);
		 return "redirect:listSign";
	 }
	 
	 
	 @RequestMapping("editSign")
	 public String editSign(Model model, int id) {
		 Sign sign = signService.getById(id);
		 Order order = orderService.getById(sign.getOid());
		 List<SignUser> signUsers = signUserService.getBySign(sign.getSign());
		 for (SignUser signUser : signUsers) {
			System.out.println(signUser.getSign());
		}
		 model.addAttribute("sign",sign);
		 model.addAttribute("order",order);
		 model.addAttribute("signUsers",signUsers);
		 return "config/resource/signInformation";
	 }
	 
}