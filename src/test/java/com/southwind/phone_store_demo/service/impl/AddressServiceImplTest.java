package com.southwind.phone_store_demo.service.impl;

import com.southwind.phone_store_demo.form.AddressForm;
import com.southwind.phone_store_demo.service.AddressService;
import com.southwind.phone_store_demo.util.KeyUtil;
import com.southwind.phone_store_demo.vo.AddressVO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AddressServiceImplTest {
    @Autowired
    private AddressService addressService;

    @Test
    void findAll() {
        List<AddressVO> list = addressService.findAll();
        int id = 0;
    }

    @Test
    void saveOrUpdate() {
        AddressForm addressForm = new AddressForm();
//        addressForm.setId(38);
        addressForm.setName("小离2");
        addressForm.setTel("13678787878");
        addressForm.setProvince("广东省");
        addressForm.setCity("深圳市");
        addressForm.setCounty("罗湖区");
        addressForm.setAddressDetail("科技路123号456室");
        addressForm.setAreaCode("330104");
        addressService.saveOrUpdate(addressForm);
    }

    @Test
    void delete() {
        addressService.deleteAddress(44);
    }

}