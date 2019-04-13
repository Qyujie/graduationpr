package pers.qyj.graduationpr.service;

import java.util.List;
import java.util.Set;

import pers.qyj.graduationpr.pojo.Bedtype;
import pers.qyj.graduationpr.pojo.Resource;

public interface BedtypeService {
	public Set<String> listBedtypeNames(Long resourceId);

	public List<Bedtype> listBedtypes(Long resourceId);

	public List<Bedtype> listBedtypes(Resource resource);

	public List<Bedtype> list();

	public void add(Bedtype bedtype);

	public void delete(Long id);

	public Bedtype get(Long id);

	public void update(Bedtype bedtype);
}
