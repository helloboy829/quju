package cn.codefit.quju.system.service.biz.impl;

import cn.codefit.quju.base.constant.Constant;
import cn.codefit.quju.base.enums.ConstantEnum;
import cn.codefit.quju.base.enums.PlatformEnum;
import cn.codefit.quju.base.rpc.CommonResponse;
import cn.codefit.quju.base.util.EncryptUtil;
import cn.codefit.quju.base.util.UserUtils;
import cn.codefit.quju.system.api.dto.SysUserDto;
import cn.codefit.quju.system.api.vo.SysUserVo;
import cn.codefit.quju.system.model.dao.SysUserMapper;
import cn.codefit.quju.system.model.dao.SysUserRoleMapper;
import cn.codefit.quju.system.model.po.SysDept;
import cn.codefit.quju.system.model.po.SysRole;
import cn.codefit.quju.system.model.po.SysUser;
import cn.codefit.quju.system.model.po.SysUserExample;
import cn.codefit.quju.system.model.po.SysUserRole;
import cn.codefit.quju.system.model.po.SysUserRoleExample;
import cn.codefit.quju.system.service.biz.SysDeptBizService;
import cn.codefit.quju.system.service.biz.SysRoleBizService;
import cn.codefit.quju.system.service.biz.SysUserBizService;
import cn.codefit.quju.system.service.biz.dao.SysUserBizMapper;
import cn.hutool.core.bean.BeanUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 */
@Service
public class SysUserBizServiceImpl implements SysUserBizService {


    @Autowired
    private SysUserMapper sysUserMapper;
    @Autowired
    private SysUserBizMapper sysUserBizMapper;
    @Autowired
    private SysDeptBizService sysDeptBizService;
    @Autowired
    private SysRoleBizService sysRoleBizService;
    @Autowired
    private SysUserRoleMapper sysUserRoleMapper;

    @Override
    public SysUserDto selectSysUserByUserName(String userName) {
        SysUserExample sysUserExample = new SysUserExample();
        SysUserExample.Criteria criteria = sysUserExample.createCriteria();
        criteria.andUserNameEqualTo(userName);
        List<SysUser> sysUserList = sysUserMapper.selectByExample(sysUserExample);
        if (sysUserList.size() == 0) {
            return null;
        }
        SysUser sysUser = sysUserList.get(0);
        SysUserDto sysUserDto = BeanUtil.copyProperties(sysUser, SysUserDto.class);
        packSysUser(sysUserDto);
        return sysUserDto;
    }

    /**
     * 分页获取用户数据
     *
     * @param param
     * @return
     */
    @Override
    public CommonResponse<List<SysUserDto>> querySysUserList(SysUserVo param) {
        Map<String, Object> map = BeanUtil.copyProperties(param, Map.class);
        map.put("isDeleted", ConstantEnum.IS_DELETE_NO.getCode());
        int pageNum = param.getPageNum();
        int pageSize = param.getPageSize();
        PageHelper.startPage(pageNum, pageSize);
        List<SysUserDto> list = sysUserBizMapper.selectByCondition(map);
        if (list.size() > 0) {
            list.forEach(item -> {
                packSysUser(item);
            });
        }
        PageInfo<SysUserDto> pageInfo = new PageInfo(list);
        int count = Integer.parseInt(String.valueOf(pageInfo.getTotal()));
        if (CollectionUtils.isEmpty(list)) {
            return CommonResponse.success(new PageInfo<>(new ArrayList<>()));
        }
        return CommonResponse.success(pageInfo);
    }

    /**
     * 包装返回的用户信息
     *
     * @param sysUserDto
     * @return
     */
    private SysUserDto packSysUser(SysUserDto sysUserDto) {
        //1、查询用户角色信息
        List<SysRole> sysRoles = sysRoleBizService.getSysRoleByUserId(sysUserDto.getId());
        if (sysRoles.size() > 0) {
            SysRole sysRole = sysRoles.get(0);
            sysUserDto.setRoleId(sysRole.getId());
            sysUserDto.setRoleCode(sysRole.getCode());
            sysUserDto.setRoleName(sysRole.getName());
            //1.1、返回用户拥有的权限信息
        }
        //2、查询用户对应的部门名称
        SysDept sysDept = sysDeptBizService.selectSysDeptById(sysUserDto.getDeptId());
        if (sysDept != null) {
            sysUserDto.setDeptName(sysDept.getName());
        }
        //3、用户敏感信息脱敏返回
        sysUserDto.setMobile(EncryptUtil.mobileEncrypt(sysUserDto.getMobile()));
        sysUserDto.setUserEmail(EncryptUtil.emailEncrypt(sysUserDto.getUserEmail()));
        sysUserDto.setIdNumber(EncryptUtil.idEncrypt(sysUserDto.getIdNumber()));
        return sysUserDto;
    }

