package pers.qyj.graduationpr.service;

import java.util.List;

import pers.qyj.graduationpr.pojo.Order;
import pers.qyj.graduationpr.pojo.Resource;
import pers.qyj.graduationpr.pojo.ResourceOrder;

public interface ResourceOrderService {

	List<ResourceOrder> list();

	List<ResourceOrder> list(Order order);

	List<ResourceOrder> list(Resource resource);

}
