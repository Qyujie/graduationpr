package pers.qyj.graduationpr.mapper;

import pers.qyj.graduationpr.pojo.UserRole;
import pers.qyj.graduationpr.pojo.UserRoleExample;
import java.util.List;

public interface UserRoleMapper {
	int deleteByPrimaryKey(Long id);

	int insert(UserRole record);

	int insertSelective(UserRole record);

	List<UserRole> selectByExample(UserRoleExample example);

	UserRole selectByPrimaryKey(Long id);

	int updateByPrimaryKeySelective(UserRole record);

	int updateByPrimaryKey(UserRole record);
}