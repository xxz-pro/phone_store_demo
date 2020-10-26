package com.southwind.phone_store_demo.form;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class OrderForm {
    @NotNull(message = "名字不能为空")
    private String name;
    @NotNull(message = "电话不能为空")
    private String tel;
    @NotNull(message = "地址不能为空")
    private String address;
    @NotNull(message = "规格id不能为空")
    private Integer specsId;
    @NotNull(message = "数量不能为空")
    private Integer quantity;
}
