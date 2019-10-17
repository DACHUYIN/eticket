package com.wechatapp.eticket.core.repository;

import com.wechatapp.eticket.core.entity.FilmTicketEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author virgo.zx
 * @date 2019/10/6 20:55
 */
public interface FilmTicketRepository extends JpaRepository<FilmTicketEntity, String> {
}
