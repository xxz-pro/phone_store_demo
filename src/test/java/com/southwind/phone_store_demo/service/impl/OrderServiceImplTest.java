package com.southwind.phone_store_demo.service.impl;

import com.southwind.phone_store_demo.dto.OrderDTO;
import com.southwind.phone_store_demo.entity.OrderMaster;
import com.southwind.phone_store_demo.service.OrderService;
import com.southwind.phone_store_demo.vo.OrderDetailVO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class OrderServiceImplTest {
    @Autowired
    private OrderService orderService;

    @Test
    void create() {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setBuyerName("小明");
        orderDTO.setBuyerPhone("13678787878");
        orderDTO.setBuyerAddress("广东省深圳市罗湖区科技路123号456室");
        orderDTO.setSpecsId(2);
        orderDTO.setPhoneQuantity(1);
        OrderDTO result = orderService.create(orderDTO);
        System.out.println(result);
    }

    @Test
    void findId() {
        OrderDetailVO orderDetailVO = orderService.findOrderDetailByOrderId("1602812367203258231");
        int id = 0;
    }
}