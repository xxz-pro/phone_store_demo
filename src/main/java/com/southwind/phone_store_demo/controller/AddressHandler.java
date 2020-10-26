package com.southwind.phone_store_demo.controller;

import com.southwind.phone_store_demo.exception.PhoneException;
import com.southwind.phone_store_demo.form.AddressForm;
import com.southwind.phone_store_demo.form.IdForm;
import com.southwind.phone_store_demo.service.AddressService;
import com.southwind.phone_store_demo.util.ResultVOUtil;
import com.southwind.phone_store_demo.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/address")
@Slf4j
public class AddressHandler {
    @Autowired
    private AddressService addressService;

    @GetMapping("/list")
    public ResultVO index() {
        return ResultVOUtil.success(addressService.findAll());
    }

    @PostMapping("/create")
    public ResultVO create(@Valid @RequestBody AddressForm addressForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.error("【添加地址】参数错误,addressForm={}", addressForm);
            throw new PhoneException(bindingResult.getFieldError().getDefaultMessage());
        }
        addressService.saveOrUpdate(addressForm);
        return ResultVOUtil.success(null);
    }

    @PutMapping("/update")
    public ResultVO update(@Valid @RequestBody AddressForm addressForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.error("【修改地址】参数错误,addressForm={}", addressForm);
            throw new PhoneException(bindingResult.getFieldError().getDefaultMessage());
        }
        addressService.saveOrUpdate(addressForm);
        Map<String, Integer> map = new HashMap<>();
        map.put("id", addressForm.getId());
        return ResultVOUtil.success(map);
    }

    @PostMapping("/delete")
    public ResultVO delete(Integer id) {
        Map<String, Integer> map = new HashMap<>();
        map.put("id", id);
        if (id == null || id.equals("")) {
            return ResultVOUtil.error("id不为空");
        }
        addressService.deleteAddress(id);
        return ResultVOUtil.success(map);
    }

    @PostMapping("/delete1")
    public ResultVO delete1(@Valid @RequestBody IdForm idForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.error("参数有误");
            throw new PhoneException(bindingResult.getFieldError().getDefaultMessage());
        }
        Map<String, Integer> map = new HashMap<>();
        map.put("id", idForm.getId());
        return ResultVOUtil.success(map);
    }

}
