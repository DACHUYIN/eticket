package com.wechatapp.eticket.core.repository;

import com.wechatapp.eticket.core.entity.FoodTicketEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

/**
 * 餐饮类
 *
 * @author virgo.zx
 * @date 2019/9/28 23:02
 */
public interface FoodTicketRepository extends JpaRepository<FoodTicketEntity, String> {

    @Query(" select o from FoodTicketEntity o where o.orderId = ?1 ")
    Optional<FoodTicketEntity> getExistFoodTikcet(Long orderId);

}
