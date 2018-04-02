package com.moxiaowei.blog.pojo;

import java.util.Arrays;
import java.util.Date;

/**
 * 博客
 *
 * @author moxiaowei
 */
public class Blog {

    private long id;
    private String title;
    private byte[] content;
    private String content2;
    private Date creatdate;
    private Date updatadate;
    private long readcount;
    //博客描述，内容的前200个字符
    private String remark;
    private String author;


    public Blog() {
        super();
    }

    public Blog(long id, String title, byte[] content, Date creatdate, Date updatadate, long readcount, String remark, String author) {
        super();
        this.id = id;
        this.title = title;
        this.content = content;
        this.creatdate = creatdate;
        this.updatadate = updatadate;
        this.readcount = readcount;
        this.remark = remark;
        this.author = author;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }

    public Date getCreatdate() {
        return creatdate;
    }

    public void setCreatdate(Date creatdate) {
        this.creatdate = creatdate;
    }

    public Date getUpdatadate() {
        return updatadate;
    }

    public void setUpdatadate(Date updatadate) {
        this.updatadate = updatadate;
    }

    public long getReadcount() {
        return readcount;
    }

    public void setReadcount(long readcount) {
        this.readcount = readcount;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent2() {
        return content2;
    }

    public void setContent2(String content2) {
        this.content2 = content2;
    }

    @Override
    public String toString() {
        return "Blog [id=" + id + ", title=" + title + ", content=" + Arrays.toString(content) + ", content2="
                + content2 + ", creatdate=" + creatdate + ", updatadate=" + updatadate + ", readcount=" + readcount
                + ", remark=" + remark + ", author=" + author + "]";
    }

    @Override
    public boolean equals(Object obj) {
        return obj.equals(this.id);
    }

}
