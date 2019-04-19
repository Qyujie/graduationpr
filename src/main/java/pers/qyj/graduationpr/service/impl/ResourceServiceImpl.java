package pers.qyj.graduationpr.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pers.qyj.graduationpr.mapper.ResourceMapper;
import pers.qyj.graduationpr.mapper.ResourceOrderMapper;
import pers.qyj.graduationpr.pojo.Order;
import pers.qyj.graduationpr.pojo.Resource;
import pers.qyj.graduationpr.pojo.ResourceExample;
import pers.qyj.graduationpr.pojo.ResourceOrder;
import pers.qyj.graduationpr.pojo.ResourceOrderExample;
import pers.qyj.graduationpr.service.ResourceFacilityService;
import pers.qyj.graduationpr.service.ResourcePolicyService;
import pers.qyj.graduationpr.service.ResourceService;
@Service
public class ResourceServiceImpl implements ResourceService {
	@Autowired
	ResourceMapper resourceMapper;
	@Autowired
	ResourceFacilityService resourceFacilityService;
	@Autowired
	ResourcePolicyService resourcePolicyService;
	@Autowired
	ResourceOrderMapper resourceOrderMapper;
	
	@Override
	public List<Resource> list() {
		ResourceExample example = new ResourceExample();
		example.setOrderByClause("id");
		return resourceMapper.selectByExample(example);
	}

	@Override
	public String getPassword(String name) {
		// TODO 自动生成的方法存根
		return null;
	}

	@Override
	public Resource getByName(String name) {
		// TODO 自动生成的方法存根
		return null;
	}

	@Override
	public void add(Resource resource) {
		resourceMapper.insert(resource);
	}

	@Override
	public void delete(Integer id) {
		resourceMapper.deleteByPrimaryKey(id);
		resourceFacilityService.deleteByResource(id);
		resourcePolicyService.deleteByResource(id);
	}

	@Override
	public Resource get(Integer id) {
		return resourceMapper.selectByPrimaryKey(id);
	}

	@Override
	public void update(Resource resource) {
		resourceMapper.updateByPrimaryKeySelective(resource);
	}

	@Override
	public List<Resource> list(Order order) {
		List<Resource> resources = new ArrayList<>();
		ResourceOrderExample example = new ResourceOrderExample();
		example.createCriteria().andOidEqualTo(order.getId());
		List<ResourceOrder> ros = resourceOrderMapper.selectByExample(example);
		for (ResourceOrder resourceOrder : ros) {
			resources.add(resourceMapper.selectByPrimaryKey(resourceOrder.getReid()));
		}
		return resources;
	}

	@Override
	public List<Resource> list(String roomName) {
		ResourceExample example = new ResourceExample();
		example.createCriteria().andRoomtypeEqualTo(roomName);
		List<Resource> resources = resourceMapper.selectByExample(example);
		return resources;
	}

}
