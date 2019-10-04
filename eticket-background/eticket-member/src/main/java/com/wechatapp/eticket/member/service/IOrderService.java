package com.wechatapp.eticket.member.service;

import com.wechatapp.eticket.core.dto.EticketInfoDTO;

public interface IOrderService {

	void insertEticketInfo(EticketInfoDTO eticketInfo);

	void updateEticketInfo(EticketInfoDTO eticketInfo);
}
