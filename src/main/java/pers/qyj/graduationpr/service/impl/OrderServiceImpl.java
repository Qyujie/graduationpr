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

	@Override
	public Order getById(Integer orderId) {
		return orderMapper.selectByPrimaryKey(orderId);
	}

	@Override
	public Order getBySign(String out_trade_no) {
		OrderExample example = new OrderExample();
		example.createCriteria().andSignEqualTo(out_trade_no);
		return orderMapper.selectByExample(example).get(0);
	}

	@Override
	public void updat(Order order) {
		orderMapper.updateByPrimaryKey(order);
	}

	@Override
	public void delete(Integer id) {
		orderMapper.deleteByPrimaryKey(id);
	}

	@Override
	public List<Order> getByUserName(String currentUser) {
		OrderExample example = new OrderExample();
		example.createCriteria().andUserNameEqualTo(currentUser);
		return orderMapper.selectByExample(example);
	}

	@Override
	public List<Order> getByOrderSign(String search) {
		OrderExample example = new OrderExample();
		example.createCriteria().andSignEqualTo(search);
		return orderMapper.selectByExample(example);
	}
}
