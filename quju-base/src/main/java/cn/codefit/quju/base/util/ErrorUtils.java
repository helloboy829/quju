package cn.codefit.quju.base.util;

import org.springframework.stereotype.Component;

import java.io.PrintWriter;
import java.io.StringWriter;

@Component
public class ErrorUtils {
    public ErrorUtils() {
    }

    public static String getErrorMessage(Exception e) {
        if (null == e) {
            return "";
        } else {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);
            return sw.toString();
        }
    }
}
