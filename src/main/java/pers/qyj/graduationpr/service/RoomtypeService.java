package pers.qyj.graduationpr.service;

import java.util.List;

import pers.qyj.graduationpr.pojo.Roomtype;

public interface RoomtypeService { 
	
	public List<Roomtype> list();

	public void add(Roomtype roomtype);

	public void delete(String name);

	public Roomtype get(String name);

	public void update(Roomtype roomtype);
}
