package com.moxiaowei.blog.util;

public class RestMessage<T> {

	private Integer code;
	private T json;
	
	public RestMessage() {
		this.code = 400;
		this.json = null;
	}

	public RestMessage(Integer code, T json) {
		super();
		this.code = code;
		this.json = json;
	}
	
	public void set200(T json){
		this.code = 200;
		this.json = json;
	}
	
	public void set400(T json){
		this.code = 400;
		this.json = json;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}


	public T getJson() {
		return json;
	}

	public void setJson(T json) {
		this.json = json;
	}

	@Override
	public String toString() {
		return "RestMassage [code=" + code + ", json=" + json + "]";
	}

	
}
