package com.moxiaowei.blog.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.moxiaowei.blog.service.NoticeService;
import com.moxiaowei.blog.util.JedisClientSingle;

@Service
public class NoticeServiceImpl implements NoticeService {

	
	@Autowired
	private JedisClientSingle jedisClientSingle;
	
	@Value("${NOTICE_STR}")
	private String NOTICE_STR;
	
	@Override
	public String getNotice() {
		return this.jedisClientSingle.get(this.NOTICE_STR);
	}

}
