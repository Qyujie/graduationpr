package pers.qyj.graduationpr.service;

import java.util.List;

import pers.qyj.graduationpr.pojo.Log;

public interface LogService {
	List<Log> list();

	void add(Log lg);
}
