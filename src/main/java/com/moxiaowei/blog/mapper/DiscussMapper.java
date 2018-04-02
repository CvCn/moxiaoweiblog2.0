package com.moxiaowei.blog.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.moxiaowei.blog.pojo.Discuss;

public interface DiscussMapper {
	
	List<Discuss> getDiscussBy(@Param("d") Discuss discuss, @Param("de") boolean desc);
	/**
	 * 用于上边函数二级查询
	 * @param map
	 * @return
	 * @author moxiaowei
	 */
	List<Discuss> getDiscussByParentid(Map<String, Long> map);
	
	/**
	 * 通过用户id查找
	 * @param fuserid
	 * @return
	 * @author moxiaowei
	 */
	List<Discuss> getDiscussByFUser(long fuserid);
	
	/**
	 * 
	 * @param discuss
	 * @return
	 * @author moxiaowei
	 */
	int addDiscuss(Discuss discuss);
	
	int delDiscuss(long id, long fuserid);
	
	/**
	 * 直接删除，不用检查用户身份
	 * @param id
	 * @return
	 * @author moxiaowei
	 */
	int delDiscuss2(long id);
	
	/**
	 * 二级删除，仅用户递归删除
	 * @param parentid
	 * @return
	 * @author moxiaowei
	 */
	int delDiscussByParentid(long parentid);
	
	int updDiscuss(Discuss discuss);

}
