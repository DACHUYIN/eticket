package com.wechatapp.eticket.transaction.service.impl;

import com.wechatapp.eticket.core.common.constants.CommonConstant;
import com.wechatapp.eticket.core.common.constants.PriceConstant;
import com.wechatapp.eticket.core.common.constants.ResponseMsgConstant;
import com.wechatapp.eticket.core.common.util.CalculateTimeDiffUtils;
import com.wechatapp.eticket.core.common.util.DateFormatUtils;
import com.wechatapp.eticket.core.dto.EticketInfoDTO;
import com.wechatapp.eticket.core.enums.ServiceChargeCategoryEnum;
import com.wechatapp.eticket.transaction.context.ServiceChargeContext;
import com.wechatapp.eticket.transaction.dto.TransactionResponseDTO;
import com.wechatapp.eticket.transaction.producer.InsertEticketInfoProducer;
import com.wechatapp.eticket.transaction.redis.TransactionRedis;
import com.wechatapp.eticket.transaction.service.ITransactionService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TransactionServiceImpl implements ITransactionService {

    private final ServiceChargeContext serviceChargeContext;

    private final InsertEticketInfoProducer insertEticketInfoProducer;

    private final TransactionRedis transactionRedis;

    /**
     * 上传图片
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
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
                // 图片的路径为：eticket-img/该用户的wechatOpenId/日期/券码种类/
                path = CommonConstant.IMG_UPLOAD_ADDRESS + wechatOpenId + "/" +
                        DateFormatUtils.YYYY_MM_DD.get().format(new Date()) + "/" + ticketType + "/";
                File fileDir = new File(path);
                if (!fileDir.exists())
                    fileDir.mkdirs();
                log.info("图片存放的路径为：" + path);
                file.transferTo(new File(path + fileName));
                log.info("图片上传成功");
                return TransactionResponseDTO.builder()
                        .imgAddress(path + fileName)
                        .responseCode(ResponseMsgConstant.RESPONSECODE_SUCCESS_UPLOAD_FILE)
                        .responseMsg(ResponseMsgConstant.RESPONSEMSG_SUCCESS_UPLOAD_FILE)
                        .build();
            } else {
                log.info("图片格式不符合要求");
                return TransactionResponseDTO.builder()
                        .responseCode(ResponseMsgConstant.RESPONSECODE_FAIL_FILETYPE_ERROR)
                        .responseMsg(ResponseMsgConstant.RESPONSEMSG_FAIL_FILETYPE_ERROR)
                        .build();
            }
        } catch (IOException e) {
            log.error(e.toString());
            return TransactionResponseDTO.builder()
                    .responseCode(ResponseMsgConstant.RESPONSECODE_FAIL_UPLOAD_FILE)
                    .responseMsg(ResponseMsgConstant.RESPONSEMSG_FAIL_UPLOAD_FILE)
                    .build();
        }
    }

    /**
     * 提交电子票券进入市场
     *
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public TransactionResponseDTO submitEticket(EticketInfoDTO eticketInfoDTO) {
        try {
            log.info("开始提交券码");

            BigDecimal price = eticketInfoDTO.getPrice();
            BigDecimal totalPrice = getTotalPrice(price);
            // 当前台计算的价格和后台不一样时，返回报错信息
            if (totalPrice.compareTo(eticketInfoDTO.getTotalPrice()) != 0) {
                log.error("价格有误，前台价格为：{}，后台价格为：{}", totalPrice, eticketInfoDTO.getTotalPrice());
                return TransactionResponseDTO.builder()
                        .responseCode(ResponseMsgConstant.RESPONSECODE_ERROR_TOTALPRICE)
                        .responseMsg(ResponseMsgConstant.RESPONSEMSG_ERROR_TOTALPRICE)
                        .build();
            }
            // 保存券码的服务费
            eticketInfoDTO.setServiceCharge(totalPrice.subtract(price));
            // 计算券码的有效天数
            int termValidity = CalculateTimeDiffUtils.calculateDayDiff(eticketInfoDTO.getStartDate(), eticketInfoDTO.getEndDate());
            if (termValidity == 0) {
                log.info("起始日期为：{}，结束日期为：{}", eticketInfoDTO.getStartDate(), eticketInfoDTO.getEndDate());
                return TransactionResponseDTO.builder()
                        .responseCode(ResponseMsgConstant.RESPONSECODE_ERROR_TOTALPRICE)
                        .responseMsg(ResponseMsgConstant.RESPONSEMSG_ERROR_TOTALPRICE)
                        .build();
            }
            eticketInfoDTO.setTermValidity(termValidity);
            transactionRedis.saveEticketInfo(eticketInfoDTO);
            transactionRedis.saveEticketToClassficationMarket(eticketInfoDTO);
            // 发送消息至RocketMQ
            sendInsertEticketInfoMessage(eticketInfoDTO);
            log.info("券码成功提交");
            return TransactionResponseDTO.builder()
                    .responseCode(ResponseMsgConstant.RESPONSECODE_SUCCESS_SUBMIT_ETICKET)
                    .responseMsg(ResponseMsgConstant.RESPONSEMSG_SUCCESS_SUBMIT_ETICKET)
                    .build();
        } catch (Exception e) {
            return TransactionResponseDTO.builder()
                    .responseCode(ResponseMsgConstant.RESPONSECODE_FAIL_SUBMIT_ETICKET)
                    .responseMsg(ResponseMsgConstant.RESPONSEMSG_FAIL_SUBMIT_ETICKET)
                    .build();
        }
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
     * 向RocketMQ发送消息，让会员微服务将相应的订单信息持久化到MySQL
     *
     * @param eticketInfoDTO
     */
    @Async
    public void sendInsertEticketInfoMessage(EticketInfoDTO eticketInfoDTO) {
        insertEticketInfoProducer.produceInsertEticketInfoMessage(eticketInfoDTO);
    }

    /**
     * 计算券码的最终价格
     *
     * @param price
     * @return
     */
    private BigDecimal getTotalPrice(BigDecimal price) {
        log.info("计算券码价格");
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
        BigDecimal serviceCharge = serviceChargeContext.CalcServiceCharge(1, erviceChargeCategoryEnum.getValue());
        // 总价格保留2位小数且不需用四舍五入
        int index = serviceCharge.toString().indexOf(".");
        if (index > -1)
            serviceCharge = new BigDecimal(serviceCharge.toString().substring(0, index + 3));
        BigDecimal totalPrice = price.add(serviceCharge);
        log.info("计算完手续费的真实价格" + totalPrice);
        return totalPrice;
    }
}
