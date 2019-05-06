package pers.qyj.graduationpr.service;

import pers.qyj.graduationpr.pojo.Userinformation;

public interface UserinformationService {
	
    public void saveUserInformation(Userinformation userInformation);//添加用户信息
    
    public void updateUserInformation(Userinformation userInformation,String username);
    
    public Userinformation getUserInformation(String username); //查询用户信息

	public void updateHeadPortrait(String filePath, String currentUser);

}
