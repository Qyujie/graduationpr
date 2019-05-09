package pers.qyj.graduationpr.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pers.qyj.graduationpr.mapper.UserinformationMapper;
import pers.qyj.graduationpr.pojo.Userinformation;
import pers.qyj.graduationpr.pojo.UserinformationExample;
import pers.qyj.graduationpr.service.UserinformationService;

@Service
public class UserinformationServiceImpl implements UserinformationService {

	@Autowired
	UserinformationMapper userinformationMapper;
	
	@Override
	public void saveUserInformation(Userinformation userInformation) {
		userinformationMapper.insertSelective(userInformation);
	}

	@Override
	public void updateUserInformation(Userinformation userInformation, String username) {
		UserinformationExample example = new UserinformationExample();
		example.createCriteria().andNameEqualTo(username);
		Long id = userinformationMapper.selectByExample(example).get(0).getId();
		userInformation.setId(id);
		userinformationMapper.updateByPrimaryKeySelective(userInformation);
	}

	@Override
	public Userinformation getUserInformation(String username) {
		UserinformationExample example = new UserinformationExample();
		example.createCriteria().andNameEqualTo(username);
		return userinformationMapper.selectByExample(example).get(0);
	}

	@Override
	public void updateHeadPortrait(String filePath,String currentUser) {
		UserinformationExample example = new UserinformationExample();
		example.createCriteria().andNameEqualTo(currentUser);
		Userinformation userinformationGet = userinformationMapper.selectByExample(example).get(0);
		userinformationGet.setHeadPortrait(filePath);
		userinformationMapper.updateByPrimaryKeySelective(userinformationGet);
	}

}
