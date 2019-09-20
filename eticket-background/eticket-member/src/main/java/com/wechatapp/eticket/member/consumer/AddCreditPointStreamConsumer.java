package com.wechatapp.eticket.member.consumer;

import com.wechatapp.eticket.core.dto.MemberShipDTO;
import com.wechatapp.eticket.member.service.IMemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.stereotype.Service;

/**
 * @author virgo.zx
 * @date 2019/8/18 16:44
 */
@Service
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AddCreditPointStreamConsumer {

    private final IMemberService memberService;

    @StreamListener(Sink.INPUT)
    public void receive(MemberShipDTO message) {
    	log.info("接收生产者生产的消息给消费者使用，改消息为：" + message);
        this.memberService.addMemberCreditPoint(message);
    }

}
