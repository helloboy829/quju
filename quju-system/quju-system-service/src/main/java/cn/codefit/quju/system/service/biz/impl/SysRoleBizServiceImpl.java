package cn.codefit.quju.system.service.biz.impl;

import cn.codefit.quju.base.enums.ConstantEnum;
import cn.codefit.quju.base.rpc.CommonResponse;
import cn.codefit.quju.system.api.dto.SysRoleDto;
import cn.codefit.quju.system.api.vo.SysRoleVo;
import cn.codefit.quju.system.model.dao.SysRoleMapper;
import cn.codefit.quju.system.model.dao.SysUserRoleMapper;
import cn.codefit.quju.system.model.po.SysRole;
import cn.codefit.quju.system.model.po.SysRoleExample;
import cn.codefit.quju.system.model.po.SysUserRole;
import cn.codefit.quju.system.model.po.SysUserRoleExample;
import cn.codefit.quju.system.service.biz.SysRoleBizService;
import cn.codefit.quju.system.service.biz.dao.SysRoleBizMapper;
import cn.hutool.core.bean.BeanUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 *
 */
@Service
public class SysRoleBizServiceImpl implements SysRoleBizService {
    @Autowired
    private SysRoleBizMapper sysRoleBizMapper;
    @Autowired
    private SysRoleMapper sysRoleMapper;
    @Autowired
    private SysUserRoleMapper sysUserRoleMapper;

    /**
     * 分页获取角色列表
     *
     * @return
     */
    @Override
    public CommonResponse<List<SysRoleDto>> querySysRoleList(SysRoleVo param) {
        Map<String, Object> map = BeanUtil.copyProperties(param, Map.class);
        map.put("isDeleted", ConstantEnum.IS_DELETE_NO.getCode());
        int pageNum = param.getPageNum();
        int pageSize = param.getPageSize();
        PageHelper.startPage(pageNum, pageSize);
        List<SysRoleDto> list = sysRoleBizMapper.selectByCondition(map);
        PageInfo<SysRoleDto> pageInfo = new PageInfo(list);
        int count = Integer.parseInt(String.valueOf(pageInfo.getTotal()));
        if (CollectionUtils.isEmpty(list)) {
            return CommonResponse.success(new PageInfo<>(new ArrayList<>()));
        }
        return CommonResponse.success(pageInfo);
    }

    /**
     * 不分页查询角色数据
     *
     * @param param
     * @return
     */
    @Override
    public List<SysRoleDto> querySysRoleListNoPage(SysRoleVo param) {
        Map<String, Object> map = BeanUtil.copyProperties(param, Map.class);
        map.put("isDeleted", ConstantEnum.IS_DELETE_NO.getCode());
        List<SysRoleDto> list = sysRoleBizMapper.selectByCondition(map);
        return list;
    }


    /**
     * 根据角色编码获取角色信息
     *
     * @param code
     * @return
     */
    @Override
    public SysRole getSysRoleByRoleCode(String code) {
        SysRoleExample example = new SysRoleExample();
        SysRoleExample.Criteria criteria = example.createCriteria();
        criteria.andCodeEqualTo(code);

        List<SysRole> list = sysRoleMapper.selectByExample(example);

        return list.size() > 0 ? list.get(0) : null;
    }

    /**
     * 根据角色id获取角色信息
     *
     * @param id
     * @return
     */
    @Override
    public SysRole getSysRoleByRoleId(Long id) {
        return sysRoleMapper.selectByPrimaryKey(id);
    }


    /**
     * 根据用户id获取关联的角色信息
     *
     * @param userId
     * @return
     */
    @Override
    public List<SysRole> getSysRoleByUserId(Long userId) {
        List<SysUserRole> sysUserRoles = getSysUserRole(userId, 0);
        if (sysUserRoles.size() == 0) {
            return new ArrayList<>();
        }
        List<Long> roleIds = sysUserRoles.stream().map(SysUserRole::getRoleId).collect(Collectors.toList());
        SysRoleExample sysRoleExample = new SysRoleExample();
        SysRoleExample.Criteria criteria = sysRoleExample.createCriteria();
        criteria.andIdIn(roleIds);
        criteria.andIsDeletedEqualTo(0);

        List<SysRole> list = sysRoleMapper.selectByExample(sysRoleExample);
        return list;
    }

    /**
     * 根据用户id或角色id获取用户角色关联关系
     *
     * @param id
     * @param type 0、根据用户id  1、根据角色id
     * @return
     */
    @Override
    public List<SysUserRole> getSysUserRole(Long id, Integer type) {
        SysUserRoleExample example = new SysUserRoleExample();
        SysUserRoleExample.Criteria criteria = example.createCriteria();
        if (type == 0) {
            criteria.andUserIdEqualTo(id);
        } else {
            criteria.andRoleIdEqualTo(id);
        }
        List<SysUserRole> list = sysUserRoleMapper.selectByExample(example);
        return list;
    }




}
