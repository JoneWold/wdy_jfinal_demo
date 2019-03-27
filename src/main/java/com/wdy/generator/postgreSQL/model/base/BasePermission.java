package com.wdy.generator.postgreSQL.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings("serial")
public abstract class BasePermission<M extends BasePermission<M>> extends Model<M> implements IBean {

	public void setId(java.lang.Integer id) {
		set("id", id);
	}
	
	public java.lang.Integer getId() {
		return getInt("id");
	}

	public void setPermissionName(java.lang.String permissionName) {
		set("permission_name", permissionName);
	}
	
	public java.lang.String getPermissionName() {
		return getStr("permission_name");
	}

	public void setPermissionMenu(java.lang.String permissionMenu) {
		set("permission_menu", permissionMenu);
	}
	
	public java.lang.String getPermissionMenu() {
		return getStr("permission_menu");
	}

	public void setSort(java.lang.String sort) {
		set("sort", sort);
	}
	
	public java.lang.String getSort() {
		return getStr("sort");
	}

}
