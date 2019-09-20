package com.wechatapp.eticket.transaction.service.impl;

import com.wechatapp.eticket.core.common.constants.CommonConstant;
import com.wechatapp.eticket.core.common.constants.PriceConstant;
import com.wechatapp.eticket.core.common.constants.ResponseMsgConstant;
import com.wechatapp.eticket.core.entity.RocketmqTransactionLogEntity;
import com.wechatapp.eticket.core.enums.ServiceChargeCategoryEnum;
import com.wechatapp.eticket.core.repository.RocketmqTransactionLogRepository;
import com.wechatapp.eticket.transaction.context.ServiceChargeContext;
import com.wechatapp.eticket.transaction.dto.EticketInfoDTO;
import com.wechatapp.eticket.transaction.dto.TransactionResponseDTO;
import com.wechatapp.eticket.transaction.producer.AddCreditPointProducer;
import com.wechatapp.eticket.transaction.service.ITransactionService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TransactionServiceImpl implements ITransactionService {

	private final ServiceChargeContext serviceChargeContext;

	private final RocketmqTransactionLogRepository rocketmqTransactionLogRepository;

	private final AddCreditPointProducer addCreditPointProducer;

	/**
	 * 上传图片
	 * 
	 */
	@Override
	public TransactionResponseDTO uploadFile(String wechatOpenId, String ticketType, MultipartFile file) {
		try {
			log.info("成功获取图片，并开始执行上传任务");
			String fileName = file.getOriginalFilename();
			String path = null;
			String type = null;
			type = fileName.indexOf(".") != -1 ? fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length())
					: null;
			log.info("图片初始名称为：{},类型为：{}", fileName, type);
			if (CommonConstant.IMG_PNG.equals(type.toUpperCase()) || CommonConstant.IMG_JPG.equals(type.toUpperCase())
					|| CommonConstant.IMG_JPEG.equals(type.toUpperCase())) {
				String trueFileName = String.valueOf(System.currentTimeMillis()) + "-" + fileName;
				path = "D:\\" + wechatOpenId + "\\" + ticketType + "\\";
				File fileDir = new File(path);
				if (!fileDir.exists())
					fileDir.mkdirs(); 
				log.info("图片存放的路径为：" + path);
				file.transferTo(new File(path + fileName));
			} else {
				log.info("图片格式不符合要求");
				return TransactionResponseDTO.builder()
						.responseCode(ResponseMsgConstant.RESPONSECODE_FAIL_FILETYPE_ERROR)
						.responseMsg(ResponseMsgConstant.RESPONSEMSG_FAIL_FILETYPE_ERROR).build();
			}
		} catch (IOException e) {
			log.error(e.toString());
		}

		return null;
	}

	/**
	 * 提交电子票券进入市场
	 *
	 * @return
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public Map<String, Object> submitEticket(Long userId, EticketInfoDTO eticketInfoDTO) {
		log.info("开始提交券码至市场中");

		log.info("券码提交结束，相关数据已存储到redis和mysql中");
		return null;
	}

	/**
	 * 计算券码的最终价格
	 *
	 * @param eticketInfoDTO
	 * @return
	 */
	@Override
	public BigDecimal getLastPrice(EticketInfoDTO eticketInfoDTO) {
		log.info("计算券码价格");
		BigDecimal price = eticketInfoDTO.getPrice();
		ServiceChargeCategoryEnum erviceChargeCategoryEnum = price.compareTo(PriceConstant.PRICE_TEN) <= 0
				? ServiceChargeCategoryEnum.UNDERTEN
				: price.compareTo(PriceConstant.PRICE_FIFTY) <= 0 && price.compareTo(PriceConstant.PRICE_TEN) > 0
						? ServiceChargeCategoryEnum.UNDFIFTY
						: price.compareTo(PriceConstant.PRICE_HUNDRED) <= 0
								&& price.compareTo(PriceConstant.PRICE_FIFTY) > 0
										? ServiceChargeCategoryEnum.UNDERHUNDRED
										: price.compareTo(PriceConstant.PRICE_THOUSAND) <= 0
												&& price.compareTo(PriceConstant.PRICE_HUNDRED) > 0
														? ServiceChargeCategoryEnum.UPTHOUSAND
														: ServiceChargeCategoryEnum.UPTHOUSAND;
		BigDecimal lastPrice = serviceChargeContext.CalcServiceCharge(1, erviceChargeCategoryEnum.getValue());
		log.info("计算完手续费的真实价格" + lastPrice);
		eticketInfoDTO.builder().serviceCharge(lastPrice.subtract(price)).totalPrice(lastPrice).build();
		log.info("计算券码价格结束");
		return lastPrice;
	}

	/**
	 * 购买电子票券
	 *
	 * @param eticketInfoDTO
	 * @return
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public Map<String, Object> buyEticket(String wechatOpenID, EticketInfoDTO eticketInfoDTO) {

		return null;
	}

	/**
	 * 确认电子票券，表明交易成功
	 *
	 * @param eticketInfoDTO
	 * @return
	 */
	@Override
	public Map<String, Object> commitEticket(EticketInfoDTO eticketInfoDTO) {

		// 交易成功，发送消息至会员微服务，为会员增加相应的积分
		this.addCreditPointProducer.sendCreditPointInfo(eticketInfoDTO);

		return null;
	}

	/**
	 * 发送第一次确认消息时，往rocketmqTransactionLog插入相应的记录
	 *
	 * @param transactionId
	 * @param eticketInfoDTO
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void insertRockmqTransactionLog(String transactionId, EticketInfoDTO eticketInfoDTO) {
		log.info("往RocketmqTransactionLogEntity中插入数据，表明发送第一消息");
		this.rocketmqTransactionLogRepository
				.save(RocketmqTransactionLogEntity
						.builder().transactionId(transactionId).log(String.format("对微信OPENID为的买家:%s和卖家:%s的用户增加积分",
								eticketInfoDTO.getWechatOpenIdBuyer(), eticketInfoDTO.getWechatOpenIdSeller()))
						.build());
	}
}
