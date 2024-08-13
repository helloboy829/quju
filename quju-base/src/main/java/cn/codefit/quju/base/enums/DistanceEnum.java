package cn.codefit.quju.base.enums;


public enum DistanceEnum {
    All(-1, "全城"),
    NearBy(0, "附近"),
    KM3(1, "3KM"),
    KM6(2, "6KM"),
    KM10(3, "10KM");

    private Integer value;
    private String description;

    DistanceEnum(Integer value, String description) {
        this.value = value;
        this.description = description;

    }

    public static DistanceEnum getByValue(Integer value) {
        switch (value) {
            case 0:
                return NearBy;
            case 1:
                return KM3;
            case 2:
                return KM6;
            case 3:
                return KM10;

            default:
                throw new RuntimeException("date way not found");

        }
    }

    public Integer getValue() {
        return value;
    }

    public String getDescription() {
        return description;
    }
}
