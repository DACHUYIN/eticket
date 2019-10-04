package com.wechatapp.eticket.transaction.controller;

import com.wechatapp.eticket.core.annotation.CheckLogin;
import com.wechatapp.eticket.core.common.constants.ResponseMsgConstant;
import com.wechatapp.eticket.core.dto.EticketInfoDTO;
import com.wechatapp.eticket.transaction.dto.TransactionResponseDTO;
import com.wechatapp.eticket.transaction.service.ITransactionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/transaction")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class TransactionController {

	private final ITransactionService transactionService;

	/**
	 * 上传图片至服务器
	 *
	 * @param file
	 * @param file
	 * @return
	 */
	@PostMapping("/uploadFile")
	@CheckLogin
	public TransactionResponseDTO uploadFile(HttpServletRequest request,
			@RequestParam(value = "uploadFile", required = false) MultipartFile file) {
		log.info("开始执行上传图片的方法");
		String wechatOpenId = request.getParameter("wechatOpenId");
		String ticketType = request.getParameter("ticketType");
		log.info("用户：{}开始上传票券类型为：{}的二维码图片", wechatOpenId, ticketType);
		if (null != wechatOpenId && null != ticketType && null != file && StringUtils.isNotEmpty(wechatOpenId)
				&& StringUtils.isNotEmpty(ticketType) && !file.isEmpty()) {
			return transactionService.uploadFile(wechatOpenId, ticketType, file);
		} else {
			log.error("上传图片失败");
			return TransactionResponseDTO.builder()
					.responseCode(ResponseMsgConstant.RESPONSECODE_FAIL_UPLOAD_FILE)
					.responseMsg(ResponseMsgConstant.RESPONSEMSG_FAIL_UPLOAD_FILE)
					.build();
		}
	}

	/**
	 * 提交电子票券
	 *
	 * @param eticketInfoDTO
	 * @return
	 */
	@PostMapping("/submitEticket")
	@CheckLogin
	public TransactionResponseDTO submitEticket(@RequestBody EticketInfoDTO eticketInfoDTO) {
		TransactionResponseDTO dto = transactionService.submitEticket(eticketInfoDTO);
		return dto;
	}

	/**
	 * 购买电子票券
	 * 
	 * @param eticketInfoDTO
	 * @return
	 */
	@PostMapping("/buyEticket")
	public Map<String, Object> buyEticket(EticketInfoDTO eticketInfoDTO) {

		return null;
	}

	/**
	 * 确认电子票券可以使用
	 *
	 * @return
	 */
	@PostMapping("/commitEticket")
	public Map<String, Object> commitEticket(EticketInfoDTO eticketInfoDTO) {

		return null;
	}
}
