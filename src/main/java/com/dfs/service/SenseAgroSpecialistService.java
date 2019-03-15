package com.dfs.service;

import com.dfs.mapper.SenseAgroSpecialistMapper;
import com.dfs.mapper.SenseAgroUserMapper;
import com.dfs.entity.SenseAgroSpecialist;
import com.dfs.entity.SenseAgroUser;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLIntegrityConstraintViolationException;

/**
 * @author taoxy 2019/01/30
 */
@Service
@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
public class SenseAgroSpecialistService {
    @Autowired
    private SenseAgroSpecialistMapper senseAgroSpecialistMapper;
    @Autowired
    private SenseAgroUserMapper senseAgroUserMapper;

    private static final Logger logger = Logger.getLogger(SenseAgroSpecialistService.class);

    /**
     * @description 用户登录逻辑
     */
    public SenseAgroSpecialist login(String name, String passwd) {
        return senseAgroSpecialistMapper.findUserBynameAndPasswd(name, passwd);
    }
    public int addSenseAgroUser(SenseAgroUser senseAgroUser) {
        return senseAgroUserMapper.insertSelective(senseAgroUser);
    }

}
