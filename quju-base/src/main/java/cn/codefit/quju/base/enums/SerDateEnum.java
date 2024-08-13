package cn.codefit.quju.base.enums;


public enum SerDateEnum {
    AllDate(0, "全部"),
    ToDay(1, "今天"),
    Tomorrow(2, "明天"),
    Week(3, "本周"),
    Weekend(4, "本周末"),
    Month(5, "本月");


    private Integer value;
    private String description;


    SerDateEnum(Integer value, String description) {
        this.value = value;
        this.description = description;

    }

    public static SerDateEnum getByValue(Integer value) {
        switch (value) {
            case 0:
                return AllDate;
            case 1:
                return ToDay;
            case 2:
                return Tomorrow;
            case 3:
                return Week;
            case 4:
                return Weekend;
            case 5:
                return Month;

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
