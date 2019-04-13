package pers.qyj.graduationpr.service.impl;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pers.qyj.graduationpr.mapper.BedtypeMapper;
import pers.qyj.graduationpr.pojo.Bedtype;
import pers.qyj.graduationpr.pojo.BedtypeExample;
import pers.qyj.graduationpr.pojo.Resource;
import pers.qyj.graduationpr.pojo.RoleExample;
import pers.qyj.graduationpr.service.BedtypeService;
@Service
public class BedtypeServiceImpl implements BedtypeService {
	@Autowired
	BedtypeMapper bedtypeMapper;
	@Override
	public Set<String> listBedtypeNames(Long resourceId) {

		return null;
	}

	@Override
	public List<Bedtype> listBedtypes(Long resourceId) {

		return null;
	}

	@Override
	public List<Bedtype> listBedtypes(Resource resource) {

		return null;
	}

	@Override
	public List<Bedtype> list() {
		BedtypeExample example = new BedtypeExample();
		example.setOrderByClause("id");
		return bedtypeMapper.selectByExample(example);
	}

	@Override
	public void add(Bedtype bedtype) {
	}

	@Override
	public void delete(Long id) {
	}

	@Override
	public Bedtype get(Long id) {

		return null;
	}

	@Override
	public void update(Bedtype bedtype) {
	}

}
