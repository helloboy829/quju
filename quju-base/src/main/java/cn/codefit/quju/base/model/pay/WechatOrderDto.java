package cn.codefit.quju.base.model.pay;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class WechatOrderDto {

    @JsonProperty("xml")
    private XmlDTO xml;

    @NoArgsConstructor
    @Data
    public static class XmlDTO {
        @JsonProperty("transaction_id")
        private String transactionId;
        @JsonProperty("nonce_str")
        private String nonceStr;
        @JsonProperty("trade_state")
        private String tradeState;
        @JsonProperty("bank_type")
        private String bankType;
        @JsonProperty("openid")
        private String openid;
        @JsonProperty("sign")
        private String sign;
        @JsonProperty("return_msg")
        private String returnMsg;
        @JsonProperty("fee_type")
        private String feeType;
        @JsonProperty("mch_id")
        private String mchId;
        @JsonProperty("cash_fee")
        private String cashFee;
        @JsonProperty("device_info")
        private String deviceInfo;
        @JsonProperty("out_trade_no")
        private String outTradeNo;
        @JsonProperty("cash_fee_type")
        private String cashFeeType;
        @JsonProperty("appid")
        private String appid;
        @JsonProperty("total_fee")
        private String totalFee;
        @JsonProperty("trade_state_desc")
        private String tradeStateDesc;
        @JsonProperty("trade_type")
        private String tradeType;
        @JsonProperty("result_code")
        private String resultCode;
        @JsonProperty("attach")
        private String attach;
        @JsonProperty("time_end")
        private String timeEnd;
        @JsonProperty("is_subscribe")
        private String isSubscribe;
        @JsonProperty("return_code")
        private String returnCode;
    }
}
