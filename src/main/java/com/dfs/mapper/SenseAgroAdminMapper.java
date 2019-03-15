package com.dfs.mapper;

import com.dfs.entity.SenseAgroAdmin;
import org.apache.ibatis.annotations.Param;

/**
 * @author taoxy 2018/12/15
 */
public interface SenseAgroAdminMapper {

    SenseAgroAdmin findUserBynameAndPasswd(@Param("memberName") String name, @Param("memberPassword") String passwd);
}