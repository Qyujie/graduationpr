package pers.qyj.graduationpr.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pers.qyj.graduationpr.mapper.ResourcePolicyMapper;
import pers.qyj.graduationpr.pojo.Resource;
import pers.qyj.graduationpr.pojo.ResourcePolicy;
import pers.qyj.graduationpr.pojo.ResourcePolicyExample;
import pers.qyj.graduationpr.service.ResourcePolicyService;

@Service
public class ResourcePolicyServiceImpl implements ResourcePolicyService {
	@Autowired
	ResourcePolicyMapper resourcePolicyMapper;
	
	@Override
	public void setPolicies(Resource resource, int[] policyIds) {
		// 删除当前资源所有的设施
		ResourcePolicyExample example = new ResourcePolicyExample();
		example.createCriteria().andReidEqualTo(resource.getId());
		List<ResourcePolicy> rps = resourcePolicyMapper.selectByExample(example);
		for (ResourcePolicy resourcePolicy : rps) {
			resourcePolicyMapper.deleteByPrimaryKey(resourcePolicy.getId());
		}

		// 设置新的资源设施关系
		if (null != policyIds) {
			for (int poid : policyIds) {
				ResourcePolicy resourcePolicy = new ResourcePolicy();
				resourcePolicy.setPoid(poid);
				resourcePolicy.setReid(resource.getId());
				resourcePolicyMapper.insert(resourcePolicy);
			}
		}
	}

	@Override
	public void deleteByResource(int resourceId) {
		ResourcePolicyExample example = new ResourcePolicyExample();
		example.createCriteria().andReidEqualTo(resourceId);
		List<ResourcePolicy> rps = resourcePolicyMapper.selectByExample(example);
		for (ResourcePolicy resourcePolicy : rps) {
			resourcePolicyMapper.deleteByPrimaryKey(resourcePolicy.getId());
		}
	}

	@Override
	public void deleteByPolicy(int policyId) {
		ResourcePolicyExample example = new ResourcePolicyExample();
		example.createCriteria().andPoidEqualTo(policyId);
		List<ResourcePolicy> rps = resourcePolicyMapper.selectByExample(example);
		for (ResourcePolicy resourcePolicy : rps)
			resourcePolicyMapper.deleteByPrimaryKey(resourcePolicy.getId());
	}

}
