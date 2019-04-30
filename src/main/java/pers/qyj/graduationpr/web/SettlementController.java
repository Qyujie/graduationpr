package pers.qyj.graduationpr.web;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import pers.qyj.graduationpr.mapper.SignMapper;
import pers.qyj.graduationpr.pojo.Order;
import pers.qyj.graduationpr.pojo.Resource;
import pers.qyj.graduationpr.pojo.Roomtype;
import pers.qyj.graduationpr.pojo.ShoppingCart;
import pers.qyj.graduationpr.pojo.Sign;
import pers.qyj.graduationpr.pojo.SystemConfiguration;
import pers.qyj.graduationpr.service.OrderService;
import pers.qyj.graduationpr.service.ResourceService;
import pers.qyj.graduationpr.service.RoomtypeService;
import pers.qyj.graduationpr.service.ShoppingCartService;
import pers.qyj.graduationpr.service.SignService;
import pers.qyj.graduationpr.service.SystemConfigurationService;
import pers.qyj.graduationpr.service.UserService;
import pers.qyj.graduationpr.util.ReflectUtil;

@Controller
public class SettlementController {
	@Autowired
	ShoppingCartService shoppingCartService;
	@Autowired
	UserService userService;
	@Autowired
	ResourceService resourceService;
	@Autowired
	RoomtypeService roomtypeService;
	@Autowired
	SystemConfigurationService systemConfigurationService;
	@Autowired
	SignService signService;
	@Autowired
	OrderService orderService;
	@Autowired
	SignMapper signMapper;

	@RequestMapping("/settlement")
	public String settlement(Model model) {
		try {
			Subject subject = SecurityUtils.getSubject();
			String currentUser = subject.getPrincipal().toString();
			Long uid = userService.getByName(currentUser).getId();
			// 获取购物车中已经勾选的
			List<ShoppingCart> shoppingCarts = shoppingCartService.list(uid, true);

			model.addAttribute("shoppingCarts", shoppingCarts);

			
			Map<Integer, Resource> id_resource = new HashMap<>();
			Map<Integer, Roomtype> id_roomtype = new HashMap<>();
			Map<Integer, Integer> id_remain = new HashMap<>();
			for (ShoppingCart shoppingCart : shoppingCarts) {
				id_resource.put(shoppingCart.getId(), resourceService.get(shoppingCart.getRid()));
				id_roomtype.put(shoppingCart.getId(),roomtypeService.get(resourceService.get(shoppingCart.getRid()).getRoomtype()));
				id_remain.put(shoppingCart.getId(), 
						signService.getRemainByReid(
								shoppingCart.getRid(),
								new java.sql.Date(shoppingCart.getArrivalDate().getTime()),
								new java.sql.Date(shoppingCart.getDepatureDate().getTime())));
			}
			
			model.addAttribute("id_resource", id_resource);
			model.addAttribute("id_roomtype", id_roomtype);
			model.addAttribute("id_remain", id_remain);
			
			String arrivalTime = systemConfigurationService.getArrivalTime();
			String depatureTime = systemConfigurationService.getDepatureTime();
			model.addAttribute("arrivalTime", arrivalTime);
			model.addAttribute("depatureTime", depatureTime);

		} catch (NullPointerException e) {
			return "login";
		}

		return "settlement";
	}
	
	@RequestMapping("/generateOrder")
	public String generateOrder(Model model,Order order,String[] userLastname,String[] userFirstname,Integer[] shoppingCartId) throws Exception {
		try {
			Subject subject = SecurityUtils.getSubject();
			String currentUser = subject.getPrincipal().toString();
			Long uid = userService.getByName(currentUser).getId();
			
			order.setTradeStatus(0);
			order.setPayStatus(0);
			order.setUserName(currentUser);
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			
			Date nowDate = new Date();
			java.sql.Date date = new java.sql.Date(sdf.parse(sdf.format(nowDate)).getTime());
			order.setDate(date);
			
			//生成订单号
			String osign = getOrderSign(uid,order.getInvoiceType(),order.getInvoiceGroup());
			order.setSign(osign);
			
			//发票抬头
			if(order.getInvoiceType()==1 || order.getInvoiceGroup()==1){//个人
				order.setInvoiceTitle(currentUser);
			}
			
			//插入数据库Orders
			orderService.add(order);
			
			
			for(int i=0;i<shoppingCartId.length;i++){
				ShoppingCart shoppingCart = shoppingCartService.getById(shoppingCartId[i]);
				Sign sign = new Sign();
				sign.setOid(order.getId());
				sign.setReid(shoppingCart.getRid());
				sign.setArrivalDate(shoppingCart.getArrivalDate());
				sign.setDepatureDate(shoppingCart.getDepatureDate());
				sign.setNumber(shoppingCart.getNumber());
				sign.setStatus(1);
				
				//资源号
				String ssign = getSignSign(uid,sign.getReid(),sign.getNumber());
				sign.setSign(ssign);
				
				signMapper.insert(sign);
			}
			
			
			ReflectUtil reflectUtil = new ReflectUtil();
			reflectUtil.printltAttrAndValue("pers.qyj.graduationpr.pojo.Order", order);
			
			System.out.println(Arrays.toString(userLastname));
			System.out.println(Arrays.toString(userFirstname));
			System.out.println(Arrays.toString(shoppingCartId));

		} catch (NullPointerException e) {
			return "login";
		}

		return "pay";
	}
	
	private String getOrderSign(Long uid,Integer invoiceType,Integer invoiceGroup){
		String uidStr = "000"+uid;
		uidStr = uidStr.substring(uidStr.length()-4);
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		Date nowDate = new Date();
		String dateStr = sdf.format(nowDate);//订单号的一部分
		
		String invoiceTypeStr = "0"+invoiceType;
		invoiceTypeStr = invoiceTypeStr.substring(invoiceTypeStr.length()-2);
		
		String invoiceGroupStr = "0"+invoiceGroup;
		invoiceGroupStr = invoiceGroupStr.substring(invoiceGroupStr.length()-2);
		
		return uidStr+dateStr+invoiceTypeStr+invoiceGroupStr;
	}
	
	private String getSignSign(Long uid,Integer reid,Integer number){
		String uidStr = "000"+uid;
		uidStr = uidStr.substring(uidStr.length()-4);
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		Date nowDate = new Date();
		String dateStr = sdf.format(nowDate);//订单号的一部分
		
		String reidStr = "0"+reid;
		reidStr = reidStr.substring(uidStr.length()-2);
		
		String numberStr = "0"+number;
		numberStr = numberStr.substring(numberStr.length()-2);
		
		return uidStr+dateStr+reidStr+numberStr;
	}
}
