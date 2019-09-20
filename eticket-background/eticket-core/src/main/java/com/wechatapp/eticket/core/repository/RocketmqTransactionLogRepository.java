package com.wechatapp.eticket.core.repository;

import com.wechatapp.eticket.core.entity.RocketmqTransactionLogEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * @author virgo.zx
 * @date 2019/8/18 22:38
 */
@Repository
public interface RocketmqTransactionLogRepository extends JpaRepository<RocketmqTransactionLogEntity, String> {

      @Query(" select r from RocketmqTransactionLogEntity r where r.transactionId = ?1 ")
      RocketmqTransactionLogEntity getRocketmqTransactionLog(String transactionId);
}
