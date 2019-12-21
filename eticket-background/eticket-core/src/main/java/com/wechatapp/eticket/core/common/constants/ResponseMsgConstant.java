package com.wechatapp.eticket.core.common.constants;

public class ResponseMsgConstant {

	private ResponseMsgConstant() {
	}

	/**
	 * 成功代码
	 */
	public static final String RESPONSECODE_SUCCESS_LOGIN = "100";
	
	/**
	 * 成功代码
	 */
	public static final String RESPONSEMSG_SUCCESS_LOGIN = "登录成功";
	
	/**
	 * 失败代码
	 */
	public static final String RESPONSECODE_FAIL_LOGIN_WXERROR = "101";
	
	/**
	 * 失败代码
	 */
	public static final String RESPONSEMSG_FAIL_LOGIN_WXERROR = "微信服务器异常";
	
	/**
	 * 失败代码
	 */
	public static final String RESPONSECODE_FAIL_LOGIN_ERROR = "102";
	
	/**
	 * 失败代码
	 */
	public static final String RESPONSEMSG_FAIL_LOGIN_ERROR = "系统异常";
	
	/**
	 * 失败代码
	 */
	public static final String RESPONSECODE_FAIL_LOGIN_REDISERROR = "103";

	/**
	 * 获取图片信息失败代码
	 */
	public static final String RESPONSECODE_SUCCESS_UPLOAD_FILE = "200";

	/**
	 * 获取图片信息失败
	 */
	public static final String RESPONSEMSG_SUCCESS_UPLOAD_FILE= "图片上传成功";


	/**
	 * 获取图片信息失败代码
	 */
	public static final String RESPONSECODE_FAIL_UPLOAD_FILE = "201";
	
	/**
	 * 获取图片信息失败
	 */
	public static final String RESPONSEMSG_FAIL_UPLOAD_FILE= "系统异常";
	
	/**
	 * 图片格式错误代码
	 */
	public static final String RESPONSECODE_FAIL_FILETYPE_ERROR = "202";
	
	/**
	 * 图片格式错误
	 */
	public static final String RESPONSEMSG_FAIL_FILETYPE_ERROR = "图片格式错误";

	/**
	 * 确认电子券码成功代码
	 */
	public static final String RESPONSECODE_SUCCESS_SUBMIT_ETICKET = "300";

	/**
	 * 确认电子券码成功消息
	 */
	public static final String RESPONSEMSG_SUCCESS_SUBMIT_ETICKET= "电子券发布成功";

	/**
	 * 确认电子券码失败代码
	 */
	public static final String RESPONSECODE_FAIL_SUBMIT_ETICKET = "301";

	/**
	 * 确认电子券码失败消息
	 */
	public static final String RESPONSEMSG_FAIL_SUBMIT_ETICKET = "电子券发布失败";

	/**
	 * 电子券码价格异常代码
	 */
	public static final String RESPONSECODE_ERROR_TOTALPRICE = "302";

	/**
	 * 电子券码价格异常消息
	 */
	public static final String RESPONSEMSG_ERROR_TOTALPRICE = "券码价格异常";

	/**
	 * 电子券码有效期异常代码
	 */
	public static final String RESPONSECODE_ERROR_TERMVALIDITY = "303";

	/**
	 * 电子券码有效期异常消息
	 */
	public static final String RESPONSEMSG_ERROR_TERMVALIDITY = "券码有效期异常";

	/**
	 *
	 */
	public static final String RESPONSECODE_SUCCESS_IDEMPOTENTTOKEN = "400";

	/**
	 *
	 */
	public static final String RESPONSEMSG_SUCCESS_IDEMPOTENTTOKEN = "幂等性token创建成功";

	/**
	 *
	 */
	public static final String RESPONSECODE_ERROR_IDEMPOTENTTOKEN = "401";

	/**
	 *
	 */
	public static final String RESPONSEMSG_ERROR_IDEMPOTENTTOKEN = "幂等性token创建失败";
}
