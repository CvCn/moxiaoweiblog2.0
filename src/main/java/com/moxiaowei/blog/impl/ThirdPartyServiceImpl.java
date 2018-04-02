package com.moxiaowei.blog.impl;

import com.moxiaowei.blog.pojo.FUser;
import com.moxiaowei.blog.service.FUserService;
import com.moxiaowei.blog.service.ThirdPartyService;
import com.moxiaowei.blog.util.CookieUtils;
import com.moxiaowei.blog.util.JedisClientSingle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public class ThirdPartyServiceImpl implements ThirdPartyService {


	@Autowired
	private JedisClientSingle jedisClientSingle;
	
	@Autowired
	private FUserService fUserServiceImpl;
	/**
	 * 因为获取用户操作比较频繁，所以抽象出来的办法
	 * @return
	 * @author moxiaowei
	 */
	public FUser getFUser(HttpServletRequest req) {
		String id = CookieUtils.getCookieValue(req, "MOXIAOWEI_LOGIN_TOKEN");
		if (id != null && id.equals("")) {
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
