package com.wechatapp.eticket.core.common.util;

import lombok.extern.slf4j.Slf4j;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

/**
 * 计算两个日期的时间差
 *
 * @author virgo.zx
 * @date 2019/9/23 21:18
 */
@Slf4j
public class CalculateTimeDiffUtils {

    private CalculateTimeDiffUtils() {
    }

    /**
     * 计算两个时间的天数差
     *
     * @param startDate
     * @param endDate
     * @return
     */
    public static Integer calculateDayDiff(String startDate, String endDate) {
        try {
            Date start = DateFormatUtils.YYYY_MM_DD.get().parse(startDate);
            Date end = DateFormatUtils.YYYY_MM_DD.get().parse(endDate);
            Calendar cal1 = Calendar.getInstance();
            cal1.setTime(start);
            Calendar cal2 = Calendar.getInstance();
            cal2.setTime(end);
            int day1 = cal1.get(Calendar.DAY_OF_YEAR);
            int day2 = cal2.get(Calendar.DAY_OF_YEAR);
            int year1 = cal1.get(Calendar.YEAR);
            int year2 = cal2.get(Calendar.YEAR);
            // 不是同一年的情况
            if (year1 != year2)
            {
                int timeDistance = 0;
                for (int i = year1; i < year2; i++) {
                    // 判断是否为闰年
                    if (i % 4 == 0 && i % 100 != 0 || i % 400 == 0)
                    {
                        timeDistance += 366;
                    } else
                    {
                        timeDistance += 365;
                    }
                }
                return timeDistance + (day2 - day1);
            } else {
                // 同一年的话
                return day2 - day1;
            }
        } catch (ParseException e) {
            log.error(e.toString());
            return 0;
        }
    }
}
