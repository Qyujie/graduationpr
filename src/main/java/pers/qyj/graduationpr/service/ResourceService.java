package pers.qyj.graduationpr.service;

import java.util.List;

import pers.qyj.graduationpr.pojo.Order;
import pers.qyj.graduationpr.pojo.Resource;

public interface ResourceService {
	public String getPassword(String name);

	public Resource getByName(String name);

	public List<Resource> list();
	
	public List<Resource> list(Order order);
	
	public List<Resource> list(String roomName);

	public void add(Resource resource);

	public void delete(Integer id);

	public Resource get(Integer id);

	public void update(Resource resource);

	public List<Resource> list(Integer adults, Integer children, String roomtype,
			String breakfast, Integer[] facility, Integer[] policy,Integer start,Integer size);

	public void deleteByRoomtype(String name);
}
