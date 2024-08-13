package cn.codefit.quju.system.service.service.impl;


import cn.codefit.quju.base.constant.Constant;
import cn.codefit.quju.base.enums.ResponseCode;
import cn.codefit.quju.base.model.DropOption;
import cn.codefit.quju.base.rpc.CommonResponse;
import cn.codefit.quju.system.api.dto.SysRoleDto;
import cn.codefit.quju.system.api.service.SysRoleService;
import cn.codefit.quju.system.api.vo.SysRoleVo;
import cn.codefit.quju.system.model.po.SysRole;
import cn.codefit.quju.system.service.biz.SysRoleBizService;
import cn.hutool.core.bean.BeanUtil;
import lombok.extern.slf4j.Slf4j;
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

import java.util.ArrayList;
import java.util.List;

/**
 * 角色业务接口
 */
@DubboService(version = Constant.VERSION, interfaceClass = SysRoleService.class)
@RequestMapping(value = "/role")
@RestController
@Slf4j
public class SysRoleServiceImpl implements SysRoleService {


    @Autowired
    private SysRoleBizService sysRoleBizService;

    /**
     * 分页获取角色列表
     *
     * @return
     */
    @Override
    @PostMapping(value = "/querySysRoleList")
    public CommonResponse<List<SysRoleDto>> querySysRoleList(@RequestBody SysRoleVo param) {
        return sysRoleBizService.querySysRoleList(param);
    }

}
