package com.wechatapp.eticket.member.log;

import java.util.Date;

import com.wechatapp.eticket.core.common.util.DateFormatUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * 日志平台收集的日志单独打印
 * @author virgo.zx
 *
 */
@Slf4j
public class LogPlatformLogger {

	private LogPlatformLogger() {
	}

	public static void info(String msg) {
		log.info(msg);
	}

	public static void info(String format, Object... arguments) {
		log.info(format, arguments);
	}

	public static String parseDateString(Date date) {
		if (date == null) {
			return null;
		}
		return DateFormatUtils.YYYY_MM_DD.get().format(date);
	}
}
