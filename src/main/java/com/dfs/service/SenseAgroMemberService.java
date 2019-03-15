package com.dfs.service;

import com.dfs.mapper.SenseAgroMemberMapper;
import com.dfs.entity.SenseAgroMember;
import com.dfs.utils.RedisSingletonUtil;
import com.dfs.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author taoxy 2018/12/13
 */
@Service
@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
public class SenseAgroMemberService {

    private static final RedisSingletonUtil redisUtil = new RedisSingletonUtil();

    @Autowired
    private SenseAgroMemberMapper senseAgroMemberMapper;

    public SenseAgroMember findMember(String openid) {
        if (StringUtil.isEmpty(openid)) return null;
        SenseAgroMember senseAgroMember = null;
        try {
            senseAgroMember = redisUtil.getSenseAgroMember(openid);
            if (senseAgroMember != null) {
                return senseAgroMember;
            }
            senseAgroMember = senseAgroMemberMapper.selectByOpenId(openid);
            redisUtil.setSenseAgroMember(openid, senseAgroMember, 1);
            return senseAgroMember;
        } catch (Exception e) {
            senseAgroMember = senseAgroMemberMapper.selectByOpenId(openid);
            redisUtil.setSenseAgroMember(openid, senseAgroMember, 1);
            return senseAgroMember;
        }
    }
}
