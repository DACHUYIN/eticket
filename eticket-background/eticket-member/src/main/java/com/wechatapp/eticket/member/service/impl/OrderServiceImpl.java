package com.wechatapp.eticket.member.service.impl;

import com.wechatapp.eticket.core.entity.OrderEntity;
import com.wechatapp.eticket.core.repository.OrderRepository;
import com.wechatapp.eticket.member.service.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class OrderServiceImpl implements IOrderService {

	@Autowired
	private OrderRepository orderRepository;
	
	@Override
	public List<OrderEntity> getOrderInfo(String telephoneNumber) {
		return orderRepository.getOrderInfo(telephoneNumber);
	}

}
