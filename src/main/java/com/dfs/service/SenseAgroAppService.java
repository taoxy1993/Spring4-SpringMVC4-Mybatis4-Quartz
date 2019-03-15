package com.dfs.service;

import com.dfs.mapper.SenseAgroUserMapper;
import com.dfs.entity.SenseAgroUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author taoxy 2019/02/02
 */
@Service
@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
public class SenseAgroAppService {
	@Autowired
	private SenseAgroUserMapper senseAgroUserMapper;

	//判断登录是否有效
	public synchronized List<SenseAgroUser> findAppUserByToken(int nowTime, String sessionId) {
		return senseAgroUserMapper.findUserByToken(nowTime, sessionId);
	}
}
