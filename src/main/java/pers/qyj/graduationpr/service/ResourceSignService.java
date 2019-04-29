package pers.qyj.graduationpr.service;

import java.sql.Date;
import java.util.List;
import java.util.Map;

import pers.qyj.graduationpr.pojo.Resource;
import pers.qyj.graduationpr.pojo.ResourceSign;

public interface ResourceSignService {
	public List<ResourceSign> listFree(Date arrivalDate, Date depatureDate);

	public List<ResourceSign> listOccupy(Date arrivalDate, Date depatureDate);
	
	public Map<Integer, Integer> listRemain(List<Resource> resources,Date arrivalDate, Date depatureDate);
	
	public Integer getRemainByReid(Integer Reid,Date arrivalDate, Date depatureDate);
	
}
