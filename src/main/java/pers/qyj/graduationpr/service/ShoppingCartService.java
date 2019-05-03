package pers.qyj.graduationpr.service;

import java.sql.Date;
import java.util.List;

import pers.qyj.graduationpr.pojo.ShoppingCart;

public interface ShoppingCartService {

	public List<ShoppingCart> list();
	
	public List<ShoppingCart> list(Long uid);
	
	public ShoppingCart getById(Integer id);
	
	public List<ShoppingCart> list(Long uid,boolean checked);

	public List<Object> add(Long uid,Integer rid,Date arrivalDate,Date depatureDate);

	public void delete(Integer rid);

	public List<Object> updateNumber(Integer id,Integer num);
	
	public void updateChecked(Long uid,Integer[] id);

	public void updateDate(Integer id, Date arrivalDate, Date depatureDate);

	public Integer getRidByid(Integer id);

	public void delete(Long uid, Integer reid, Date arrivalDate, Date depatureDate);

}
