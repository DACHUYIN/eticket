package com.wechatapp.eticket.core.repository;

import com.wechatapp.eticket.core.entity.PaymentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * @author virgo.zx
 * @date 2019/8/19 20:45
 */
@Repository
public interface PaymentRepository extends JpaRepository<PaymentEntity, String> {

    @Query(" select p.paymentStatus from PaymentEntity p where p.paymentId = ?1 ")
    String getPaymentStatus(Long paymentId);

}
