package pers.qyj.graduationpr.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pers.qyj.graduationpr.mapper.ResourceOrderMapper;
import pers.qyj.graduationpr.pojo.Order;
import pers.qyj.graduationpr.pojo.Resource;
import pers.qyj.graduationpr.pojo.ResourceOrder;
import pers.qyj.graduationpr.pojo.ResourceOrderExample;
import pers.qyj.graduationpr.pojo.RoleExample;
import pers.qyj.graduationpr.service.ResourceOrderService;

@Service
public class ResourceOrderServiceImpl implements ResourceOrderService {

	@Autowired
	ResourceOrderMapper resourceOrderMapper;
	
	@Override
	public List<ResourceOrder> list() {
		ResourceOrderExample example = new ResourceOrderExample();
		example.setOrderByClause("id");
		List<ResourceOrder> resourceOrders = resourceOrderMapper.selectByExample(example);
		return resourceOrders;
	}
	
	@Override
	public List<ResourceOrder> list(Order order) {
		ResourceOrderExample example = new ResourceOrderExample();
		example.createCriteria().andOidEqualTo(order.getId());
		List<ResourceOrder> resourceOrders = resourceOrderMapper.selectByExample(example);
		return resourceOrders;
	}
	
	@Override
	public List<ResourceOrder> list(Resource resource) {
		ResourceOrderExample example = new ResourceOrderExample();
		example.createCriteria().andReidEqualTo(resource.getId());
		List<ResourceOrder> resourceOrders = resourceOrderMapper.selectByExample(example);
		return resourceOrders;
	}
}
