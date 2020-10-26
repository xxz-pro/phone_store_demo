package com.southwind.phone_store_demo.service.impl;

import com.southwind.phone_store_demo.dto.OrderDTO;
import com.southwind.phone_store_demo.entity.OrderMaster;
import com.southwind.phone_store_demo.entity.PhoneInfo;
import com.southwind.phone_store_demo.entity.PhoneSpecs;
import com.southwind.phone_store_demo.enums.PayStatusEnums;
import com.southwind.phone_store_demo.enums.ResultEnums;
import com.southwind.phone_store_demo.exception.PhoneException;
import com.southwind.phone_store_demo.repository.OrderMasterRepository;
import com.southwind.phone_store_demo.repository.PhoneInfoRepository;
import com.southwind.phone_store_demo.repository.PhoneSpecsRepository;
import com.southwind.phone_store_demo.service.OrderService;
import com.southwind.phone_store_demo.service.PhoneService;
import com.southwind.phone_store_demo.util.KeyUtil;
import com.southwind.phone_store_demo.vo.OrderDetailVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {
    @Autowired
    private PhoneSpecsRepository phoneSpecsRepository;

    @Autowired
    private PhoneInfoRepository phoneInfoRepository;

    @Autowired
    private OrderMasterRepository orderMasterRepository;

    @Autowired
    private PhoneService phoneService;

    @Override
    public OrderDTO create(OrderDTO orderDTO) {
        OrderMaster orderMaster = new OrderMaster();
        BeanUtils.copyProperties(orderDTO, orderMaster);

        PhoneSpecs phoneSpecs = phoneSpecsRepository.findById(orderDTO.getSpecsId()).get();
        if (phoneSpecs == null) {
            log.error("【创建订单】规格为空", phoneSpecs);
            throw new PhoneException(ResultEnums.SPECS_NOT_EXIST);
        }
        BeanUtils.copyProperties(phoneSpecs, orderMaster);

        PhoneInfo phoneInfo = phoneInfoRepository.findById(phoneSpecs.getPhoneId()).get();
        if (phoneInfo == null) {
            log.error("【创建订单】手机不存在", phoneInfo);
            throw new PhoneException(ResultEnums.PHONE_NOT_EXIST);
        }
        BeanUtils.copyProperties(phoneInfo, orderMaster);

        //计算总价
        BigDecimal orderAmount = new BigDecimal(0);
        orderAmount = phoneSpecs.getSpecsPrice().divide(new BigDecimal(100))
                .multiply(new BigDecimal(orderDTO.getPhoneQuantity()))
                .add(orderAmount);
        orderMaster.setOrderAmount(orderAmount);

        //orderId
        orderMaster.setOrderId(KeyUtil.creatUniqueKey());
        orderDTO.setOrderId(orderMaster.getOrderId());

        //payStatus
        orderMaster.setPayStatus(PayStatusEnums.UNPAID.getCode());
        phoneService.subStock(orderDTO.getSpecsId(), orderDTO.getPhoneQuantity());
        orderMasterRepository.save(orderMaster);

        return orderDTO;
    }

    @Override
    public OrderDetailVO findOrderDetailByOrderId(String orderId) {
        OrderDetailVO orderDetailVO = new OrderDetailVO();
        OrderMaster orderMaster = orderMasterRepository.findById(orderId).get();
        if (orderMaster == null) {
            log.error("【查询订单】订单为空", orderMaster);
            throw new PhoneException(ResultEnums.ORDER_NOT_EXIST);
        }
        BeanUtils.copyProperties(orderMaster, orderDetailVO);
        orderDetailVO.setSpecsPrice(orderMaster.getSpecsPrice().divide(new BigDecimal(100)) + ".00");
        return orderDetailVO;
    }

    @Override
    public String pay(String orderId) {
        OrderMaster orderMaster = orderMasterRepository.findById(orderId).get();
        if (orderMaster == null) {
            log.error("【支付订单】订单为空", orderMaster);
            throw new PhoneException(ResultEnums.ORDER_NOT_EXIST);
        }
        if (orderMaster.getPayStatus().equals(PayStatusEnums.UNPAID.getCode())) {
            orderMaster.setPayStatus(PayStatusEnums.PAID.getCode());
        } else {
            log.error("【支付订单】订单已支付", orderMaster);
        }
        return orderId;
    }
}
