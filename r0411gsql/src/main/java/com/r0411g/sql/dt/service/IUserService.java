package com.r0411g.sql.dt.service;

import com.r0411g.sql.dt.pojo.User;

/**
 * @Description
 *
 */
public interface IUserService {

	/**
	 * @Description
	 *
	 */
	public int insertUser(User user);

	/**
	 * @Description
	 *
	 */
	public int deleteByuserNum(User user);

	/**
	 * @Description
	 *
	 */
	public int updateByUserNum(User user);

	/**
	 * @Description
	 *
	 */
	public User selectByUserNum(User user);


}
