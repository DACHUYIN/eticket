package com.wechatapp.eticket.core.common.util;

import java.text.SimpleDateFormat;

/**
 * 时间格式化工具
 * @author MSI
 *
 */
public class DateFormatUtils {

	private DateFormatUtils() {
	}

	public static final ThreadLocal<SimpleDateFormat> YYYY_MM_DD_HH_MM_SS = ThreadLocal
			.withInitial(() -> new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));

	public static final ThreadLocal<SimpleDateFormat> YYYY_MM_DD = ThreadLocal
			.withInitial(() -> new SimpleDateFormat("yyyy-MM-dd"));

	public static final ThreadLocal<SimpleDateFormat> YYYYMMDD = ThreadLocal
			.withInitial(() -> new SimpleDateFormat("yyyyMMdd"));

	public static final ThreadLocal<SimpleDateFormat> YYYYMMDDHHMMSS = ThreadLocal
			.withInitial(() -> new SimpleDateFormat("yyyyMMddHHmmss"));

	public static final ThreadLocal<SimpleDateFormat> YYYYMMDDHHMMSSSSS = ThreadLocal
			.withInitial(() -> new SimpleDateFormat("yyyyMMddHHmmssSSS"));
}
