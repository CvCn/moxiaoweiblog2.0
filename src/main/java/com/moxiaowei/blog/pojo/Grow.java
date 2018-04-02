package com.moxiaowei.blog.pojo;

import java.util.Date;

/**
 * 成长历程
 * @author moxiaowei
 *
 */
public class Grow {

	private long id;
	private String content;
	private Date creatdate;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Date getCreatdate() {
		return creatdate;
	}
	public void setCreatdate(Date creatdate) {
		this.creatdate = creatdate;
	}
	@Override
	public String toString() {
		return "Grow [id=" + id + ", content=" + content + ", creatdate=" + creatdate + "]";
	}
	
	
}
