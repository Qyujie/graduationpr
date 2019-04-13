package pers.qyj.graduationpr.service;

import java.util.List;
import java.util.Set;

import pers.qyj.graduationpr.pojo.Facility;
import pers.qyj.graduationpr.pojo.Resource;

public interface FacilityService {
	public Set<String> listFacilityNames(Long resourceId);

	public List<Facility> listFacilities(Long resourceId);

	public List<Facility> listFacilities(Resource resource);

	public List<Facility> list();

	public void add(Facility facility);

	public void delete(int id);

	public Facility get(int id);

	public void update(Facility facility);
}
