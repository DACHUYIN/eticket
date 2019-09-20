package com.wechatapp.eticket.core.common.constants;

public class JwtConstant {

	private JwtConstant() {	
	}
	
    /**
     * 秘钥
     * 默认秘钥为：aaabbbcccdddeeefffggghhhiiijjjkkklllmmmnnnooopppqqqrrrsssttt
     */
    public static final String SECRET_KEY = "aaabbbcccdddeeefffggghhhiiijjjkkklllmmmnnnooopppqqqrrrsssttt";
    /**
     * 有效期，单位秒
     * - 默认2周
     */
    public static final Long EXPIRATION_TIME_IN_SECOND = new Long(1209600);
}
