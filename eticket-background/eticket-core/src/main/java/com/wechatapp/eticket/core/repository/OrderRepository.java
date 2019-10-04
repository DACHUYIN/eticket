package com.wechatapp.eticket.core.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.wechatapp.eticket.core.entity.OrderEntity;


@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, String> {

	@Query(" select o.orderId from OrderEntity o where o.shardingId = ?1 ")
	Long getOrderId(String shardingId);

	@Query(" select o from OrderEntity o where o.redisMapKey = ?1 and o.wechatOpenIdSeller = ?2 ")
	Optional<OrderEntity> getExistSellOrder(String redisMapKey, String wechatOpenIdSeller);

	@Query(" select o from OrderEntity o where o.redisMapKey = ?1 and o.wechatOpenIdSeller = ?2 ")
	Optional<OrderEntity> getExistBuyerOrder(String redisMapKey, String wechatOpenIdSeller);
}
