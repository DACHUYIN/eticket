package com.wechatapp.eticket.transaction.service;

import com.wechatapp.eticket.transaction.dto.EticketInfoDTO;
import com.wechatapp.eticket.transaction.dto.TransactionResponseDTO;

import java.math.BigDecimal;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

public interface ITransactionService {
	
	TransactionResponseDTO uploadFile(String wechatOpenId, String ticketType, MultipartFile file);
	
	Map<String, Object> submitEticket(Long userId, EticketInfoDTO eticketInfoDTO);

	BigDecimal getLastPrice(EticketInfoDTO eticketInfoDTO);

	Map<String, Object> buyEticket(String wechatOpenID, EticketInfoDTO eticketInfoDTO);

	Map<String, Object> commitEticket(EticketInfoDTO eticketInfoDTO);

	void insertRockmqTransactionLog(String transactionId, EticketInfoDTO eticketInfoDTO);
}
