package com.moxiaowei.blog.pojo;

import java.util.Date;

/**
 * 普通用户，对接的新浪登录
 * @author moxiaowei
 *
 */
public class FUser {

	private long id;
	private String username;
	private Date createdate;
	private long unreadcount;
	//最后登录
	private Date lastlogin;
	//通过code识别用户的token
	private String accesstoken;
	//用户的新浪微博id
	private String uid;
	//头像大图
	private String img;
	//个人描述信息
	private String remark;
	//授权方
	private String third;
	
	public FUser(long id, String username, Date createdate, long unreadcount, Date lastlogin, String accesstoken,
			String uid, String img, String remark, String third) {
		super();
		this.id = id;
		this.username = username;
		this.createdate = createdate;
		this.unreadcount = unreadcount;
		this.lastlogin = lastlogin;
		this.accesstoken = accesstoken;
		this.uid = uid;
		this.img = img;
		this.remark = remark;
		this.third = third;
	}
	public FUser() {
		super();
	}
	public long getId() {
		return id;
	}
	public String getUsername() {
		return username;
	}
	public Date getCreatedate() {
		return createdate;
	}
	public long getUnreadcount() {
		return unreadcount;
	}
	public Date getLastlogin() {
		return lastlogin;
	}
	public void setId(long id) {
		this.id = id;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public void setCreatedate(Date createdate) {
		this.createdate = createdate;
	}
	public void setUnreadcount(long unreadcount) {
		this.unreadcount = unreadcount;
	}
	public void setLastlogin(Date lastlogin) {
		this.lastlogin = lastlogin;
	}
	public String getAccesstoken() {
		return accesstoken;
	}
	public void setAccesstoken(String accesstoken) {
		this.accesstoken = accesstoken;
	}
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getThird() {
		return third;
	}
	public void setThird(String third) {
		this.third = third;
	}
	
}
