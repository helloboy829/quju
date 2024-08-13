package cn.codefit.quju.system.service.biz;

import cn.codefit.quju.base.rpc.CommonResponse;
import cn.codefit.quju.system.api.dto.SysUserDto;
import cn.codefit.quju.system.api.vo.SysUserVo;
import cn.codefit.quju.system.model.po.SysUser;

import java.util.List;

/**
 *
 */
public interface SysUserBizService {

    /**
     * 根据用户名获取用户信息
     *
     * @param userName
     * @return
     */
    SysUserDto selectSysUserByUserName(String userName);

    /**
     * 分页获取用户数据
     *
     * @param param
     * @return
     */
    CommonResponse<List<SysUserDto>> querySysUserList(SysUserVo param);

    /**
     * 根据id查询用户详情
     *
     * @param id
     * @return
     */
    SysUserDto selectSysUserById(Long id);

    /**
     * 校验当前登录账号是否已被使用
     *
     * @param userName 用户名(登录账号)
     * @param id       存在即为更新判断 不存在为新增时判断
     * @return
     */
    boolean checkSysUserName(String userName, Long id);

    /**
     * 新增系统用户
     *
     * @param sysUser
     */
    void addSysUser(SysUser sysUser);

    /**
     * 新增用户-角色绑定关系
     *
     * @param userId 用户id
     * @param roleId 角色id
     */
    void addSysUserRole(Long userId, Long roleId);

    /**
     * 删除用户-角色绑定关系
     *
     * @param userId 用户id
     * @param roleId 角色id
     */
    void deleteSysUserRole(Long userId, Long roleId);

    /**
     * 修改用户信息
     *
     * @param sysUser
     */
    void updateSysUser(SysUser sysUser);

    /**
     * 根据用户id删除用户信息
     *
     * @param userId 用户id
     */
    void deleteSysUser(Long userId);
}
