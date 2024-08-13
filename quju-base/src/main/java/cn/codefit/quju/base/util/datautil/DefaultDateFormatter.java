package cn.codefit.quju.base.util.datautil;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DefaultDateFormatter implements DateFormatter {
    private final Pattern pattern;
    private final String format;
    private final boolean containTime;

    DefaultDateFormatter(Pattern pattern, String format, boolean containTime) {
        this.pattern = pattern;
        this.format = format;
        this.containTime = containTime;
    }

    @Override
    public boolean support(String dateString) {
        Matcher matcher = pattern.matcher(dateString);
        return matcher.matches();
    }

    @Override
    public Date format(String dateString) throws ParseException {
        return new SimpleDateFormat(format).parse(dateString);
    }

    @Override
    public DateFormat getDateFormat() {
        return new SimpleDateFormat(format);
    }

    @Override
    public String getFormat() {
        return format;
    }

    @Override
    public boolean isContainTime() {
        return containTime;
    }
}
