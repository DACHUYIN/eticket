package com.wechatapp.eticket.core.entity;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author virgo.zx
 * @date 2019/8/18 22:29
 */
@Data
@Entity
@Table(name = "rocketmq_transaction_log")
@EqualsAndHashCode(callSuper = false)
@Builder
public class RocketmqTransactionLogEntity extends BaseEntity {

    // 主键
    @Id
    @Column(name = "log_id")
    private Long logId;

    // 事务ID
    @Column(name = "transaction_Id")
    private String transactionId;

    // 日志信息
    @Column(name = "log")
    private String log;
}
