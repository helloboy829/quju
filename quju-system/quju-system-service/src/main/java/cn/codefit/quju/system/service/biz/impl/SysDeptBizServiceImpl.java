package cn.codefit.quju.system.service.biz.impl;

import cn.codefit.quju.base.constant.Constant;
import cn.codefit.quju.base.enums.ConstantEnum;
import cn.codefit.quju.base.enums.PlatformEnum;
import cn.codefit.quju.base.util.UserUtils;
import cn.codefit.quju.system.api.dto.SysDeptDto;
import cn.codefit.quju.system.api.vo.SysDeptVo;
import cn.codefit.quju.system.model.dao.SysDeptMapper;
import cn.codefit.quju.system.model.po.SysDept;
import cn.codefit.quju.system.model.po.SysDeptExample;
import cn.codefit.quju.system.service.biz.SysDeptBizService;
import cn.codefit.quju.system.service.biz.dao.SysDeptBizMapper;
import cn.hutool.core.bean.BeanUtil;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 *
 */
@Service
public class SysDeptBizServiceImpl implements SysDeptBizService {
    @Autowired
    private SysDeptMapper sysDeptMapper;
    @Autowired
    private SysDeptBizMapper sysDeptBizMapper;

    /**
     * 根据条件查询部门信息
     *
     * @param param
     * @return
     */
    @Override
    public List<SysDeptDto> querySysDeptList(SysDeptVo param) {
        Map<String, Object> map = BeanUtil.copyProperties(param, Map.class);
        List<SysDeptDto> list = sysDeptBizMapper.selectByCondition(map);
        return list;
    }

    /**
     * 根据id查询部门信息
     *
     * @param id
     * @return
     */
    @Override
    public SysDept selectSysDeptById(Long id) {
        if (id == null) {
            return null;
        }
        return sysDeptMapper.selectByPrimaryKey(id);
    }

    /**
     * 根据id删除部门层级信息
     *
     * @param id
     */
    @Override
    public void deleteSyDeptById(Long id) {
        //查询所有下级部门
        Map<String, Object> map = new HashMap<>();
        map.put("id", id);

        List<SysDeptDto> list = sysDeptBizMapper.selectAllChildByParentId(map);
        //删除子级
        list.forEach(item -> {
            sysDeptMapper.deleteByPrimaryKey(item.getId());
        });
        //删除部门
        sysDeptMapper.deleteByPrimaryKey(id);

    }

    /**
     * 根据名称查询部门信息
     *
     * @param name
     * @return
     */
    @Override
    public SysDept selectSysDeptByName(String name) {
        SysDeptExample example = new SysDeptExample();
        SysDeptExample.Criteria criteria = example.createCriteria();
        criteria.andNameEqualTo(name);

        List<SysDept> list = sysDeptMapper.selectByExample(example);

        return list.size() > 0 ? list.get(0) : null;
    }

    /**
     * 新增一个部门信息
     *
     * @param sysDept
     */
    @Override
    public void save(SysDept sysDept) {
        sysDept.setCreateTime(new Date());
        sysDept.setCreateUser(UserUtils.getUserId().toString());
        sysDept.setCreateUserName(UserUtils.getUsername());
        sysDept.setIsDeleted(ConstantEnum.IS_DELETE_NO.getCode());
        sysDept.setPlatform(PlatformEnum.PLATEFORM_0.getCode());

        // 生成部门树路径
        String treePath = generateDeptTreePath(sysDept);
        sysDept.setTreePath(treePath);

        sysDeptMapper.insert(sysDept);
    }

    /**
     * 更新部门信息
     *
     * @param sysDept
     */
    @Override
    public void updateSysDept(SysDept sysDept) {
        // 生成部门树路径
        String treePath = generateDeptTreePath(sysDept);
        sysDept.setTreePath(treePath);

        sysDept.setModifyTime(new Date());
        sysDept.setModifyUser(UserUtils.getUserId().toString());
        sysDept.setModifyUserName(UserUtils.getUsername());
        sysDeptMapper.updateByPrimaryKeySelective(sysDept);
    }

    /**
     * 生成部门路径
     *
     * @param dept
     * @return
     */
    private String generateDeptTreePath(SysDept dept) {
        Long parentId = dept.getParentId();
        String treePath;
        if (parentId.equals(Constant.ROOT_DEPT_ID)) {
            treePath = String.valueOf(Constant.ROOT_DEPT_ID) + ",";
        } else {
            SysDept parentDept = this.selectSysDeptById(parentId);
            treePath = Optional.ofNullable(parentDept).map(item -> item.getTreePath() + item.getId() + ",").orElse(Strings.EMPTY);
        }
        return treePath;
    }

    /**
     * 判断部门名称不能重复
     *
     * @param name   部门名称
     * @param deptId 菜单id
     * @return
     */
    @Override
    public boolean checkSysDeptName(String name, Long deptId) {
        Map<String, Object> map = new HashMap<>();
        map.put("name", name);
        if (deptId != null) {
            map.put("noId", deptId);
        }
        int number = sysDeptBizMapper.checkSysDeptName(map);
        return number > 0 ? true : false;
    }
}
