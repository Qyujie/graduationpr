package pers.qyj.graduationpr.service.impl;

import java.sql.Date;
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
		ShoppingCartExample example = new ShoppingCartExample();
		example.setOrderByClause("id");
		return shoppingCartMapper.selectByExample(example);
	}

	@Override
	public ShoppingCart getById(Integer id) {
		return shoppingCartMapper.selectByPrimaryKey(id);
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
	public List<Object> add(Long uid, Integer rid ,Date arrivalDate,Date depatureDate) {
		List<Object> message = new ArrayList<>();
		ShoppingCartExample example = new ShoppingCartExample();
		example.createCriteria().andUidEqualTo(uid)
								.andRidEqualTo(rid)
								.andArrivalDateEqualTo(arrivalDate)
								.andDepatureDateEqualTo(depatureDate);
		List<ShoppingCart> shoppingCarts = shoppingCartMapper.selectByExample(example);
		
		ShoppingCart shoppingCart = new ShoppingCart();
		if(shoppingCarts.size()>0){
			shoppingCart = shoppingCarts.get(0);
			int total = shoppingCart.getNumber();
			total++;
			shoppingCart.setNumber(total);
			shoppingCartMapper.updateByPrimaryKey(shoppingCart);
			
			message.add(0);//表示没有增加数据，而只是修改了数据
			message.add(shoppingCart);
		}else{
			shoppingCart.setUid(uid);
			shoppingCart.setRid(rid);
			shoppingCart.setArrivalDate(arrivalDate);
			shoppingCart.setDepatureDate(depatureDate);
			shoppingCart.setNumber(1);
			shoppingCart.setChecked(false);
			shoppingCartMapper.insert(shoppingCart);
			
			
			Resource resource = new Resource();
			resource = resourceMapper.selectByPrimaryKey(rid);
			
			RoomtypeExample roomtypeExample = new RoomtypeExample();
			roomtypeExample.createCriteria().andNameEqualTo(resource.getRoomtype());
			
			Roomtype roomtype = new Roomtype();
			roomtype = roomtypeMapper.selectByExample(roomtypeExample).get(0);
			
			message.add(1);//表示增加了一条数据
			message.add(shoppingCart);
			message.add(roomtype);
		}

		return message;
	}

	@Override
	public void delete(Integer id) {
		shoppingCartMapper.deleteByPrimaryKey(id);
	}

	@Override
	public void updateNumber(Integer id,Integer num) {
		ShoppingCart shoppingCart = shoppingCartMapper.selectByPrimaryKey(id);
		shoppingCart.setNumber(num);
		shoppingCartMapper.updateByPrimaryKey(shoppingCart);
	}

	@Override
	public void updateChecked(Long uid ,Integer[] id) {
		ShoppingCartExample example = new ShoppingCartExample();
		example.createCriteria().andUidEqualTo(uid);
		
		List<ShoppingCart> shoppingCarts = shoppingCartMapper.selectByExample(example);
		
		for(ShoppingCart shoppingCart:shoppingCarts){
			boolean have = false;
			for(int i=0;i<id.length;i++){
				if(id[i]==shoppingCart.getId()){
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

	@Override
	public void updateDate(Integer id, Date arrivalDate, Date depatureDate) {
		ShoppingCart shoppingCart = shoppingCartMapper.selectByPrimaryKey(id);
		shoppingCart.setArrivalDate(arrivalDate);
		shoppingCart.setDepatureDate(depatureDate);
		shoppingCartMapper.updateByPrimaryKey(shoppingCart);
	}

	@Override
	public Integer getRidByid(Integer id) {
		ShoppingCart shoppingCart = shoppingCartMapper.selectByPrimaryKey(id);
		return shoppingCart.getRid();
	}

}
