package com.template.springboot.mapper;

import com.template.springboot.model.Account;
import org.apache.ibatis.annotations.Select;

public interface AccountMapper {
    @Select({"SELECT", "name, password,nick_name", "FROM", "account", "WHERE", "name=#{name,jdbcType=VARCHAR}"})
    Account getAccountByName(String name);
}
