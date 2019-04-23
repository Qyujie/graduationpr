package pers.qyj.graduationpr.service;

import java.util.List;

import pers.qyj.graduationpr.pojo.ShoppingCart;

public interface ShoppingCartService {

	public List<ShoppingCart> list();
	
	public List<ShoppingCart> list(Long uid);

	public List<Object> add(Long uid,Integer rid);

	public void delete(Long uid,Integer rid);

	public void update(Long uid,Integer rid,Integer num);
	
	public void updateChecked(Long uid,Integer[] rid);
}
