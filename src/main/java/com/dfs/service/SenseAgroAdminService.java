package com.dfs.service;

import com.dfs.mapper.SenseAgroAdminMapper;
import com.dfs.entity.SenseAgroAdmin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Title: UserService.java
 * Description: 对用户相关的业务逻辑的抽象(面向接口编程)
 */

/**
 * @author taoxy 2019/01/02
 */
@Service
@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
public class SenseAgroAdminService {

	@Autowired
	private SenseAgroAdminMapper senseAgroAdminMapper;

	/**
	 * @description 用户登录逻辑
	 */
	public SenseAgroAdmin login(String name, String passwd) {
		return senseAgroAdminMapper.findUserBynameAndPasswd(name, passwd);
	}
}

