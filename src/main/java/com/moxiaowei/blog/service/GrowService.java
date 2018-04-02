package com.moxiaowei.blog.service;

import java.util.List;

import com.moxiaowei.blog.pojo.Grow;

/**
 * 成长历程
 * @author moxiaowei
 *
 */
public interface GrowService {

	List<Grow> getGrow(int page, int rows);
}
