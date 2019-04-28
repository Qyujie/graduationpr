package pers.qyj.graduationpr.service.impl;

import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import pers.qyj.graduationpr.mapper.ResourceSignMapper;
import pers.qyj.graduationpr.pojo.ResourceSign;
import pers.qyj.graduationpr.pojo.ResourceSignExample;
import pers.qyj.graduationpr.pojo.ResourceSignExample.Criteria;
import pers.qyj.graduationpr.service.ResourceSignService;

public class ResourceSignServiceImpl implements ResourceSignService {
	@Autowired
	ResourceSignMapper resourceSignMapper;
	
	@Override
	public List<ResourceSign> listFree(Date arrivalDate, Date depatureDate) {
		ResourceSignExample example = new ResourceSignExample();
		example.or().andDepatureDateLessThanOrEqualTo(arrivalDate);
		example.or().andArrivalDateGreaterThanOrEqualTo(depatureDate);
		List<ResourceSign> resourceSigns = resourceSignMapper.selectByExample(example);
		return resourceSigns;
	}

	@Override
	public List<ResourceSign> listOccupy(Date arrivalDate, Date depatureDate) {
		ResourceSignExample example = new ResourceSignExample();
		Criteria criteria = example.createCriteria();
		criteria.andDepatureDateGreaterThanOrEqualTo(depatureDate);
		criteria.andArrivalDateLessThanOrEqualTo(arrivalDate);
		List<ResourceSign> resourceSigns = resourceSignMapper.selectByExample(example);
		return resourceSigns;
	}

}
