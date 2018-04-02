package com.moxiaowei.blog.impl;

import com.github.pagehelper.PageHelper;
import com.moxiaowei.blog.mapper.DiscussMapper;
import com.moxiaowei.blog.mapper.FMassageMapper;
import com.moxiaowei.blog.pojo.Discuss;
import com.moxiaowei.blog.pojo.FMassage;
import com.moxiaowei.blog.service.FMassageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FMassageServiceImpl implements FMassageService {

	
	@Autowired
	private FMassageMapper fMassageMapper;
	
	@Autowired
	private DiscussMapper discussMapper;
	
	@Override
	public List<FMassage> getFMassage(long dfuserid, int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		List<FMassage> fMassage = this.fMassageMapper.getFMassage(dfuserid);
		//手动查询评论的相关信息，因为分页有问题
		for(FMassage pf : fMassage){
			Discuss discuss = new Discuss();
			discuss.setId(pf.getParentid());
			discuss.setParentid(-1);
			pf.setParentdiscuss(this.discussMapper.getDiscussBy(discuss, false).get(0));
			
			Discuss discuss2 = new Discuss();
			discuss2.setId(pf.getChildid());
			discuss2.setParentid(-1);
			pf.setChilddiscuss(this.discussMapper.getDiscussBy(discuss2, false).get(0));
		}
		return fMassage;
	}

	@Override
	public boolean addFMassage(FMassage fMassage) {
		int addFMassage = this.fMassageMapper.addFMassage(fMassage);
		return addFMassage > 0;
	}

	@Override
	public boolean updstate(long dfuserid) {
		int updstate = this.fMassageMapper.updstate(dfuserid);
		return updstate > 0;
	}

	@Override
	public boolean delFMassage(long id, long dfuserid) {
		int delFMassage = this.fMassageMapper.delFMassage(id, dfuserid);
		return delFMassage > 0;
	}

	@Override
	public boolean delEmpty(long dfuserid) {
		int empty = this.fMassageMapper.empty(dfuserid);
		return empty > 0;
	}

	@Override
	public int getStateIsZero(long dfuserid) {
		return this.fMassageMapper.getStateIsZero(dfuserid);
	}
	

}
