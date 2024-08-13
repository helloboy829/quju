package cn.codefit.quju.system.service.service.impl;

import cn.codefit.quju.base.constant.Constant;
import cn.codefit.quju.base.enums.ResponseCode;
import cn.codefit.quju.base.rpc.CommonResponse;
import cn.codefit.quju.base.util.UserUtils;
import cn.codefit.quju.system.api.dto.SysUserDto;
import cn.codefit.quju.system.api.service.SysUserService;
import cn.codefit.quju.system.api.vo.SysUserVo;
import cn.codefit.quju.system.model.po.SysUser;
import cn.codefit.quju.system.service.biz.SysUserBizService;
import cn.hutool.core.bean.BeanUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * web端用户相关接口
 */
@DubboService(version = Constant.VERSION, interfaceClass = SysUserService.class)
@RequestMapping(value = "/user")
@RestController
@Slf4j
public class SysUserServiceImpl implements SysUserService {

    @Autowired
    private SysUserBizService sysUserBizService;

    /**
     * 根据用户名获取用户信息
     *
     * @param userName
     * @return
     */
    @Override
    @PostMapping("/getUserDetailInfoByUserName")
    public CommonResponse<SysUserDto> selectSysUserByUserName(
            @RequestParam(name = "userName", required = false) @NotBlank(message = "用户名不能为空") String userName) {
        // 日志记录
        log.info("查询用户名为 {} 的用户详细信息", userName);

        // 查询用户信息
        SysUserDto sysUserDto = sysUserBizService.selectSysUserByUserName(userName);

        // 返回查询结果
        return CommonResponse.success(sysUserDto);
    }


