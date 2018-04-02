package com.moxiaowei.blog.pojo;

import java.util.Date;
import java.util.List;

/**
 * 评论
 * @author moxiaowei
 *
 */
public class Discuss {

	private long id;
	//评论内容
	private String content;
	private Date createdate;
	private Date updatedate;
	private long blogid;
	private Blog blog;
	private long fuserid;
	private FUser fuser;
	private long parentid;
	//子评论
	private List<Discuss> dislist;

	public Discuss(long id, String content, Date createdate, Date updatedate, long blogid, Blog blog, long fuserid,
			FUser fuser, long parentid, List<Discuss> dislist) {
		super();
		this.id = id;
		this.content = content;
		this.createdate = createdate;
		this.updatedate = updatedate;
		this.blogid = blogid;
		this.blog = blog;
		this.fuserid = fuserid;
		this.fuser = fuser;
		this.parentid = parentid;
		this.dislist = dislist;
	}

	public Discuss() {
		super();
	}

	public long getId() {
		return id;
	}

	public String getContent() {
		return content;
	}

	public Date getCreatedate() {
		return createdate;
	}

	public Date getUpdatedate() {
		return updatedate;
	}

	public long getBlogid() {
		return blogid;
	}

	public long getFuserid() {
		return fuserid;
	}

	public FUser getFUser() {
		return fuser;
	}

	public long getParentid() {
		return parentid;
	}

	public List<Discuss> getDislist() {
		return dislist;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public void setCreatedate(Date createdate) {
		this.createdate = createdate;
	}

	public void setUpdatedate(Date updatedate) {
		this.updatedate = updatedate;
	}

	public void setBlogid(long blogid) {
		this.blogid = blogid;
	}

	public void setFuserid(long fuserid) {
		this.fuserid = fuserid;
	}

	public void setFUser(FUser fuser) {
		this.fuser = fuser;
	}

	public void setParentid(long parentid) {
		this.parentid = parentid;
	}

	public void setDislist(List<Discuss> dislist) {
		this.dislist = dislist;
	}

	public Blog getBlog() {
		return blog;
	}

	public void setBlog(Blog blog) {
		this.blog = blog;
	}

	@Override
	public String toString() {
		return "Discuss [id=" + id + ", content=" + content + ", createdate=" + createdate + ", updatedate="
				+ updatedate + ", blogid=" + blogid + ", blog=" + blog + ", fuserid=" + fuserid + ", fuser=" + fuser
				+ ", parentid=" + parentid + ", dislist=" + dislist + "]";
	}

}
