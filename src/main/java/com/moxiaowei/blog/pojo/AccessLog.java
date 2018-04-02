package com.moxiaowei.blog.pojo;

import java.util.Date;

/**
 * 访问日志
 *
 * @author moxiaowei
 */
public class AccessLog {

    private long id;
    private String path;
    private Date accessdate;
    private String ip;
    //此ip的位置
    private String address;

    public AccessLog(String path, String ip, String address) {
        super();
        this.path = path;
        this.ip = ip;
        this.address = address;
    }

    public AccessLog() {
        super();
    }

    public long getId() {
        return id;
    }

    public String getPath() {
        return path;
    }

    public Date getAccessdate() {
        return accessdate;
    }

    public String getIp() {
        return ip;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setAccessdate(Date accessdate) {
        this.accessdate = accessdate;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "AccessLog [id=" + id + ", path=" + path + ", accessdate=" + accessdate + ", ip=" + ip + ", address="
                + address + "]";
    }

}