    /**
     * 分页获取用户列表
     *
     * @return
     */
    @Override
    @RequestMapping(value = "/querySysUserList", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public CommonResponse<List<SysUserDto>> querySysUserList(@RequestBody SysUserVo param) {
        return sysUserBizService.querySysUserList(param);
    }

    /**
     * 根据id查询用户最新详情
     *
     * @param id
     * @return
     */
    @Override
    @RequestMapping(value = "/querySysUserDetailById", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public CommonResponse<SysUserDto> querySysUserDetailById(@RequestParam Long id) {
        if (id == null) {
            return CommonResponse.error(ResponseCode.ILLEGAL_ARGUMENT);
        }
        SysUserDto sysUserDto = sysUserBizService.selectSysUserById(id);
        return CommonResponse.success(sysUserDto);
    }

    /**
     * 用户登录后获取个人用户信息
     *
     * @param
     * @return
     */
    @Override
    @RequestMapping(value = "/querySysUserDetail", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public CommonResponse<SysUserDto> querySysUserDetail() {
        SysUserDto sysUserDto = sysUserBizService.selectSysUserById(UserUtils.getUserId());
        return CommonResponse.success(sysUserDto);
    }

    /**
     * 新增或修改系统用户
     *
     * @param param
     * @return
     */
    @Override
    @RequestMapping(value = "/addOrUpdateSysUser", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public CommonResponse<Boolean> addOrUpdateSysUser(@RequestBody SysUserVo param) {
        if (param == null) {
            return CommonResponse.error(ResponseCode.ILLEGAL_ARGUMENT);
        }

        if (StringUtils.isBlank(param.getUserName()) || (param.getRolesIds() == null || param.getRolesIds().size() == 0)) {
            return CommonResponse.error(ResponseCode.ILLEGAL_ARGUMENT);
        }
        if (param.getRolesIds().size() > 1) {
            return CommonResponse.error(ResponseCode.USER_ROLE_ERROR);
        }
        try {
            Long id = param.getId();
            List<Long> roleIds = param.getRolesIds();
            SysUser sysUser = BeanUtil.copyProperties(param, SysUser.class);
            //1、校验账号是否可用
            boolean result = sysUserBizService.checkSysUserName(param.getUserName(), id);
            if (result) {
                return CommonResponse.error(ResponseCode.USER_CREATE_ERROR);
            }
            if (id == null) {
                //2、新增用户
                sysUserBizService.addSysUser(sysUser);
                //3、新增用户-角色绑定关系
                roleIds.forEach(item -> {
                    sysUserBizService.addSysUserRole(sysUser.getId(), item);
                });
            } else {
                SysUserDto sysUserDto = sysUserBizService.selectSysUserById(id);

                if (sysUserDto.getUserName().equals(Constant.USERNAME_SUPER_ADMIN) ||
                        sysUserDto.getUserName().equals(Constant.USERNAME_SYSTEM_ADMIN)) {
                    if (!sysUser.getUserName().equals(Constant.USERNAME_SUPER_ADMIN) &&
                            !sysUser.getUserName().equals(Constant.USERNAME_SYSTEM_ADMIN)) {
                        return CommonResponse.error(ResponseCode.USER_UPDATE_ERROR);
                    }
                }

                //2、修改用户
                sysUserBizService.updateSysUser(sysUser);
                //3、新增用户-角色绑定关系
                if (sysUserDto.getRoleId() == null) {
                    //3、新增用户-角色绑定关系
                    roleIds.forEach(item -> {
                        sysUserBizService.addSysUserRole(sysUser.getId(), item);
                    });
                } else {
                    if (!roleIds.get(0).equals(sysUserDto.getRoleId())) {
                        //3、删除历史绑定关系
                        sysUserBizService.deleteSysUserRole(id, sysUserDto.getRoleId());
                        //4、新增用户-角色绑定关系
                        roleIds.forEach(item -> {
                            sysUserBizService.addSysUserRole(sysUser.getId(), item);
                        });
                    }
                }
            }
            return CommonResponse.success(true);
        } catch (Exception e) {
            log.error(ExceptionUtils.getStackTrace(e));
            return CommonResponse.error(ResponseCode.FAIL_COMMON);
        }
    }


    /**
     * 更新系统用户状态
     *
     * @param param
     * @return
     */
    @Override
    @RequestMapping(value = "/updateSysUserStatus", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public CommonResponse<Boolean> updateSysUserStatus(@RequestBody SysUserVo param) {
        if (param == null) {
            return CommonResponse.error(ResponseCode.ILLEGAL_ARGUMENT);
        }

        if (param.getId() == null || param.getStatus() == null) {
            return CommonResponse.error(ResponseCode.ILLEGAL_ARGUMENT);
        }
        try {
            SysUser sysUser = new SysUser();
            sysUser.setId(param.getId());
            sysUser.setStatus(param.getStatus());
            sysUserBizService.updateSysUser(sysUser);
            return CommonResponse.success(true);
        } catch (Exception e) {
            log.error(ExceptionUtils.getStackTrace(e));
            return CommonResponse.error(ResponseCode.FAIL_COMMON);
        }
    }

    /**
     * 删除系统用户
     *
     * @param id 用户id
     * @return
     */
    @Override
    @RequestMapping(value = "/deleteSysUserById", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public CommonResponse<Boolean> deleteSysUserById(@RequestParam Long id) {
        if (id == null) {
            return CommonResponse.error(ResponseCode.ILLEGAL_ARGUMENT);
        }
        try {
            SysUserDto sysUserDto = sysUserBizService.selectSysUserById(id);
            if (sysUserDto == null) {
                return CommonResponse.error(ResponseCode.HAS_NO_DATA);
            }
            if (sysUserDto.getUserName().equals(Constant.USERNAME_SUPER_ADMIN) ||
                    sysUserDto.getUserName().equals(Constant.USERNAME_SYSTEM_ADMIN)) {
                return CommonResponse.error(ResponseCode.USER_DELETET_ERROR);
            }
            //2、删除用户信息
            sysUserBizService.deleteSysUser(id);
            //3、删除用户-角色绑定信息
            sysUserBizService.deleteSysUserRole(id, sysUserDto.getRoleId());
            return CommonResponse.success(true);
        } catch (Exception e) {
            log.error(ExceptionUtils.getStackTrace(e));
            return CommonResponse.error(ResponseCode.FAIL_COMMON);
        }
    }
}
