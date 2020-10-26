package com.southwind.phone_store_demo.controller;

import com.southwind.phone_store_demo.exception.PhoneException;
import com.southwind.phone_store_demo.service.PhoneService;
import com.southwind.phone_store_demo.util.ResultVOUtil;
import com.southwind.phone_store_demo.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/phone")
public class PhoneHandler {
    @Autowired
    private PhoneService phoneService;

    @GetMapping("/index")
    public ResultVO index() {
        return ResultVOUtil.success(phoneService.findDataVO());
    }

    @GetMapping("/findByCategoryType")
    public ResultVO findByCategoryType(Integer categoryType, HttpServletRequest request) {
        return ResultVOUtil.success(phoneService.findPhoneInfoVOByCategoryType(categoryType));
    }

    @GetMapping("/findSpecsByPhoneId")
    public ResultVO findSpecsByPhoneId(@RequestParam("phoneId") Integer phoneId) {
        return ResultVOUtil.success(phoneService.findSpecsByPhoneId(phoneId));
    }
}
