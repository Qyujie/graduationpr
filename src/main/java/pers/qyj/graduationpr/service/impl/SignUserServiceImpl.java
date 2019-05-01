package pers.qyj.graduationpr.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pers.qyj.graduationpr.mapper.SignUserMapper;
import pers.qyj.graduationpr.pojo.Order;
import pers.qyj.graduationpr.pojo.Resource;
import pers.qyj.graduationpr.pojo.SignUser;
import pers.qyj.graduationpr.pojo.SignUserExample;
import pers.qyj.graduationpr.pojo.RoleExample;
import pers.qyj.graduationpr.service.SignUserService;

@Service
public class SignUserServiceImpl implements SignUserService {
	@Autowired
	SignUserMapper signUserMapper;
	
	@Override
	public List<SignUser> list() {
		SignUserExample example = new SignUserExample();
		example.setOrderByClause("id");
		return signUserMapper.selectByExample(example);
	}

	@Override
	public void add(SignUser signUser) {
		signUserMapper.insert(signUser);
	}

}
