package pers.qyj.graduationpr.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pers.qyj.graduationpr.pojo.User;
import pers.qyj.graduationpr.pojo.UserExample;
import pers.qyj.graduationpr.service.UserRoleService;
import pers.qyj.graduationpr.service.UserService;

import pers.qyj.graduationpr.mapper.UserMapper;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserMapper userMapper;
	@Autowired
	UserRoleService userRoleService;

	@Override
	public String getPassword(String name) {
		User user = getByName(name);
		if (null == user)
			return null;
		return user.getPassword();
	}

	@Override
	public User getByName(String name) {
		UserExample example = new UserExample();
		example.createCriteria().andNameEqualTo(name);
		List<User> users = userMapper.selectByExample(example);
		if (users.isEmpty())
			return null;
		return users.get(0);
	}

	@Override
	public void add(User u) {
		userMapper.insert(u);
	}

	@Override
	public void delete(Long id) {
		userMapper.deleteByPrimaryKey(id);
		userRoleService.deleteByUser(id);
	}

	@Override
	public void update(User u) {
		userMapper.updateByPrimaryKeySelective(u);
	}

	@Override
	public User get(Long id) {
		return userMapper.selectByPrimaryKey(id);
	}

	@Override
	public List<User> list() {
		UserExample example = new UserExample();
		example.setOrderByClause("id desc");
		return userMapper.selectByExample(example);

	}

	@Override
	public Long getId(String name) {
		UserExample example = new UserExample();
		example.createCriteria().andNameEqualTo(name);
		return userMapper.selectByExample(example).get(0).getId();
	}

}