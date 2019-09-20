package com.wechatapp.eticket.core.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.wechatapp.eticket.core.entity.OrderEntity;


@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, String> {

	@Query(" select o from OrderEntity o where o.telephoneNumber = ?1 ")
	List<OrderEntity> getOrderInfo(String telephoneNumber);
}
