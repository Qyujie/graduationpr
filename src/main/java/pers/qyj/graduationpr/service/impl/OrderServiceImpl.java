package pers.qyj.graduationpr.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pers.qyj.graduationpr.mapper.OrderMapper;
import pers.qyj.graduationpr.pojo.Order;
import pers.qyj.graduationpr.pojo.OrderExample;
import pers.qyj.graduationpr.service.OrderService;

@Service
public class OrderServiceImpl implements OrderService {
	@Autowired
	OrderMapper orderMapper;

	@Override
	public List<Order> list() {
		OrderExample example = new OrderExample();
		example.setOrderByClause("id");
		return orderMapper.selectByExample(example);
	}
	
	@Override
	public void add(Order order) {
		orderMapper.insert(order);
	}
}
