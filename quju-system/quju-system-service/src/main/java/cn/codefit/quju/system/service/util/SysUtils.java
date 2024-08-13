package cn.codefit.quju.system.service.util;

import cn.codefit.quju.base.model.DropOption;
import cn.codefit.quju.system.api.dto.SysDeptDto;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

public class SysUtils {


    /**
     * 递归生成部门结构-前端默认展示所有
     *
     * @return
     */
    public static List<SysDeptDto> generateRecurDepts(List<SysDeptDto> sysDeptDtos) {
        List<SysDeptDto> list = new ArrayList<>();
        List<Long> ids = sysDeptDtos.stream().map(SysDeptDto::getId).distinct().collect(Collectors.toList());
        List<Long> parentIds = sysDeptDtos.stream().map(SysDeptDto::getParentId).distinct().collect(Collectors.toList());
        //差集 -- 表示顶级节点
        List<Long> diffIds = parentIds.stream().filter(itemValue -> !ids.contains(itemValue)).collect(toList());
        for (int i = 0; i < diffIds.size(); i++) {
            list.addAll(recurDepts(diffIds.get(i), sysDeptDtos));
        }
        return list;
    }

    /**
     * 递归生成-部门下所有直接子节点数据
     *
     * @param parentId 父节点
     * @param deptList 部门数据
     * @return
     */
    public static List<SysDeptDto> recurDepts(Long parentId, List<SysDeptDto> deptList) {
        List<SysDeptDto> list = new ArrayList<>();
        if (!Objects.isNull(deptList) && deptList.size() > 0) {
            deptList.stream().filter(item -> item.getParentId().equals(parentId)).forEach(sysDeptDto -> {
                //设置子节点
                sysDeptDto.setChildren(recurDepts(sysDeptDto.getId(), deptList));
                list.add(sysDeptDto);
            });
        }
        return list;
    }



    /**
     * 递归生成部门下拉层级列表
     *
     * @param parentId    父级ID
     * @param sysDeptDtos 部门列表
     * @return
     */
    public static List<DropOption> recurDropDepts(Long parentId, List<SysDeptDto> sysDeptDtos) {
        List<DropOption> menus = Optional.ofNullable(sysDeptDtos).orElse(new ArrayList<>()).stream()
                .filter(sysDeptDto -> sysDeptDto.getParentId().equals(parentId))
                .map(sysDeptDto -> new DropOption(sysDeptDto.getId(), sysDeptDto.getName(), recurDropDepts(sysDeptDto.getId(), sysDeptDtos)))
                .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
        return menus;
    }


}
