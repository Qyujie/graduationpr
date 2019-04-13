package pers.qyj.graduationpr.service;

import java.util.List;

import pers.qyj.graduationpr.pojo.Order;

public interface OrderService {

	List<Order> list();

	void add(Order order);

}
