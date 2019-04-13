package pers.qyj.graduationpr.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pers.qyj.graduationpr.mapper.ResourceFacilityMapper;
import pers.qyj.graduationpr.pojo.Resource;
import pers.qyj.graduationpr.pojo.ResourceFacility;
import pers.qyj.graduationpr.pojo.ResourceFacilityExample;
import pers.qyj.graduationpr.service.ResourceFacilityService;

@Service
public class ResourceFacilityServiceImpl implements ResourceFacilityService {
	@Autowired
	ResourceFacilityMapper resourceFacilityMapper;
	
	@Override
	public void setFacilities(Resource resource, int[] facilityIds) {
		// 删除当前资源所有的设施
		ResourceFacilityExample example = new ResourceFacilityExample();
		example.createCriteria().andReidEqualTo(resource.getId());
		List<ResourceFacility> rfs = resourceFacilityMapper.selectByExample(example);
		for (ResourceFacility resourceFacility : rfs) {
			resourceFacilityMapper.deleteByPrimaryKey(resourceFacility.getId());
		}

		// 设置新的资源设施关系
		if (null != facilityIds) {
			for (int fid : facilityIds) {
				ResourceFacility resourceFacility = new ResourceFacility();
				resourceFacility.setFid( fid);
				resourceFacility.setReid(resource.getId());
				resourceFacilityMapper.insert(resourceFacility);
			}
		}
	}

	@Override
	public void deleteByResource(int resourceId) {
		ResourceFacilityExample example = new ResourceFacilityExample();
		example.createCriteria().andReidEqualTo(resourceId);
		List<ResourceFacility> rfs = resourceFacilityMapper.selectByExample(example);
		for (ResourceFacility resourceFacility : rfs) {
			resourceFacilityMapper.deleteByPrimaryKey(resourceFacility.getId());
		}
	}

	@Override
	public void deleteByFacility(int facilityId) {
		ResourceFacilityExample example = new ResourceFacilityExample();
		example.createCriteria().andFidEqualTo(facilityId);
		List<ResourceFacility> rfs = resourceFacilityMapper.selectByExample(example);
		for (ResourceFacility resourceFacility : rfs)
			resourceFacilityMapper.deleteByPrimaryKey(resourceFacility.getId());
	}
}
