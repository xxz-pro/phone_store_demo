package com.southwind.phone_store_demo.controller;

import javax.validation.constraints.NotNull;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/test")
@Slf4j
public class testHandler {
    @GetMapping("/cc")
    public String test1(String t1, @NotNull(message = "不能为空") @RequestParam(name = "t2", required = true) String t2, HttpServletRequest request) {
        log.info("参数:{},{}", t1, t2);
        return "Java";
    }

    @GetMapping("/c2")
    public String test2(HttpServletRequest request) {
        String t1 = request.getParameter("t1");
        String t2 = request.getParameter("t2");
        log.info("参数:{},{}", t1, t2);
        return "Java";
    }

    @PostMapping("/ids")
    public List<Integer> updateDept(@RequestParam(value = "ids") List<Integer> ids) {
        List<Integer> list = new ArrayList<>();
        for (Integer id : ids) {
            list.add(id);
            System.out.println(id);
        }
        return list;
    }

    @PostMapping("/ids1")
    public Integer[] updateDept(@RequestParam(value = "ids") Integer[] ids) {

        return ids;
    }

}
