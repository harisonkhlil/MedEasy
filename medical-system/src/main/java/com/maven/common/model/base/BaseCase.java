package com.maven.common.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings("serial")
public abstract class BaseCase<M extends BaseCase<M>> extends Model<M> implements IBean {

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
	 * 病人id
	 */
	public void setPatientId(java.lang.Integer patientId) {
		set("patient_id", patientId);
	}
	
	/**
	 * 病人id
	 */
	public java.lang.Integer getPatientId() {
		return getInt("patient_id");
	}

	/**
	 * 是否自己（1是0否）
	 */
	public void setIsSelf(java.lang.Boolean isSelf) {
		set("is_self", isSelf);
	}
	
	/**
	 * 是否自己（1是0否）
	 */
	public java.lang.Boolean getIsSelf() {
		return get("is_self");
	}

	/**
	 * 实际看病者的id
	 */
	public void setRealPatientId(java.lang.Integer realPatientId) {
		set("real_patient_id", realPatientId);
	}
	
	/**
	 * 实际看病者的id
	 */
	public java.lang.Integer getRealPatientId() {
		return getInt("real_patient_id");
	}

	/**
	 * 实际看病者的姓名
	 */
	public void setRealPatientName(java.lang.String realPatientName) {
		set("real_patient_name", realPatientName);
	}
	
	/**
	 * 实际看病者的姓名
	 */
	public java.lang.String getRealPatientName() {
		return getStr("real_patient_name");
	}

	/**
	 * 就诊科室
	 */
	public void setDepartment(java.lang.Integer department) {
		set("department", department);
	}
	
	/**
	 * 就诊科室
	 */
	public java.lang.Integer getDepartment() {
		return getInt("department");
	}

	/**
	 * 就诊医生id
	 */
	public void setDoctorId(java.lang.Integer doctorId) {
		set("doctor_id", doctorId);
	}
	
	/**
	 * 就诊医生id
	 */
	public java.lang.Integer getDoctorId() {
		return getInt("doctor_id");
	}

	/**
	 * 就诊医生姓名
	 */
	public void setDoctorName(java.lang.String doctorName) {
		set("doctor_name", doctorName);
	}
	
	/**
	 * 就诊医生姓名
	 */
	public java.lang.String getDoctorName() {
		return getStr("doctor_name");
	}

	/**
	 * 就诊时间
	 */
	public void setDate(java.util.Date date) {
		set("date", date);
	}
	
	/**
	 * 就诊时间
	 */
	public java.util.Date getDate() {
		return get("date");
	}

	/**
	 * 病例内容
	 */
	public void setContent(java.lang.String content) {
		set("content", content);
	}
	
	/**
	 * 病例内容
	 */
	public java.lang.String getContent() {
		return getStr("content");
	}

}
