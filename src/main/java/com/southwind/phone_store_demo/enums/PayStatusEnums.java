package com.southwind.phone_store_demo.enums;

import lombok.Getter;

@Getter
public enum PayStatusEnums {
    UNPAID(0, "未支付"),
    PAID(1, "已支付");

    private Integer code;
    private String msg;

    PayStatusEnums(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
