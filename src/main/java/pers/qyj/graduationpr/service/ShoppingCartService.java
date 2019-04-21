package pers.qyj.graduationpr.service;

import java.util.List;

import pers.qyj.graduationpr.pojo.ShoppingCart;

public interface ShoppingCartService {

	public List<ShoppingCart> list();
	
	public List<ShoppingCart> list(Integer id);

	public int[] add(Long uid,Integer rid);

	public void delete(Integer id);

	public void update(ShoppingCart shoppingCart);
}
