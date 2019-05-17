package pers.qyj.graduationpr.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pers.qyj.graduationpr.mapper.LogMapper;
import pers.qyj.graduationpr.pojo.Log;
import pers.qyj.graduationpr.pojo.LogExample;
import pers.qyj.graduationpr.service.LogService;

@Service
public class LogServiceImpl implements LogService {
	@Autowired
	LogMapper logMapper;
	
	@Override
	public List<Log> list() {
		LogExample example = new LogExample();
		example.setOrderByClause("id desc");
		return logMapper.selectByExample(example);
	}

	@Override
	public void add(Log lg) {
		logMapper.insert(lg);
	}

}
