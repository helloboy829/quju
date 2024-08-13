package cn.codefit.quju.system.api.service;

import cn.codefit.quju.base.rpc.CommonResponse;
import cn.codefit.quju.system.api.dto.SysUserDto;
import cn.codefit.quju.system.api.vo.SysUserVo;

import java.util.List;

/**
 *
 */
public interface SysUserService {

    /**
     * 根据用户名获取用户信息
     *
     * @param userName
     * @return
     */
    CommonResponse<SysUserDto> selectSysUserByUserName(String userName);


    /**
     * 分页获取用户列表
     *
     * @return
     */
    CommonResponse<List<SysUserDto>> querySysUserList(SysUserVo param);

    /**
     * 根据id查询用户最新详情
     *
     * @param id
     * @return
     */
    CommonResponse<SysUserDto> querySysUserDetailById(Long id);

    /**
     * 用户登录后获取个人用户信息
     *
     * @param
     * @return
     */
    CommonResponse<SysUserDto> querySysUserDetail();

    /**
     * 新增或修改系统用户
     *
     * @param param
     * @return
     */
    CommonResponse<Boolean> addOrUpdateSysUser(SysUserVo param);

    /**
     * 更新系统用户状态
     *
     * @param param
     * @return
     */
    CommonResponse<Boolean> updateSysUserStatus(SysUserVo param);

    /**
     * 删除系统用户
     *
     * @param id 用户id
     * @return
     */
    CommonResponse<Boolean> deleteSysUserById(Long id);


}
