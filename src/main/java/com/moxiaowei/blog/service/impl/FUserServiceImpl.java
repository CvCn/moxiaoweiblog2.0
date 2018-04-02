package com.moxiaowei.blog.service.impl;

import com.moxiaowei.blog.mapper.FUserMapper;
import com.moxiaowei.blog.pojo.FUser;
import com.moxiaowei.blog.service.FUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FUserServiceImpl implements FUserService {

	@Autowired
	private FUserMapper fUserMapper;
	
	@Override
	public boolean addFUser(FUser user) {
		int addFUser = this.fUserMapper.addFUser(user);
		
		return addFUser > 0;
	}

	@Override
	public FUser getFUser(String accesstoken) {
		FUser fUser2 = new FUser();
		fUser2.setAccesstoken(accesstoken);
		List<FUser> fUser = this.fUserMapper.getFUserBy(fUser2);
		if(fUser != null && fUser.size() > 0)
			return fUser.get(0);
		return null;
	}

	@Override
	public FUser getFUserByUid(String uid) {
		FUser fUser = new FUser();
		fUser.setUid(uid);
		List<FUser> fUserByUid = this.fUserMapper.getFUserBy(fUser);
		if(fUserByUid != null &&fUserByUid.size()>0)
			return fUserByUid.get(0);
		return null;
	}

	@Override
	public boolean delFUser(String accesstoken) {
		int delFUser = this.fUserMapper.delFUser(accesstoken);
		return delFUser > 0;
	}

	@Override
	public boolean updFUser(FUser fuser) {
		int updFUser = this.fUserMapper.updFUser(fuser);
		return updFUser > 0;
	}

	@Override
	public FUser getFUserById(long id) {
		FUser fUser = new FUser();
		fUser.setId(id);
		List<FUser> fUserByUid = this.fUserMapper.getFUserBy(fUser);
		if(fUserByUid != null &&fUserByUid.size()>0)
			return fUserByUid.get(0);
		return null;
	}

}
