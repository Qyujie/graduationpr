package pers.qyj.graduationpr.service;

import java.sql.Date;
import java.util.List;

import pers.qyj.graduationpr.pojo.ResourceSign;

public interface ResourceSignService {
	public List<ResourceSign> listFree(Date arrivalDate, Date depatureDate);

	public List<ResourceSign> listOccupy(java.sql.Date arrivalDate, java.sql.Date depatureDate);
}
