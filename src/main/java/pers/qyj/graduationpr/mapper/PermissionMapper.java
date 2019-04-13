package pers.qyj.graduationpr.mapper;

import pers.qyj.graduationpr.pojo.Permission;
import pers.qyj.graduationpr.pojo.PermissionExample;
import java.util.List;

public interface PermissionMapper {
	int deleteByPrimaryKey(Long id);

	int insert(Permission record);

	int insertSelective(Permission record);

	List<Permission> selectByExample(PermissionExample example);

	Permission selectByPrimaryKey(Long id);

	int updateByPrimaryKeySelective(Permission record);

	int updateByPrimaryKey(Permission record);
}