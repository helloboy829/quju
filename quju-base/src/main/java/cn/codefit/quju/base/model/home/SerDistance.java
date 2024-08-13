package cn.codefit.quju.base.model.home;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SerDistance {

    /**
     * 距离名称
     */
    private String distanceName;

    /**
     * 距离code
     */
    private int distanceCode;

}
