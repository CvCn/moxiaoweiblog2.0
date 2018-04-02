package com.moxiaowei.blog.service.impl;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.moxiaowei.blog.pojo.FUser;
import com.moxiaowei.blog.service.FUserService;
import com.moxiaowei.blog.service.ThirdPartyService;
import com.moxiaowei.blog.util.CookieUtils;
import com.moxiaowei.blog.util.JedisClientSingle;

@Service
public class ThirdPartyServiceImpl implements ThirdPartyService {


	@Autowired
	private JedisClientSingle jedisClientSingle;
	
	@Autowired
	private FUserService fUserServiceImpl;
	/**
	 * 因为获取用户操作比较频繁，所以抽象出来的办法
	 * @param session
	 * @return
	 * @author moxiaowei
	 */
	public FUser getFUser(HttpServletRequest req) {
		String id = CookieUtils.getCookieValue(req, "MOXIAOWEI_LOGIN_TOKEN");
		if (id != null && id != "") {
			String uid = this.jedisClientSingle.get(id);
			if (uid != null && !uid.equals("0")) {
				this.jedisClientSingle.expire(id, 60 * 30);
				return this.fUserServiceImpl.getFUserByUid(uid);
			}
		}
		return null;
//		return this.fUserServiceImpl.getFUserByUid("1231");
	}
}
