package pers.qyj.graduationpr.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pers.qyj.graduationpr.mapper.FacilityMapper;
import pers.qyj.graduationpr.mapper.ResourceFacilityMapper;
import pers.qyj.graduationpr.pojo.BedtypeExample;
import pers.qyj.graduationpr.pojo.Facility;
import pers.qyj.graduationpr.pojo.FacilityExample;
import pers.qyj.graduationpr.pojo.Resource;
import pers.qyj.graduationpr.pojo.ResourceFacility;
import pers.qyj.graduationpr.pojo.ResourceFacilityExample;
import pers.qyj.graduationpr.pojo.Role;
import pers.qyj.graduationpr.pojo.UserRole;
import pers.qyj.graduationpr.pojo.UserRoleExample;
import pers.qyj.graduationpr.service.FacilityService;
import pers.qyj.graduationpr.service.ResourceFacilityService;
@Service
public class FacilityServiceImpl implements FacilityService {
	@Autowired
	ResourceFacilityMapper resourceFacilityMapper;
	@Autowired
	FacilityMapper facilityMapper;
	@Autowired
	ResourceFacilityService resourceFacilityService;
	
	@Override
	public Set<String> listFacilityNames(Long resourceId) {
		// TODO 自动生成的方法存根
		return null;
	}

	@Override
	public List<Facility> listFacilities(Long resourceId) {
		// TODO 自动生成的方法存根
		return null;
	}

	@Override
	public List<Facility> listFacilities(Resource resource) {
		List<Facility> facilities = new ArrayList<>();
		ResourceFacilityExample example = new ResourceFacilityExample();
		example.createCriteria().andReidEqualTo(resource.getId());
		List<ResourceFacility> resourceFacilities = resourceFacilityMapper.selectByExample(example);
		for (ResourceFacility resourceFacility : resourceFacilities) {
			Facility facility = facilityMapper.selectByPrimaryKey(resourceFacility.getFid());
			facilities.add(facility);
		}
		return facilities;
	}

	@Override
	public List<Facility> list() {
		FacilityExample example = new FacilityExample();
		example.setOrderByClause("id");
		return facilityMapper.selectByExample(example);
	}

	@Override
	public void add(Facility facility) {
		facilityMapper.insert(facility);
	}

	@Override
	public void delete(int id) {
		facilityMapper.deleteByPrimaryKey(id);
		resourceFacilityService.deleteByFacility(id);
	}

	@Override
	public Facility get(int id) {
		return facilityMapper.selectByPrimaryKey(id);
	}

	@Override
	public void update(Facility facility) {
		facilityMapper.updateByPrimaryKeySelective(facility);
	}

}
