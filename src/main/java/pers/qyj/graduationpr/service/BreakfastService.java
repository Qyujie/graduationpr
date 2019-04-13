package pers.qyj.graduationpr.service;

import java.util.List;
import java.util.Set;

import pers.qyj.graduationpr.pojo.Breakfast;
import pers.qyj.graduationpr.pojo.Resource;

public interface BreakfastService {
	public Set<String> listBreakfastNames(Long resourceId);

	public List<Breakfast> listBreakfasts(Long resourceId);

	public List<Breakfast> listBreakfasts(Resource resource);

	public List<Breakfast> list();

	public void add(Breakfast breakfast);

	public void delete(Long id);

	public Breakfast get(Long id);

	public void update(Breakfast breakfast);
}
