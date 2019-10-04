package com.wechatapp.eticket.core.common.util;

import java.util.UUID;

import org.apache.commons.lang3.StringUtils;

/**
   * 分库工具
 * @author virgo.zx
 *  
 */
public class ShardingUtils {

	private ShardingUtils() {
	}

	/**
	    * 生成普通主键
	 * 
	 * @return
	 */
	public static String generateId() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}

	/**
	 * 生成分库主键
	 * 
	 * @param shardingHint
	 * @return
	 */
	public static String generateShardingId(Comparable<?> shardingHint) {
		return generateId() + shardingHint;
	}

	/**
	 * 生成分库主键
	 * 
	 * @param numberString
	 * @return
	 */
	public static String generateShardingId(String numberString) {
		String shardingHint = lastCharactorOf(numberString);
		return generateShardingId(Integer.valueOf(shardingHint));
	}

	/**
	 * 获取分库主键中的分库路由
	 * 
	 * @param shardingId
	 * @return
	 */
	public static String getShardingHint(String shardingId) {
		return lastCharactorOf(shardingId);
	}

	/**
	 * 获取字符串的最后一个字符
	 * 
	 * @param s
	 * @return
	 */
	public static String lastCharactorOf(String s) {
		return StringUtils.substring(s, StringUtils.length(s) - 1);
	}
}
