package pers.qyj.graduationpr.service.impl;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pers.qyj.graduationpr.mapper.BreakfastMapper;
import pers.qyj.graduationpr.pojo.BedtypeExample;
import pers.qyj.graduationpr.pojo.Breakfast;
import pers.qyj.graduationpr.pojo.BreakfastExample;
import pers.qyj.graduationpr.pojo.Resource;
import pers.qyj.graduationpr.service.BreakfastService;
@Service
public class BreakfastServiceImpl implements BreakfastService {
	@Autowired
	BreakfastMapper breakfastMapper;

	@Override
	public Set<String> listBreakfastNames(Long resourceId) {

		return null;
	}

	@Override
	public List<Breakfast> listBreakfasts(Long resourceId) {

		return null;
	}

	@Override
	public List<Breakfast> listBreakfasts(Resource resource) {

		return null;
	}

	@Override
	public List<Breakfast> list() {
		BreakfastExample example = new BreakfastExample();
		example.setOrderByClause("id");
		return breakfastMapper.selectByExample(example);
	}

	@Override
	public void add(Breakfast breakfast) {
	}

	@Override
	public void delete(Long id) {
	}

	@Override
	public Breakfast get(Long id) {

		return null;
	}

	@Override
	public void update(Breakfast breakfast) {
	}

}
