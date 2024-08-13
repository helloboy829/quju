package cn.codefit.quju.system.service.service.impl;

import cn.codefit.quju.base.constant.Constant;
import cn.codefit.quju.base.enums.ResponseCode;
import cn.codefit.quju.base.model.DropOption;
import cn.codefit.quju.base.rpc.CommonResponse;
import cn.codefit.quju.system.api.dto.SysDeptDto;
import cn.codefit.quju.system.api.service.SysDeptService;
import cn.codefit.quju.system.api.vo.SysDeptVo;
import cn.codefit.quju.system.model.po.SysDept;
import cn.codefit.quju.system.service.biz.SysDeptBizService;
import cn.codefit.quju.system.service.util.SysUtils;
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

import java.util.List;

/**
 * 部门业务接口
 * 列表不分页查询、新增部门、删除部门、编辑部门
 */
@DubboService(version = Constant.VERSION, interfaceClass = SysDeptService.class)
@RequestMapping(value = "/dept")
@RestController
@Slf4j
public class SysDeptServiceImpl implements SysDeptService {

    @Autowired
    private SysDeptBizService sysDeptBizService;

    /**
     * 部门列表查询
     *
     * @return
     */
    @Override
    @RequestMapping(value = "/querySysDeptList")
    public CommonResponse<List<SysDeptDto>> querySysDeptList(@RequestBody SysDeptVo param) {
        List<SysDeptDto> list = sysDeptBizService.querySysDeptList(param);
        return CommonResponse.success(SysUtils.generateRecurDepts(list));
    }


    /**
     * 新增或修改部门
     *
     * @param param
     * @return
     */
    @Override
    @PostMapping(value = "/addOrUpdateDept")
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public CommonResponse<Boolean> saveDept(@RequestBody SysDeptVo param) {
        if (param == null) {
            return CommonResponse.error(ResponseCode.ILLEGAL_ARGUMENT);
        }
        try {
            Long id = param.getId();
            SysDept sysDept = BeanUtil.copyProperties(param, SysDept.class);
            //1、 判断部门名称不能重复
            boolean result = sysDeptBizService.checkSysDeptName(sysDept.getName(), id);
            if (result) {
                return CommonResponse.error(ResponseCode.MORE_MENU);
            }
            if (id == null) {
                sysDeptBizService.save(sysDept);
            } else {
                sysDeptBizService.updateSysDept(sysDept);
            }
            return CommonResponse.success(true);
        } catch (Exception e) {
            log.error(ExceptionUtils.getStackTrace(e));
            return CommonResponse.error(ResponseCode.FAIL_COMMON);
        }
    }


    /**
     * 删除部门
     *
     * @param id 部门ID
     * @return
     */
    @Override
    @RequestMapping(value = "/deleteSysDeptById", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public CommonResponse<Boolean> deleteByIds(@RequestParam Long id) {
        if (id == null) {
            return CommonResponse.error(ResponseCode.ILLEGAL_ARGUMENT);
        }
        SysDept sysDept = sysDeptBizService.selectSysDeptById(id);
        if (sysDept == null) {
            return CommonResponse.error(ResponseCode.HAS_NO_DATA);
        }
        sysDeptBizService.deleteSyDeptById(id);
        return CommonResponse.success(true);
    }


    /**
     * 新增部门时获取下拉部门树
     *
     * @return
     */
    @Override
    @RequestMapping(value = "/lisetDeptOptions", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public CommonResponse<List<DropOption>> lisetDeptOptions() {
        SysDeptVo param = new SysDeptVo();
        param.setStatus(Constant.STATUS_ENABLE);
        List<SysDeptDto> list = sysDeptBizService.querySysDeptList(param);
        return CommonResponse.success(SysUtils.recurDropDepts(Constant.ROOT_DEPT_ID, list));
    }


}
