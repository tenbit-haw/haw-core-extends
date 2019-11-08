package cn.tenbit.haw.core.extend.util;

import cn.tenbit.haw.core.constant.HawConsts;
import cn.tenbit.haw.core.util.HawStrings;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

/**
 * @author bangquan.qian
 * @date 2019-10-31 18:54
 */
public class HawTimes {

    private static final DateTimeFormatter FORMATTER = DateTimeFormat.forPattern(HawConsts.TIME_FORMAT_PATTERN);

    public static String formatCurrentTimeMillis() {
        return FORMATTER.print(currentTimeMillis());
    }

    public static long currentTimeMillis() {
        return System.currentTimeMillis();
    }

    public static String currentTimeMillisString() {
        return HawStrings.toNotNullString(currentTimeMillis());
    }

}
