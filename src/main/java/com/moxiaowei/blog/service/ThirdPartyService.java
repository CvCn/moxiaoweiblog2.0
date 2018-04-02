package com.moxiaowei.blog.service;

import javax.servlet.http.HttpServletRequest;

import com.moxiaowei.blog.pojo.FUser;

public interface ThirdPartyService {

	FUser getFUser(HttpServletRequest req);
}
