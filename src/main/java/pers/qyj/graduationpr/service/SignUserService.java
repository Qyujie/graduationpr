package pers.qyj.graduationpr.service;

import java.util.List;

import pers.qyj.graduationpr.pojo.Order;
import pers.qyj.graduationpr.pojo.Resource;
import pers.qyj.graduationpr.pojo.Sign;
import pers.qyj.graduationpr.pojo.SignUser;

public interface SignUserService {

	List<SignUser> list();

	void add(SignUser signUser);

	void deleteBySign(Sign sign);

	List<SignUser> getBySign(String sign);
}
