package pers.qyj.graduationpr.web;

import java.util.List;

import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.apache.catalina.util.URLEncoder;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import pers.qyj.graduationpr.pojo.Order;
import pers.qyj.graduationpr.pojo.Resource;
import pers.qyj.graduationpr.pojo.ShoppingCart;
import pers.qyj.graduationpr.pojo.Sign;
import pers.qyj.graduationpr.service.OrderService;
import pers.qyj.graduationpr.service.ResourceService;
import pers.qyj.graduationpr.service.ShoppingCartService;
import pers.qyj.graduationpr.service.SignService;
import pers.qyj.graduationpr.service.UserService;

@Controller
public class PayController {
	@Autowired
	OrderService orderService;
	@Autowired
	SignService signService;
	@Autowired
	ResourceService resourceService;
	@Autowired
	UserService userService;
	@Autowired
	ShoppingCartService shoppingCartService;

	@RequestMapping("pay")
	public String pay(Model model, HttpServletRequest req, Integer payType) {
		try {
			Subject subject = SecurityUtils.getSubject();
			String currentUser = subject.getPrincipal().toString();

			int orderId = (int) req.getSession().getAttribute("orderId");
			Order order = orderService.getById(orderId);

			List<Sign> signs = signService.list(order);
			int price = 0;
			for (Sign sign : signs) {
				price = price + resourceService.get(sign.getReid()).getPrice();
			}
			model.addAttribute("price", price);

			if (order.getInvoiceType() == 1) {
				model.addAttribute("invoiceType", "电子发票");
			} else if (order.getInvoiceType() == 2) {
				model.addAttribute("invoiceType", "纸质发票");
			}

			if (order.getInvoiceGroup() == 1) {
				model.addAttribute("invoiceGroup", "个人");
			} else if (order.getInvoiceGroup() == 2) {
				model.addAttribute("invoiceGroup", "单位");
			}
			model.addAttribute("order", order);

			return "pay";
		} catch (NullPointerException e) {
			return "login";
		}
	}

	@RequestMapping("payMain")
	public String payMain(Model model, HttpServletRequest req, Integer payType) {
		try {
			Subject subject = SecurityUtils.getSubject();
			String currentUser = subject.getPrincipal().toString();
			int orderId = (int) req.getSession().getAttribute("orderId");
			Order order = orderService.getById(orderId);

			if(order.getPayStatus()==1 || order.getPayStatus()==1){
				return "redirect:home";
			}else{
				List<Sign> signs = signService.list(order);
				int price = 0;
				String roomtypes = "";
				for (Sign sign : signs) {
					Resource resource = resourceService.get(sign.getReid());
					price = price + resource.getPrice();
					roomtypes = roomtypes + "/" + resource.getRoomtype();
				}

				String partner = "10086";
				String subjects = "999";// 商品名称
				String body = "666";// 商品描述
				String out_trade_no = order.getSign();// 订单号
				String total_fee = price + ""; // 总金额
				String seller_email = "942504986@qq.com";// 卖家邮箱
//				String return_url = "http://localhost/payResult";// 回调地址
				String return_url = "http://119.23.50.158:8090/payResult";// 回调地址
				String key = "123456";
				String sign = DigestUtils.md5DigestAsHex((total_fee + partner + out_trade_no + subjects + key).getBytes());
				// 重定向到支付的页面
				return "redirect:http://paytest.rupeng.cn/AliPay/PayGate.ashx?partner=" + partner + "&return_url="
						+ return_url + "&subject=" + subjects + "&body=" + body + "&out_trade_no=" + out_trade_no
						+ "&total_fee=" + total_fee + "&seller_email=" + seller_email + "&sign=" + sign;
			}
		} catch (NullPointerException e) {
			return "login";
		}
	}

	@RequestMapping("payResult")
	public String payResult(String out_trade_no, String returncode, String total_fee, String sign) {
		System.out.println(sign);System.out.println(DigestUtils.md5DigestAsHex((out_trade_no + returncode + total_fee + "123456").getBytes()));
		if (sign.equals(DigestUtils.md5DigestAsHex((out_trade_no + returncode + total_fee + "123456").getBytes())))// 防止用户随意篡改数据
		{
			System.out.println("数据校验成功");

			Order order = orderService.getBySign(out_trade_no);
			
			Long uid = userService.getByName(order.getUserName()).getId();
			
			if (returncode.equals("ok")) {
				System.out.println("支付成功，支付的金额为" + total_fee);
				order.setPayStatus(1);
				order.setTradeStatus(1);
				orderService.updat(order);
				
				List<Sign> signs = signService.list(order);
				for (Sign sign2 : signs) {
					shoppingCartService.delete(uid,sign2.getReid(),
							new java.sql.Date(sign2.getArrivalDate().getTime()),
							new java.sql.Date(sign2.getDepatureDate().getTime()));
				}
			} else {
				System.out.println("支付失败");
				
				int oid = Integer.parseInt(out_trade_no);
				SettlementController sc = new SettlementController();
				sc.payError(oid,uid);
			}
		} else {
			System.out.println("数据校验失败");
		}

		return "redirect:home";

	}

	@RequestMapping("payAccess")
	public String payAccess(Model model, HttpServletRequest req, Integer payType) {
		try {
			Subject subject = SecurityUtils.getSubject();
			String currentUser = subject.getPrincipal().toString();

			int orderId = (int) req.getSession().getAttribute("orderId");
			Order order = orderService.getById(orderId);
			order.setPayStatus(1);
			order.setTradeStatus(1);

			return "redirect:home";
		} catch (NullPointerException e) {
			return "login";
		}
	}
}
