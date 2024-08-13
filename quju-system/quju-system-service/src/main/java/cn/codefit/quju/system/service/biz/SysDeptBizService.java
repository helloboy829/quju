package cn.codefit.quju.system.service.biz;

import cn.codefit.quju.system.api.dto.SysDeptDto;
import cn.codefit.quju.system.api.vo.SysDeptVo;
import cn.codefit.quju.system.model.po.SysDept;

import java.util.List;

/**
 *
 */
public interface SysDeptBizService {

    /**
     * 根据条件查询部门信息
     *
     * @param param
     * @return
     */
    List<SysDeptDto> querySysDeptList(SysDeptVo param);

    /**
     * 根据id查询部门信息
     *
     * @param id
     * @return
     */
    SysDept selectSysDeptById(Long id);

    /**
     * 根据id删除部门层级信息
     *
     * @param id
     */
    void deleteSyDeptById(Long id);


    /**
     * 根据名称查询部门信息
     *
     * @param name
     * @return
     */
    SysDept selectSysDeptByName(String name);

    /**
     * 新增一个部门信息
     *
     * @param sysDept
     */
    void save(SysDept sysDept);

    /**
     * 更新部门信息
     *
     * @param sysDictionary
     */
    void updateSysDept(SysDept sysDictionary);

    /**
     * 判断部门名称不能重复
     *
     * @param name   部门名称
     * @param deptId 菜单id
     * @return
     */
    boolean checkSysDeptName(String name, Long deptId);
}
