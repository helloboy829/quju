package cn.codefit.quju.base.model.home;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NormalSer {

    /**
     * 一般搜索名称
     */
    private String normalName;

    /**
     * code
     */
    private int normalCode;

}
