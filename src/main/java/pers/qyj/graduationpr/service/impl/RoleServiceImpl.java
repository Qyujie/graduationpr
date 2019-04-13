package pers.qyj.graduationpr.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pers.qyj.graduationpr.pojo.Role;
import pers.qyj.graduationpr.pojo.RoleExample;
import pers.qyj.graduationpr.pojo.User;
import pers.qyj.graduationpr.pojo.UserRole;
import pers.qyj.graduationpr.pojo.UserRoleExample;
import pers.qyj.graduationpr.service.RolePermissionService;
import pers.qyj.graduationpr.service.RoleService;
import pers.qyj.graduationpr.service.UserRoleService;
import pers.qyj.graduationpr.service.UserService;

import pers.qyj.graduationpr.mapper.RoleMapper;
import pers.qyj.graduationpr.mapper.UserRoleMapper;

@Service
public class RoleServiceImpl implements RoleService {
	@Autowired
	RoleMapper roleMapper;
	@Autowired
	UserRoleMapper userRoleMapper;
	@Autowired
	UserService userService;
	@Autowired
	RolePermissionService rolePermissionService;
	@Autowired
	UserRoleService userRoleService;
	
	@Override
	public Set<String> listRoleNames(String userName) {
		Set<String> result = new HashSet<>();
		List<Role> roles = listRoles(userName);
		for (Role role : roles) {
			result.add(role.getName());
		}
		return result;
	}

	@Override
	public List<Role> listRoles(String userName) {
		List<Role> roles = new ArrayList<>();
		User user = userService.getByName(userName);
		if (null == user)
			return roles;

		roles = listRoles(user);
		return roles;
	}

	@Override
	public void add(Role u) {
		roleMapper.insert(u);
	}

	@Override
	public void delete(Long id) {
		roleMapper.deleteByPrimaryKey(id);
		rolePermissionService.deleteByRole(id);
		userRoleService.deleteByRole(id);
	}

	@Override
	public void update(Role u) {
		roleMapper.updateByPrimaryKeySelective(u);
	}

	@Override
	public Role get(Long id) {
		return roleMapper.selectByPrimaryKey(id);
	}

	@Override
	public List<Role> list() {
		RoleExample example = new RoleExample();
		example.setOrderByClause("id desc");
		return roleMapper.selectByExample(example);

	}

	@Override
	public List<Role> listRoles(User user) {
		List<Role> roles = new ArrayList<>();

		UserRoleExample example = new UserRoleExample();

		example.createCriteria().andUidEqualTo(user.getId());
		List<UserRole> userRoles = userRoleMapper.selectByExample(example);

		for (UserRole userRole : userRoles) {
			Role role = roleMapper.selectByPrimaryKey(userRole.getRid());
			roles.add(role);
		}
		return roles;
	}

}