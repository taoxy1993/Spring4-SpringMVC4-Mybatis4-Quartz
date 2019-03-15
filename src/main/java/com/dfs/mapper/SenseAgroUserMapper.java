package com.dfs.mapper;

import com.dfs.entity.SenseAgroUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SenseAgroUserMapper {

    int insertSelective(SenseAgroUser record);

    List<SenseAgroUser> findUserByToken(@Param("nowTime") int nowTime, @Param("sessionId") String sessionId);
}