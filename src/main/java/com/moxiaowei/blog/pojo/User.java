package com.moxiaowei.blog.pojo;

import java.util.Date;

/**
 * 管理员用户
 * @author moxiaowei
 *
 */
public class User {

	private long id;
	private String username;
	private String pwd;
	private Date creatdate;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	public Date getCreatdate() {
		return creatdate;
	}
	public void setCreatdate(Date creatdate) {
		this.creatdate = creatdate;
	}
	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", pwd=" + pwd + ", creatdate=" + creatdate + "]";
	}
	
	
}
