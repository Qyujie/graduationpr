package pers.qyj.graduationpr.service;

import pers.qyj.graduationpr.pojo.Resource;

public interface ResourceFacilityService {
	public void setFacilities(Resource resource, int[] facilities);

	public void deleteByResource(int resourceId);

	public void deleteByFacility(int facilityId);
}
