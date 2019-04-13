package pers.qyj.graduationpr.service;

import java.util.List;
import java.util.Set;

import pers.qyj.graduationpr.pojo.Policy;
import pers.qyj.graduationpr.pojo.Resource;

public interface PolicyService {
	public Set<String> listRoleNames(Long resourceId);

	public List<Policy> listPolicies(Long resourceId);

	public List<Policy> listPolicies(Resource resource);

	public List<Policy> list();

	public void add(Policy policy);

	public void delete(int id);

	public Policy get(int id);

	public void update(Policy policy);
}
