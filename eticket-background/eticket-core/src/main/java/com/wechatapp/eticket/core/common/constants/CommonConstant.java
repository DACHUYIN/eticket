package com.wechatapp.eticket.core.common.constants;

public class CommonConstant {

	private CommonConstant() {	
	}
	
	// 表明不是黑名单成员
	public static final String NOT_BLACKMEMBER = "0";
			
	// 表明是黑名单成员
	public static final String IS_BLACKMEMBER = "1";
	
	// PNG格式的图片
	public static final String IMG_PNG = "PNG";
	
	// JPG格式的图片
	public static final String IMG_JPG = "JPG";
	
	// JPEG格式的图片
	public static final String IMG_JPEG = "JPEG";

	//  图片上传的服务器地址
	public static final String IMG_UPLOAD_ADDRESS = "/eticket-img/";

	// 卖家wechatOpenId
	public static final String WECHAT_OPENID_SELLER = "Seller:";

	// 买家wechatOpenId
	public static final String WECHAT_OPENID_BUYER = "Buyer:";

	// SQL方法：插入
	public static final int SQL_METHOD_INSERT = 1;

	// SQL方法：更新
	public static final int SQL_METHOD_UPDATE = 2;

	// 卖方
	public static final String STR_SELLER = "卖方";

	// 买方
	public static final String STR_BUYER = "买方";
}
