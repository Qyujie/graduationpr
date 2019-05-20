package pers.qyj.graduationpr.service.impl;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;

import pers.qyj.graduationpr.mapper.RoomtypeMapper;
import pers.qyj.graduationpr.pojo.Resource;
import pers.qyj.graduationpr.pojo.Roomtype;
import pers.qyj.graduationpr.pojo.RoomtypeExample;
import pers.qyj.graduationpr.service.RoomtypeService;
@Service
public class RoomtypeServiceImpl implements RoomtypeService {
	@Autowired
	RoomtypeMapper roomtypeMapper;

	@Override
	public List<Roomtype> list() {
		RoomtypeExample example = new RoomtypeExample();
		example.setOrderByClause("name");
		return roomtypeMapper.selectByExample(example);
	}

	@Override
	public void add(Roomtype roomtype) {
		roomtypeMapper.insertSelective(roomtype);
	}

	@Override
	public void delete(String name) {
		roomtypeMapper.deleteByPrimaryKey(name);
	}

	@Override
	public Roomtype get(String name) {
		return roomtypeMapper.selectByPrimaryKey(name);
	}

	@Override
	public void update(Roomtype roomtype) {
		roomtypeMapper.updateByPrimaryKeySelective(roomtype);
	}

}
