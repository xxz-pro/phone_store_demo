package com.southwind.phone_store_demo.controller;

import com.southwind.phone_store_demo.dto.OrderDTO;
import com.southwind.phone_store_demo.exception.PhoneException;
import com.southwind.phone_store_demo.form.OrderForm;
import com.southwind.phone_store_demo.service.OrderService;
import com.southwind.phone_store_demo.service.PhoneService;
import com.southwind.phone_store_demo.util.ResultVOUtil;
import com.southwind.phone_store_demo.vo.OrderDetailVO;
import com.southwind.phone_store_demo.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/order")
@Slf4j
public class OrderHandler {
    @Autowired
    private OrderService orderService;
    @Autowired
    private PhoneService phoneService;

    @PostMapping("create")
    public ResultVO create(@Valid @RequestBody OrderForm orderForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.error("【创建订单】参数错误，orderForm={}", orderForm);
            throw new PhoneException(bindingResult.getFieldError().getDefaultMessage());
        }
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setBuyerName(orderForm.getName());
        orderDTO.setBuyerPhone(orderForm.getTel());
        orderDTO.setBuyerAddress(orderForm.getAddress());
        orderDTO.setSpecsId(orderForm.getSpecsId());
        orderDTO.setPhoneQuantity(orderForm.getQuantity());
        OrderDTO result = orderService.create(orderDTO);

        HashMap<String, String> map = new HashMap<>();
        map.put("orderId", result.getOrderId());
        return ResultVOUtil.success(map);
    }

    @GetMapping("/detail")
    public ResultVO detail(String orderId) {
        OrderDetailVO orderDetailVO = orderService.findOrderDetailByOrderId(orderId);
        return ResultVOUtil.success(orderDetailVO);
    }

    @PutMapping("pay")
    public ResultVO pay(String orderId) {
        String result = orderService.pay(orderId);
        Map<String, String> map = new HashMap<>();
        map.put("orderId", result);
        return ResultVOUtil.success(map, "支付成功");
    }
}