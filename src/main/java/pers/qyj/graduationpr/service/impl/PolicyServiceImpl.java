package pers.qyj.graduationpr.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pers.qyj.graduationpr.mapper.PolicyMapper;
import pers.qyj.graduationpr.mapper.ResourcePolicyMapper;
import pers.qyj.graduationpr.pojo.FacilityExample;
import pers.qyj.graduationpr.pojo.Policy;
import pers.qyj.graduationpr.pojo.PolicyExample;
import pers.qyj.graduationpr.pojo.Resource;
import pers.qyj.graduationpr.pojo.ResourcePolicy;
import pers.qyj.graduationpr.pojo.ResourcePolicyExample;
import pers.qyj.graduationpr.service.PolicyService;
import pers.qyj.graduationpr.service.ResourcePolicyService;
@Service
public class PolicyServiceImpl implements PolicyService {
	@Autowired
	ResourcePolicyMapper resourcePolicyMapper;
	@Autowired
	PolicyMapper policyMapper;
	@Autowired
	ResourcePolicyService resourcePolicyService;
	
	@Override
	public Set<String> listRoleNames(Long resourceId) {
		// TODO 自动生成的方法存根
		return null;
	}

	@Override
	public List<Policy> listPolicies(Long resourceId) {
		// TODO 自动生成的方法存根
		return null;
	}

	@Override
	public List<Policy> listPolicies(Resource resource) {
		List<Policy> policies = new ArrayList<>();

		ResourcePolicyExample example = new ResourcePolicyExample();

		example.createCriteria().andReidEqualTo(resource.getId());
		List<ResourcePolicy> resourcePolicies = resourcePolicyMapper.selectByExample(example);

		for (ResourcePolicy resourcePolicy : resourcePolicies) {
			Policy policy = policyMapper.selectByPrimaryKey(resourcePolicy.getPoid());
			policies.add(policy);
		}
		return policies;
	}

	@Override
	public List<Policy> list() {
		PolicyExample example = new PolicyExample();
		example.setOrderByClause("id");
		return policyMapper.selectByExample(example);
	}

	@Override
	public void add(Policy policy) {
		policyMapper.insert(policy);
	}

	@Override
	public void delete(int id) {
		policyMapper.deleteByPrimaryKey(id);
		resourcePolicyService.deleteByPolicy(id);
	}

	@Override
	public Policy get(int id) {
		return policyMapper.selectByPrimaryKey(id);
	}

	@Override
	public void update(Policy policy) {
		policyMapper.updateByPrimaryKeySelective(policy);
	}

}
