package pers.qyj.graduationpr.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pers.qyj.graduationpr.mapper.SystemConfigurationMapper;
import pers.qyj.graduationpr.pojo.SystemConfiguration;
import pers.qyj.graduationpr.service.SystemConfigurationService;
@Service
public class SystemConfigurationServiceImpl implements SystemConfigurationService {
	@Autowired
	SystemConfigurationMapper systemConfigurationMapper;
	
	@Override
	public String getArrivalTime() {
		SystemConfiguration systemConfiguration  = systemConfigurationMapper.selectByPrimaryKey(1);
		return systemConfiguration.getArrivalTime();
	}

	@Override
	public String getDepatureTime() {
		SystemConfiguration systemConfiguration  = systemConfigurationMapper.selectByPrimaryKey(1);
		return systemConfiguration.getDepatureTime();
	}

}
