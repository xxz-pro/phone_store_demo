package com.southwind.phone_store_demo.form;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class AddressForm {
    private Integer id;
    private String name;
    @NotNull(message = "电话不能为空")
    private String tel;
    @NotNull(message = "省不能为空")
    private String province;
    @NotNull(message = "市不能为空")
    private String city;
    @NotNull(message = "区不能为空")
    private String county;
    @NotNull(message = "编码不能为空")
    private String areaCode;
    @NotNull(message = "详细地址不能为空")
    private String addressDetail;
}
