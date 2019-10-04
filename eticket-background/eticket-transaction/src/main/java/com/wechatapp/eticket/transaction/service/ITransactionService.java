package com.wechatapp.eticket.transaction.service;

import com.wechatapp.eticket.core.dto.EticketInfoDTO;
import com.wechatapp.eticket.transaction.dto.TransactionResponseDTO;

import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

public interface ITransactionService {
	
	TransactionResponseDTO uploadFile(String wechatOpenId, String ticketType, MultipartFile file);

	TransactionResponseDTO submitEticket(EticketInfoDTO eticketInfoDTO);

	Map<String, Object> buyEticket(String wechatOpenID, EticketInfoDTO eticketInfoDTO);

}