    /**
     * 根据id查询用户详情
     *
     * @param id
     * @return
     */
    @Override
    public SysUserDto selectSysUserById(Long id) {
        SysUser sysUser = sysUserMapper.selectByPrimaryKey(id);
        SysUserDto sysUserDto = BeanUtil.copyProperties(sysUser, SysUserDto.class);
        packSysUser(sysUserDto);
        return sysUserDto;
    }

    /**
     * 校验当前登录账号是否已被使用
     *
     * @param userName 用户名(登录账号)
     * @param id       存在即为更新判断 不存在为新增时判断
     * @return
     */
    @Override
    public boolean checkSysUserName(String userName, Long id) {
        Map<String, Object> map = new HashMap<>();
        map.put("userName", userName);
        if (id != null) {
            map.put("noId", id);
        }
        int number = sysUserBizMapper.checkSysUserName(map);
        return number > 0 ? true : false;
    }

    /**
     * 新增系统用户
     *
     * @param sysUser
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void addSysUser(SysUser sysUser) {
        sysUser.setPassword(Constant.DEFAUT_PASSWORD);
        sysUser.setCreateTime(new Date());
        sysUser.setCreateUser(UserUtils.getUserId().toString());
        sysUser.setCreateUserName(UserUtils.getUsername());
        sysUser.setStatus(Constant.STATUS_ENABLE);
        sysUser.setIsDeleted(ConstantEnum.IS_DELETE_NO.getCode());
        sysUser.setPlatform(PlatformEnum.PLATEFORM_0.getCode());
        sysUser.setHasWechat(0);
        sysUserMapper.insert(sysUser);
    }

    /**
     * 新增用户-角色绑定关系
     *
     * @param userId 用户id
     * @param roleId 角色id
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void addSysUserRole(Long userId, Long roleId) {
        SysUserRole sysUserRole = new SysUserRole();
        sysUserRole.setUserId(userId);
        sysUserRole.setRoleId(roleId);
        sysUserRoleMapper.insert(sysUserRole);
    }

    /**
     * 删除用户-角色绑定关系
     *
     * @param userId 用户id
     * @param roleId 角色id
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void deleteSysUserRole(Long userId, Long roleId) {
        SysUserRoleExample example = new SysUserRoleExample();
        SysUserRoleExample.Criteria criteria = example.createCriteria();
        criteria.andUserIdEqualTo(userId);
        criteria.andRoleIdEqualTo(roleId);

        sysUserRoleMapper.deleteByExample(example);
    }

    /**
     * 修改用户信息
     *
     * @param sysUser
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void updateSysUser(SysUser sysUser) {
        if (StringUtils.isNotBlank(sysUser.getMobile()) && sysUser.getMobile().contains("*")) {
            sysUser.setMobile(null);
        }
        if (StringUtils.isNotBlank(sysUser.getUserEmail()) && sysUser.getUserEmail().contains("*")) {
            sysUser.setUserEmail(null);
        }
        if (StringUtils.isNotBlank(sysUser.getIdNumber()) && sysUser.getIdNumber().contains("*")) {
            sysUser.setIdNumber(null);
        }
        sysUser.setModifyTime(new Date());
        sysUser.setModifyUser(UserUtils.getUserId().toString());
        sysUser.setModifyUserName(UserUtils.getUsername());
        sysUserMapper.updateByPrimaryKeySelective(sysUser);
    }

    /**
     * 根据用户id删除用户信息
     *
     * @param userId 用户id
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void deleteSysUser(Long userId) {
        sysUserMapper.deleteByPrimaryKey(userId);
    }
}
