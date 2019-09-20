package com.wechatapp.eticket.member.service;

import java.util.List;

import com.wechatapp.eticket.core.entity.OrderEntity;

public interface IOrderService {

	List<OrderEntity> getOrderInfo(String telephoneNumber);
}
