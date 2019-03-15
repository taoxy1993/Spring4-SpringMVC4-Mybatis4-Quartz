package com.dfs.mapper;

import com.dfs.entity.SenseAgroMember;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author taoxy 2018/12/15
 */
public interface SenseAgroMemberMapper {
    SenseAgroMember selectByOpenId(@Param("memberOpenid") String memberOpenid);
}