package pers.qyj.graduationpr.service;

import java.util.List;

import pers.qyj.graduationpr.pojo.Order;
import pers.qyj.graduationpr.pojo.Resource;
import pers.qyj.graduationpr.pojo.SignUser;

public interface SignUserService {

	List<SignUser> list();

	List<SignUser> list(Order order);

	List<SignUser> list(Resource resource);

}
