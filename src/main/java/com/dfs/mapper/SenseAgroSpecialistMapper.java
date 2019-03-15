package com.dfs.mapper;

import com.dfs.entity.SenseAgroSpecialist;
import org.apache.ibatis.annotations.Param;

public interface SenseAgroSpecialistMapper {

    SenseAgroSpecialist findUserBynameAndPasswd(@Param("uname") String uname, @Param("passwd") String passwd);

}