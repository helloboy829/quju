package cn.codefit.quju.system.service.biz.dao;


import cn.codefit.quju.system.api.dto.SysRoleDto;

import java.util.List;
import java.util.Map;

public interface SysRoleBizMapper {

    List<SysRoleDto> selectByCondition(Map<String, Object> map);

    /**
     * 判断角色编码不能重复
     *
     * @param map
     * @return
     */
    int checkSysRoleCode(Map<String, Object> map);
}
