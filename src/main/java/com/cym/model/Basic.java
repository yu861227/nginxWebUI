package com.cym.model;

import com.cym.sqlhelper.bean.BaseModel;
import com.cym.sqlhelper.config.Table;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * 基础参数
 */
@Table
public class Basic extends BaseModel {
	/**
	 * 参数名
	 */
	String name;
	/**
	 * 参数值
	 */
	String value;
	@JsonIgnore
	Long seq;

	public Basic() {

	}

	public Basic(String name, String value, Long seq) {
		this.name = name;
		this.value = value;
		this.seq = seq;
	}

	public Long getSeq() {
		return seq;
	}

	public void setSeq(Long seq) {
		this.seq = seq;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
