package pers.qyj.graduationpr.service.impl;

import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.stereotype.Service;

import pers.qyj.graduationpr.mapper.ResourceMapper;
import pers.qyj.graduationpr.mapper.SignMapper;
import pers.qyj.graduationpr.pojo.Order;
import pers.qyj.graduationpr.pojo.Resource;
import pers.qyj.graduationpr.pojo.Sign;
import pers.qyj.graduationpr.pojo.SignExample;
import pers.qyj.graduationpr.pojo.SignExample.Criteria;
import pers.qyj.graduationpr.service.OrderService;
import pers.qyj.graduationpr.service.SignService;
@Service
public class SignServiceImpl implements SignService {
	@Autowired
	SignMapper signMapper;
	@Autowired
	ResourceMapper resourceMapper;
	@Autowired
	OrderService orderService;
	
	@Override
	public List<Sign> listFree(Date arrivalDate, Date depatureDate) {
		SignExample example = new SignExample();
		example.or().andDepatureDateLessThanOrEqualTo(arrivalDate);
		example.or().andArrivalDateGreaterThanOrEqualTo(depatureDate);
		List<Sign> Signs = signMapper.selectByExample(example);
		return Signs;
	}

	@Override
	public List<Sign> listOccupy(Date arrivalDate, Date depatureDate) {
		SignExample example = new SignExample();
		Criteria criteria = example.createCriteria();
		criteria.andDepatureDateGreaterThan(arrivalDate);
		criteria.andArrivalDateLessThan(depatureDate);
		List<Sign> Signs = signMapper.selectByExample(example);
		return Signs;
	}

	@Override
	public Map<Integer, Integer> listRemain(List<Resource> resources, Date arrivalDate, Date depatureDate) {
		SignExample example = new SignExample();
		Criteria criteria = example.createCriteria();
		criteria.andDepatureDateGreaterThan(arrivalDate);
		criteria.andArrivalDateLessThan(depatureDate);
		
		List<Sign> Signs = signMapper.selectByExample(example);
		
		Map<Integer, Integer> resource_remain = new HashMap<>();
		
		if(Signs.isEmpty()){
			for (Resource resource : resources) {
				int tatal = resource.getTotal();
				resource_remain.put(resource.getId(), tatal);
			}
		}else{
			for (Resource resource : resources) {
				int tatal = resource.getTotal();
				for(Sign Sign : Signs){
					if(resource.getId()==Sign.getReid()){
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
		
		SignExample example = new SignExample();
		Criteria criteria = example.createCriteria();
		criteria.andDepatureDateGreaterThan(arrivalDate);
		criteria.andArrivalDateLessThan(depatureDate);
		criteria.andReidEqualTo(Reid);
		List<Sign> Signs = signMapper.selectByExample(example);
		
		int remain = total - Signs.size();
		return remain;
	}

	@Override
	public List<Sign> list(Order order) {
		SignExample example = new SignExample();
		example.createCriteria().andOidEqualTo(order.getId());
		return signMapper.selectByExample(example);
	}

	@Override
	public void add(Sign sign) {
		signMapper.insert(sign);
	}

	@Override
	public void deleteByOid(Integer orderId) {
		SignExample example = new SignExample();
		example.createCriteria().andOidEqualTo(orderId);
		List<Sign> signs = signMapper.selectByExample(example);
		for(Sign sign : signs){
			signMapper.deleteByPrimaryKey(sign.getId());
		}
	}

	@Override
	public List<Sign> list() {
		SignExample example = new SignExample();
		example.setOrderByClause("id");
		return signMapper.selectByExample(example);
	}

	@Override
	public List<Sign> getBySign(String search) {
		SignExample example = new SignExample();
		example.createCriteria().andSignEqualTo(search);
		return signMapper.selectByExample(example);
	}

	@Override
	public List<Sign> getByOrderSign(String search) {
		int oid = orderService.getBySign(search).getId();
		SignExample example = new SignExample();
		example.createCriteria().andOidEqualTo(oid);
		
		
		return signMapper.selectByExample(example);
	}

	@Override
	public void delete(int id) {
		signMapper.deleteByPrimaryKey(id);
	}

	@Override
	public Sign getById(int id) {
		
		return signMapper.selectByPrimaryKey(id);
	}

}
