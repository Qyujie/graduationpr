package pers.qyj.graduationpr.service;

import pers.qyj.graduationpr.pojo.Role;

public interface RolePermissionService {
	public void setPermissions(Role role, long[] permissionIds);

	public void deleteByRole(long roleId);

	public void deleteByPermission(long permissionId);
}