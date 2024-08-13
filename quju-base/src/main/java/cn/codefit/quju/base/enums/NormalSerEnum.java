package cn.codefit.quju.base.enums;


public enum NormalSerEnum {
    All(0, "全部"),
    Hot(1, "热门"),
    New(2, "新活动");

    private Integer value;
    private String description;


    NormalSerEnum(Integer value, String description) {
        this.value = value;
        this.description = description;

    }

    public Integer getValue() {
        return value;
    }

    public String getDescription() {
        return description;
    }


    public static NormalSerEnum getByValue(Integer value) {
        switch (value) {
            case 0:
                return All;
            case 1:
                return Hot;
            case 2:
                return New;

            default:
                throw new RuntimeException("date way not found");

        }
    }
}
