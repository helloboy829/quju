package cn.codefit.quju.system.service.biz.dao;


import cn.codefit.quju.system.api.dto.SysUserDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface SysUserBizMapper {

    List<SysUserDto> selectByCondition(Map<String, Object> map);

    int checkSysUserName(Map<String, Object> map);
}
