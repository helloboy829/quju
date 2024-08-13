package cn.codefit.quju.base.model.pay;

import java.io.Serializable;
import java.util.Map;

public class RechargeVo implements Serializable {

    private Map<String, String[]> requestParams;
    private Map<String, String> map;
    private String notityXml;

    public Map<String, String[]> getRequestParams() {
        return requestParams;
    }

    public void setRequestParams(Map<String, String[]> requestParams) {
        this.requestParams = requestParams;
    }

    public Map<String, String> getMap() {
        return map;
    }

    public void setMap(Map<String, String> map) {
        this.map = map;
    }

    public String getNotityXml() {
        return notityXml;
    }

    public void setNotityXml(String notityXml) {
        this.notityXml = notityXml;
    }
}
