package pers.qyj.graduationpr.service;

import java.sql.Date;
import java.util.List;
import java.util.Map;

import pers.qyj.graduationpr.pojo.Order;
import pers.qyj.graduationpr.pojo.Resource;
import pers.qyj.graduationpr.pojo.Sign;

public interface SignService {
	public List<Sign> listFree(Date arrivalDate, Date depatureDate);

	public List<Sign> listOccupy(Date arrivalDate, Date depatureDate);
	
	public Map<Integer, Integer> listRemain(List<Resource> resources,Date arrivalDate, Date depatureDate);
	
	public Integer getRemainByReid(Integer Reid,Date arrivalDate, Date depatureDate);

	public List<Sign> list(Order order);

	public void add(Sign sign);

	public void deleteByOid(Integer orderId);

	public List<Sign> list();

	public List<Sign> getBySign(String searchSign);

	public List<Sign> getByOrderSign(String search);

	public void delete(int id);

	public Sign getById(int id);
	
}
