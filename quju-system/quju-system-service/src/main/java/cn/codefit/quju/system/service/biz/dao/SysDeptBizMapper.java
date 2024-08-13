package cn.codefit.quju.system.service.biz.dao;


import cn.codefit.quju.system.api.dto.SysDeptDto;

import java.util.List;
import java.util.Map;

public interface SysDeptBizMapper {

    List<SysDeptDto> selectByCondition(Map<String, Object> map);

    List<SysDeptDto> selectAllChildByParentId(Map<String, Object> map);

    /**
     * 判断部门名称不能重复
     *
     * @param map
     * @return
     */
    int checkSysDeptName(Map<String, Object> map);
}
