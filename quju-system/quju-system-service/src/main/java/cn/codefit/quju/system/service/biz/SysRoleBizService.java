package cn.codefit.quju.system.service.biz;

import cn.codefit.quju.base.rpc.CommonResponse;
import cn.codefit.quju.system.api.dto.SysRoleDto;
import cn.codefit.quju.system.api.vo.SysRoleVo;
import cn.codefit.quju.system.model.po.SysRole;
import cn.codefit.quju.system.model.po.SysUserRole;

import java.util.List;

/**
 *
 */
public interface SysRoleBizService {

    /**
     * 分页获取角色列表
     *
     * @return
     */
    CommonResponse<List<SysRoleDto>> querySysRoleList(SysRoleVo param);

    List<SysRoleDto> querySysRoleListNoPage(SysRoleVo param);

    SysRole getSysRoleByRoleCode(String code);

    SysRole getSysRoleByRoleId(Long id);

    List<SysRole> getSysRoleByUserId(Long userId);

    List<SysUserRole> getSysUserRole(Long id, Integer type);
}
