package pers.qyj.graduationpr.service;

import java.util.List;

import pers.qyj.graduationpr.pojo.Order;

public interface OrderService {

	List<Order> list();

	void add(Order order);

	Order getById(Integer orderId);

	Order getBySign(String out_trade_no);

	void updat(Order order);

	void delete(Integer id);

	List<Order> getByUserName(String currentUser);

	List<Order> getByOrderSign(String search);

}
