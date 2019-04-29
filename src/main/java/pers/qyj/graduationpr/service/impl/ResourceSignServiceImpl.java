package pers.qyj.graduationpr.service.impl;

import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.stereotype.Service;

import pers.qyj.graduationpr.mapper.ResourceMapper;
import pers.qyj.graduationpr.mapper.ResourceSignMapper;
import pers.qyj.graduationpr.pojo.Resource;
import pers.qyj.graduationpr.pojo.ResourceSign;
import pers.qyj.graduationpr.pojo.ResourceSignExample;
import pers.qyj.graduationpr.pojo.ResourceSignExample.Criteria;
import pers.qyj.graduationpr.service.ResourceSignService;
@Service
public class ResourceSignServiceImpl implements ResourceSignService {
	@Autowired
	ResourceSignMapper resourceSignMapper;
	@Autowired
	ResourceMapper resourceMapper;
	
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
		criteria.andDepatureDateGreaterThan(arrivalDate);
		criteria.andArrivalDateLessThan(depatureDate);
		List<ResourceSign> resourceSigns = resourceSignMapper.selectByExample(example);
		return resourceSigns;
	}

	@Override
	public Map<Integer, Integer> listRemain(List<Resource> resources, Date arrivalDate, Date depatureDate) {
		ResourceSignExample example = new ResourceSignExample();
		Criteria criteria = example.createCriteria();
		criteria.andDepatureDateGreaterThan(arrivalDate);
		criteria.andArrivalDateLessThan(depatureDate);
		
		List<ResourceSign> resourceSigns = resourceSignMapper.selectByExample(example);
		
		Map<Integer, Integer> resource_remain = new HashMap<>();
		
		if(resourceSigns.isEmpty()){
			for (Resource resource : resources) {
				int tatal = resource.getTotal();
				resource_remain.put(resource.getId(), tatal);
			}
		}else{
			for (Resource resource : resources) {
				int tatal = resource.getTotal();
				for(ResourceSign resourceSign : resourceSigns){
					if(resource.getId()==resourceSign.getReid()){
						tatal--;
					}
				}
				resource_remain.put(resource.getId(), tatal);
			}
		}
		return resource_remain;
	}

	@Override
	public Integer getRemainByReid(Integer Reid, Date arrivalDate, Date depatureDate) {
		int total = resourceMapper.selectByPrimaryKey(Reid).getTotal();
		
		ResourceSignExample example = new ResourceSignExample();
		Criteria criteria = example.createCriteria();
		criteria.andDepatureDateGreaterThan(arrivalDate);
		criteria.andArrivalDateLessThan(depatureDate);
		criteria.andReidEqualTo(Reid);
		List<ResourceSign> resourceSigns = resourceSignMapper.selectByExample(example);
		
		int remain = total - resourceSigns.size();
		return remain;
	}

}
