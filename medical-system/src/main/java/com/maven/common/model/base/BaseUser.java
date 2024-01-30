package com.maven.common.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings("serial")
public abstract class BaseUser<M extends BaseUser<M>> extends Model<M> implements IBean {

	/**
	 * 主键
	 */
	public void setId(java.lang.Integer id) {
		set("id", id);
	}
	
	/**
	 * 主键
	 */
	public java.lang.Integer getId() {
		return getInt("id");
	}

	/**
	 * 账号
	 */
	public void setAccount(java.lang.String account) {
		set("account", account);
	}
	
	/**
	 * 账号
	 */
	public java.lang.String getAccount() {
		return getStr("account");
	}

	/**
	 * 密码
	 */
	public void setPassword(java.lang.String password) {
		set("password", password);
	}
	
	/**
	 * 密码
	 */
	public java.lang.String getPassword() {
		return getStr("password");
	}

	/**
	 * 姓名
	 */
	public void setName(java.lang.String name) {
		set("name", name);
	}
	
	/**
	 * 姓名
	 */
	public java.lang.String getName() {
		return getStr("name");
	}

	/**
	 * 性别（1男0女）
	 */
	public void setSex(java.lang.Integer sex) {
		set("sex", sex);
	}
	
	/**
	 * 性别（1男0女）
	 */
	public java.lang.Integer getSex() {
		return getInt("sex");
	}

	/**
	 * 用户类型（0管理员1医生2病人）
	 */
	public void setUserType(java.lang.Integer userType) {
		set("user_type", userType);
	}
	
	/**
	 * 用户类型（0管理员1医生2病人）
	 */
	public java.lang.Integer getUserType() {
		return getInt("user_type");
	}

	/**
	 * 医生或病人的id
	 */
	public void setRelation(java.lang.Integer relation) {
		set("relation", relation);
	}
	
	/**
	 * 医生或病人的id
	 */
	public java.lang.Integer getRelation() {
		return getInt("relation");
	}

}
