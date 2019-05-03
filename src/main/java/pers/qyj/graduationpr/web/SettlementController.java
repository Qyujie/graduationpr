package pers.qyj.graduationpr.web;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.catalina.servlet4preview.http.HttpServletRequest;
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
import pers.qyj.graduationpr.pojo.SignUser;
import pers.qyj.graduationpr.pojo.SystemConfiguration;
import pers.qyj.graduationpr.service.OrderService;
import pers.qyj.graduationpr.service.ResourceService;
import pers.qyj.graduationpr.service.RoomtypeService;
import pers.qyj.graduationpr.service.ShoppingCartService;
import pers.qyj.graduationpr.service.SignService;
import pers.qyj.graduationpr.service.SignUserService;
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
	SignUserService signUserService;

	@RequestMapping("/settlement")
	public String settlement(Model model) {
		try {
			Subject subject = SecurityUtils.getSubject();
			String currentUser = subject.getPrincipal().toString();
			Long uid = userService.getByName(currentUser).getId();
			// 获取购物车中已经勾选的
			List<ShoppingCart> shoppingCarts = shoppingCartService.list(uid, true);
			
			if(shoppingCarts.size()==0){
				return "redirect:roomInformation";
			}else{
				model.addAttribute("shoppingCarts", shoppingCarts);

				Map<Integer, Resource> id_resource = new HashMap<>();
				Map<Integer, Roomtype> id_roomtype = new HashMap<>();
				Map<Integer, Integer> id_remain = new HashMap<>();
				for (ShoppingCart shoppingCart : shoppingCarts) {
					id_resource.put(shoppingCart.getId(), resourceService.get(shoppingCart.getRid()));
					id_roomtype.put(shoppingCart.getId(),
							roomtypeService.get(resourceService.get(shoppingCart.getRid()).getRoomtype()));
					id_remain.put(shoppingCart.getId(),
							signService.getRemainByReid(shoppingCart.getRid(),
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

				List<ShoppingCart> newShoppingCarts = new ArrayList<>();
				for (ShoppingCart shoppingCart : shoppingCarts) {
					if (shoppingCart.getNumber() >= 1) {
						for (int i = 0; i < shoppingCart.getNumber(); i++) {
							newShoppingCarts.add(shoppingCart);
						}
					}
				}
				model.addAttribute("newShoppingCarts", newShoppingCarts);
			}
		} catch (NullPointerException e) {
			return "login";
		}

		return "settlement";
	}

	@RequestMapping("/generateOrder")
	public String generateOrder(Model model, HttpServletRequest req, Order order, String[] userLastname,
			String[] userFirstname, Integer[] shoppingCartId) throws Exception {
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

			// 生成订单号
			String osign = getOrderSign(uid, order.getInvoiceType(), order.getInvoiceGroup());
			order.setSign(osign);

			// 发票抬头
			if (order.getInvoiceType() == 1 || order.getInvoiceGroup() == 1) {// 个人
				order.setInvoiceTitle(currentUser);
			}

			// 插入数据库Orders
			orderService.add(order);

			req.getSession().setAttribute("orderId", order.getId());

			// 插入数据库Signs
			int k = 0;
			for (int i = 0; i < shoppingCartId.length; i++) {
				ShoppingCart shoppingCart = shoppingCartService.getById(shoppingCartId[i]);
				int signNumber = shoppingCart.getNumber();
				int reid = shoppingCart.getRid();

				Sign sign = new Sign();
				sign.setOid(order.getId());
				sign.setReid(reid);
				sign.setArrivalDate(shoppingCart.getArrivalDate());
				sign.setDepatureDate(shoppingCart.getDepatureDate());
				sign.setStatus(1);
				// 资源号
				String ssign = getSignSign(uid, reid, signNumber);
				sign.setSign(ssign);

				signService.add(sign);

				// 插入数据库Sign_user
				int peopleNumber = roomtypeService.get(resourceService.get(reid).getRoomtype()).getPeople();
				if (userLastname.length < 1 || userFirstname.length < 1) {
					
					if (userLastname.length < 1) {
						userLastname = new String[1];
						userLastname[0] = "";
					}
					if (userFirstname.length < 1) {
						userFirstname = new String[1];
						userFirstname[0] = "";
					}

				}
				for (int j = 0; j < peopleNumber; j++) {
					SignUser signUser = new SignUser();
					signUser.setSign(ssign);
					signUser.setUserLastname(userLastname[k]);
					signUser.setUserFirstname(userFirstname[k]);
					k++;
					signUserService.add(signUser);
				}
			}

			// 查看表单数据
			ReflectUtil reflectUtil = new ReflectUtil();
			reflectUtil.printltAttrAndValue("pers.qyj.graduationpr.pojo.Order", order);
			System.out.println(Arrays.toString(userLastname));
			System.out.println(Arrays.toString(userFirstname));
			System.out.println(Arrays.toString(shoppingCartId));

			payTimer(order.getId(), uid);

		} catch (NullPointerException e) {
			return "redirect:roomInformation";
		}

		return "redirect:pay";
	}

	private String getOrderSign(Long uid, Integer invoiceType, Integer invoiceGroup) {
		String uidStr = "0" + uid;
		uidStr = uidStr.substring(uidStr.length() - 2);

		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		Date nowDate = new Date();
		String dateStr = sdf.format(nowDate);// 订单号的一部分
		dateStr = dateStr.substring(dateStr.length() - 6);

		String invoiceTypeStr = "0" + invoiceType;
		invoiceTypeStr = invoiceTypeStr.substring(invoiceTypeStr.length() - 1);

		String invoiceGroupStr = "0" + invoiceGroup;
		invoiceGroupStr = invoiceGroupStr.substring(invoiceGroupStr.length() - 1);

		String timeMillisStr = System.currentTimeMillis() + "";
		timeMillisStr = timeMillisStr.substring(timeMillisStr.length() - 6);

		return uidStr + dateStr + invoiceTypeStr + invoiceGroupStr + timeMillisStr;
	}

	private String getSignSign(Long uid, Integer reid, Integer number) {
		String uidStr = "0" + uid;
		uidStr = uidStr.substring(uidStr.length() - 2);

		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		Date nowDate = new Date();
		String dateStr = sdf.format(nowDate);// 订单号的一部分
		dateStr = dateStr.substring(dateStr.length() - 6);

		String reidStr = "0" + reid;
		reidStr = reidStr.substring(reidStr.length() - 1);

		String numberStr = "0" + number;
		numberStr = numberStr.substring(numberStr.length() - 1);

		String timeMillisStr = System.currentTimeMillis() + "";
		timeMillisStr = timeMillisStr.substring(timeMillisStr.length() - 6);

		return uidStr + dateStr + reidStr + numberStr + timeMillisStr;
	}

	// 定义一个计时线程，时间结束还没有支付，就删除数据库相应资源数据
	private void payTimer(Integer orderId, Long uid) {
		Thread timer = new Thread() {
			public void run() {
				try {
					sleep(1000 * 60 * 15);
					payError(orderId, uid);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		};

		System.out.println("支付倒计时开始");
		timer.start();
	}

	public void payError(Integer orderId, Long uid) {
		Order order = orderService.getById(orderId);
		if (order.getPayStatus() != 1 || order.getTradeStatus() != 1) {
			System.out.println("支付失败");

			// 更新订单表，设置该条数据为支付失败(-1)
			order.setPayStatus(-1);
			order.setTradeStatus(-1);
			orderService.updat(order);

			// 删除该订单对应住客表
			List<Sign> signs = signService.list(order);
			for (Sign sign : signs) {
				signUserService.deleteBySign(sign);
			}

			// 删除订单对应资源表
			signService.deleteByOid(orderId);

			// 重置购物车为未选中
			Integer[] shoppingCartId = new Integer[0];
			shoppingCartService.updateChecked(uid, shoppingCartId);
		}
	}
}
