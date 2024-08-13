package cn.codefit.quju.system.api.service;


import cn.codefit.quju.base.model.DropOption;
import cn.codefit.quju.base.rpc.CommonResponse;
import cn.codefit.quju.system.api.dto.SysRoleDto;
import cn.codefit.quju.system.api.vo.SysRoleVo;

import java.util.List;

/**
 * 角色业务接口层
 */
public interface SysRoleService {

    /**
     * 分页获取角色列表
     *
     * @return
     */
    CommonResponse<List<SysRoleDto>> querySysRoleList(SysRoleVo param);

}

