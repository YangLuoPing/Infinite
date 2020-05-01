package com.base.leran.jdbc.servcie;

import java.util.List;
import java.util.Map;

public interface JdbcService {
    public List<Map<String, Object>> findAll();
    public String addUser();
    public String updateUser(String code);
    public String delUser(Integer code);
}
