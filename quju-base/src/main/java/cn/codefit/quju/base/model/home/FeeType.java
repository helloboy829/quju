package cn.codefit.quju.base.model.home;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 活动报名支付方式
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FeeType {

    /**
     * name
     */
    private String name;

    /**
     * code
     */
    private int code;

}
