package pers.qyj.graduationpr.service;

import pers.qyj.graduationpr.pojo.Resource;

public interface ResourcePolicyService {

	public void setPolicies(Resource resource, int[] policies);

	public void deleteByResource(int resourceId);

	public void deleteByPolicy(int policyId);
}
