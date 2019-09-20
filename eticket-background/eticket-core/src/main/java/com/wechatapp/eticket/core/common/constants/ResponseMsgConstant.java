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
}
