package cn.codefit.quju.system.api.service;


import cn.codefit.quju.base.model.DropOption;
import cn.codefit.quju.base.rpc.CommonResponse;
import cn.codefit.quju.system.api.dto.SysDeptDto;
import cn.codefit.quju.system.api.vo.SysDeptVo;

import java.util.List;

/**
 * 菜单路由业务接口
 */
public interface SysDeptService {
    /**
     * 部门列表查询
     *
     * @return
     */
    CommonResponse<List<SysDeptDto>> querySysDeptList(SysDeptVo param);


    /**
     * 新增/修改部门
     *
     * @param param
     * @return
     */
    CommonResponse<Boolean> saveDept(SysDeptVo param);

    /**
     * 删除部门
     *
     * @param id 部门ID
     * @return
     */
    CommonResponse<Boolean> deleteByIds(Long id);


    /**
     * 新增部门时获取下拉部门树
     *
     * @return
     */
    CommonResponse<List<DropOption>> lisetDeptOptions();


}
