package pers.qyj.graduationpr.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pers.qyj.graduationpr.mapper.ShoppingCartMapper;
import pers.qyj.graduationpr.pojo.ShoppingCart;
import pers.qyj.graduationpr.pojo.ShoppingCartExample;
import pers.qyj.graduationpr.service.ShoppingCartService;
@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {
	@Autowired
	ShoppingCartMapper shoppingCartMapper;

	@Override
	public List<ShoppingCart> list() {
		
		return null;
	}

	@Override
	public List<ShoppingCart> list(Integer id) {
		
		return null;
	}

	@Override
	public int[] add(Long uid, Integer rid) {
		int[] message = new int[2];
		message[0] = rid;
		ShoppingCartExample example = new ShoppingCartExample();
		example.createCriteria().andUidEqualTo(uid).andRidEqualTo(rid);
		List<ShoppingCart> shoppingCarts = shoppingCartMapper.selectByExample(example);
		
		ShoppingCart shoppingCart = new ShoppingCart();
		if(shoppingCarts.size()>0){
			shoppingCart = shoppingCarts.get(0);
			int total = shoppingCart.getNumber();
			total++;
			shoppingCart.setNumber(total);
			shoppingCartMapper.updateByPrimaryKey(shoppingCart);
			
			message[1] = 0;
		}else{
			shoppingCart.setUid(uid);
			shoppingCart.setRid(rid);
			shoppingCart.setNumber(1);
			shoppingCartMapper.insert(shoppingCart);
			
			message[1] = 1;
		}

		return message;
	}

	@Override
	public void delete(Integer id) {
	}

	@Override
	public void update(ShoppingCart shoppingCart) {
	}

}
