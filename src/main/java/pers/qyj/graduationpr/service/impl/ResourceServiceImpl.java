package pers.qyj.graduationpr.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pers.qyj.graduationpr.mapper.ResourceFacilityMapper;
import pers.qyj.graduationpr.mapper.ResourceMapper;
import pers.qyj.graduationpr.mapper.SignMapper;
import pers.qyj.graduationpr.mapper.ResourcePolicyMapper;
import pers.qyj.graduationpr.mapper.RoomtypeMapper;
import pers.qyj.graduationpr.pojo.Order;
import pers.qyj.graduationpr.pojo.Resource;
import pers.qyj.graduationpr.pojo.ResourceExample;
import pers.qyj.graduationpr.pojo.ResourceExample.Criteria;
import pers.qyj.graduationpr.pojo.ResourceFacility;
import pers.qyj.graduationpr.pojo.ResourceFacilityExample;
import pers.qyj.graduationpr.pojo.Sign;
import pers.qyj.graduationpr.pojo.SignExample;
import pers.qyj.graduationpr.pojo.ResourcePolicy;
import pers.qyj.graduationpr.pojo.ResourcePolicyExample;
import pers.qyj.graduationpr.pojo.Roomtype;
import pers.qyj.graduationpr.pojo.RoomtypeExample;
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
	RoomtypeMapper roomtypeMapper;
	@Autowired
	SignMapper signMapper;
	@Autowired
	ResourceFacilityMapper resourceFacilityMapper;
	@Autowired
	ResourcePolicyMapper resourcePolicyMapper;

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
		SignExample example = new SignExample();
		example.createCriteria().andOidEqualTo(order.getId());
		List<Sign> ros = signMapper.selectByExample(example);
		for (Sign sign : ros) {
			resources.add(resourceMapper.selectByPrimaryKey(sign.getReid()));
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

	@Override
	public List<Resource> list(Integer adults, Integer children, String roomtype,
			String breakfast, Integer[] facility, Integer[] policy) {

		ResourceExample resourceExample = new ResourceExample();
		Criteria resourceCriteria = resourceExample.createCriteria();

		// roomtype 房型
		if (!roomtype.equals("")) {
			resourceCriteria.andRoomtypeEqualTo(roomtype);
		}

		// breakfast 早餐
		if (!breakfast.equals("")) {
			resourceCriteria.andBreakfastEqualTo(breakfast);
		}

		// adults children 人数
		if (adults != null || children != null ) {
			float cpeople = 0;
			float apeople = 0;
			if(adults != null){
				apeople = adults;
			}
			if(children != null){
				cpeople = children;
			}
			/**
			 * 根据成人和儿童的人数计算床数
			 * 这里把床的容量看做10，成人的大小为6，儿童的大小为4
			 * 比例随便定，只要满足1张床最多只能容纳1+1，且不满足2+0和0+3
			 * 即一张床最多睡一个成人和一个儿童，不能睡两个成人或三个儿童
			 * 在数量较少的情况下此算法有效，当然数量也不可能多得起来
			 * */
			int bedNumMin = (int) Math.ceil((apeople*6+cpeople*4)/10);
			int bedNumMax = (int) (apeople+cpeople);
			
			RoomtypeExample roomtypeExample = new RoomtypeExample();
			pers.qyj.graduationpr.pojo.RoomtypeExample.Criteria roomtypeCriteria = roomtypeExample.createCriteria();
			roomtypeCriteria.andPeopleBetween(bedNumMin, bedNumMax);
			List<Roomtype> roomtypes = roomtypeMapper.selectByExample(roomtypeExample);
			
			List<String> renames = new ArrayList<String>();
			for (Roomtype rroomtype : roomtypes) {
				renames.add(rroomtype.getName());
			}
			if(renames.size()>0){
				resourceCriteria.andRoomtypeIn(renames);
			}
		}
		

		// facility 设施
		/**
		 * 获取设施与资源的映射表，得到映射数据
		 * andFidIn()可以获得的数据为
		 * 符合facility中任意一条即可
		 * 而我想要的数据是必须同时符合facility中所有条件的数据
		 * 通过观察可以得知，andFidIn()返回的数据统计每条的reid频率
		 * 频率=facility条数 的即为需要的数据
		 **/
		if (facility != null) {
			
			ResourceFacilityExample resourceFacilityExample = new ResourceFacilityExample();
			pers.qyj.graduationpr.pojo.ResourceFacilityExample.Criteria resourceFacilityCriteria = resourceFacilityExample
					.createCriteria();
			
			resourceFacilityCriteria.andFidIn(Arrays.asList(facility));

			List<ResourceFacility> resourceFacilities = resourceFacilityMapper.selectByExample(resourceFacilityExample);
			
			List<Integer> reids = new ArrayList<Integer>();
			for (ResourceFacility resourceFacility : resourceFacilities) {
				reids.add(resourceFacility.getReid());
			}
			
			List<Integer> reid = sort(reids,facility.length);

			if(reid.size()>0){
				resourceCriteria.andIdIn(reid);
			}
		}
		

		// policy 退订政策
		/**
		 * (原理同上)
		 **/
		if (policy != null) {

			ResourcePolicyExample resourcePolicyExample = new ResourcePolicyExample();
			pers.qyj.graduationpr.pojo.ResourcePolicyExample.Criteria resourcePolicyCriteria = resourcePolicyExample
					.createCriteria();

			resourcePolicyCriteria.andPoidIn(Arrays.asList(policy));

			List<ResourcePolicy> resourcePolicies = resourcePolicyMapper.selectByExample(resourcePolicyExample);

			List<Integer> reids = new ArrayList<Integer>();
			for (ResourcePolicy resourcePolicy : resourcePolicies) {
				reids.add(resourcePolicy.getReid());
			}
			
			List<Integer> reid = sort(reids,policy.length);
			
			if(reid.size()>0){
				resourceCriteria.andIdIn(reid);
			}
		}
		
		
		List<Resource> resources = resourceMapper.selectByExample(resourceExample);
		return resources;
	}
	
	private List<Integer> sort(List<Integer> reids,int num) {
		HashMap<Integer, Integer> reidhash = new HashMap<Integer, Integer>();
		int count = 0;
		for (int i = 0; i < reids.size(); i++) {
			int c = reids.get(i);
			if (!reidhash.containsKey(c)) {
				for (int j = 0; j < reids.size(); j++) {
					if (reids.get(j) == c) {
						count++;
					}
					if (j == reids.size() - 1) {
						reidhash.put(c, count);
						count = 0;
					}
				}
			}
		}

		List<Integer> reid = new ArrayList<Integer>();
		Iterator<Integer> iter1 = reidhash.keySet().iterator();
		while (iter1.hasNext()) {
			Integer key = iter1.next();
			Integer value = reidhash.get(key);
			if (value == num) {
				reid.add(key);
			}
		}
		return reid;
		
	}
}
