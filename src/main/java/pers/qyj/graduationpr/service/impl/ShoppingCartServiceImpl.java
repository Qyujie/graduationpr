package pers.qyj.graduationpr.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pers.qyj.graduationpr.mapper.ResourceMapper;
import pers.qyj.graduationpr.mapper.RoomtypeMapper;
import pers.qyj.graduationpr.mapper.ShoppingCartMapper;
import pers.qyj.graduationpr.pojo.Resource;
import pers.qyj.graduationpr.pojo.ResourceExample;
import pers.qyj.graduationpr.pojo.Roomtype;
import pers.qyj.graduationpr.pojo.RoomtypeExample;
import pers.qyj.graduationpr.pojo.ShoppingCart;
import pers.qyj.graduationpr.pojo.ShoppingCartExample;
import pers.qyj.graduationpr.service.ShoppingCartService;
@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {
	@Autowired
	ShoppingCartMapper shoppingCartMapper;
	@Autowired
	ResourceMapper resourceMapper;
	@Autowired
	RoomtypeMapper roomtypeMapper;
	
	@Override
	public List<ShoppingCart> list() {
		
		return null;
	}

	@Override
	public List<ShoppingCart> list(Long uid) {
		ShoppingCartExample example = new ShoppingCartExample();
		example.createCriteria().andUidEqualTo(uid);
		return shoppingCartMapper.selectByExample(example);
	}

	@Override
	public List<ShoppingCart> list(Long uid, boolean checked) {
		ShoppingCartExample example = new ShoppingCartExample();
		example.createCriteria().andUidEqualTo(uid).andCheckedEqualTo(checked);
		return shoppingCartMapper.selectByExample(example);
	}

	@Override
	public List<Object> add(Long uid, Integer rid) {
		List<Object> message = new ArrayList<>();
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
			
			message.add(0);
			message.add(rid);
		}else{
			shoppingCart.setUid(uid);
			shoppingCart.setRid(rid);
			shoppingCart.setNumber(1);
			shoppingCart.setChecked(false);
			shoppingCartMapper.insert(shoppingCart);
			
			
			Resource resource = new Resource();
			resource = resourceMapper.selectByPrimaryKey(rid);
			
			RoomtypeExample roomtypeExample = new RoomtypeExample();
			roomtypeExample.createCriteria().andNameEqualTo(resource.getRoomtype());
			
			Roomtype roomtype = new Roomtype();
			roomtype = roomtypeMapper.selectByExample(roomtypeExample).get(0);
			
			message.add(1);
			message.add(rid);
			message.add(roomtype);
		}

		return message;
	}

	@Override
	public void delete(Long uid,Integer rid) {
		ShoppingCartExample example = new ShoppingCartExample();
		example.createCriteria().andUidEqualTo(uid).andRidEqualTo(rid);
		ShoppingCart shoppingCart = shoppingCartMapper.selectByExample(example).get(0);
		shoppingCartMapper.deleteByPrimaryKey(shoppingCart.getId());
	}

	@Override
	public void update(Long uid,Integer rid,Integer num) {
		ShoppingCartExample example = new ShoppingCartExample();
		example.createCriteria().andUidEqualTo(uid).andRidEqualTo(rid);
		ShoppingCart shoppingCart = shoppingCartMapper.selectByExample(example).get(0);
		shoppingCart.setNumber(num);
		shoppingCartMapper.updateByPrimaryKey(shoppingCart);
	}

	@Override
	public void updateChecked(Long uid, Integer[] rid) {
		ShoppingCartExample example = new ShoppingCartExample();
		example.createCriteria().andUidEqualTo(uid);
		
		List<ShoppingCart> shoppingCarts = shoppingCartMapper.selectByExample(example);
		
		for(ShoppingCart shoppingCart:shoppingCarts){
			boolean have = false;
			for(int i=0;i<rid.length;i++){
				if(rid[i]==shoppingCart.getRid()){
					shoppingCart.setChecked(true);
					have = true;
					break;
				}
			}
			if(!have){
				shoppingCart.setChecked(false);
			}
			shoppingCartMapper.updateByPrimaryKey(shoppingCart);
		}
	}

}
