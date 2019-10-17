package com.wechatapp.eticket.core.repository;

import com.wechatapp.eticket.core.entity.ConcertTicektEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author virgo.zx
 * @date 2019/10/6 20:56
 */
public interface ConcertTicketRepository extends JpaRepository<ConcertTicektEntity, String> {
}
