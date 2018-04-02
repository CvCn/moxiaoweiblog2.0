package com.moxiaowei.blog.pojo;

import java.util.Date;

/**
 * 消息，用于被评论提醒
 * @author moxiaowei
 *
 */
public class FMassage {

	private long id;
	//被评论的评论对象信息
	private long parentid;
	private Discuss parentdiscuss;
	//主动评论的评论对象信息
	private long childid;
	private Discuss childdiscuss;
	//相关博客信息
	private long blogid;
	private Blog blog;
	//是否已读，0未读，1已读
	private int state;
	//被评论用户id
	private long dfuserid;
	//日期
	private Date disdate;
	
	public FMassage(long parentid, long childid, long blogid, long dfuserid) {
		super();
		this.parentid = parentid;
		this.childid = childid;
		this.blogid = blogid;
		this.dfuserid = dfuserid;
	}
	public FMassage() {
		super();
	}
	public long getId() {
		return id;
	}
	public long getParentid() {
		return parentid;
	}
	public Discuss getParentdiscuss() {
		return parentdiscuss;
	}
	public long getChildid() {
		return childid;
	}
	public Discuss getChilddiscuss() {
		return childdiscuss;
	}
	public long getBlogid() {
		return blogid;
	}
	public Blog getBlog() {
		return blog;
	}
	public int getState() {
		return state;
	}
	public void setId(long id) {
		this.id = id;
	}
	public void setParentid(long parentid) {
		this.parentid = parentid;
	}
	public void setParentdiscuss(Discuss parentdiscuss) {
		this.parentdiscuss = parentdiscuss;
	}
	public void setChildid(long childid) {
		this.childid = childid;
	}
	public void setChilddiscuss(Discuss childdiscuss) {
		this.childdiscuss = childdiscuss;
	}
	public void setBlogid(long blogid) {
		this.blogid = blogid;
	}
	public void setBlog(Blog blog) {
		this.blog = blog;
	}
	public void setState(int state) {
		this.state = state;
	}
	public long getDfuserid() {
		return dfuserid;
	}
	public void setDfuserid(long dfuserid) {
		this.dfuserid = dfuserid;
	}
	public Date getDisdate() {
		return disdate;
	}
	public void setDisdate(Date disdate) {
		this.disdate = disdate;
	}
	@Override
	public String toString() {
		return "FMassage [id=" + id + ", parentid=" + parentid + ", parentdiscuss=" + parentdiscuss + ", childid="
				+ childid + ", childdiscuss=" + childdiscuss + ", blogid=" + blogid + ", blog=" + blog + ", state="
				+ state + ", dfuserid=" + dfuserid + ", disdate=" + disdate + "]";
	}
	
}
