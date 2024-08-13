package cn.codefit.quju.base.model.home;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 小程序城市表
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SerDate {

    /**
     * 日期名称
     */
    private String dateName;

    /**
     * 日期code
     */
    private int dateCode;

}
