package cn.codefit.quju.base.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 通用下拉列表选择项
 */
@Data
@NoArgsConstructor
public class DropOption<T> {

    private String label;
    private T value;
    @JsonInclude(value = JsonInclude.Include.NON_EMPTY)
    private List<DropOption> children;

    public DropOption(T value, String label) {
        this.value = value;
        this.label = label;
    }


    public DropOption(T value, String label, List<DropOption> children) {
        this.value = value;
        this.label = label;
        this.children = children;
    }


}
