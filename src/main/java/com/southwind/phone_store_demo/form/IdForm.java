package com.southwind.phone_store_demo.form;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class IdForm {
    @NotNull(message = "id不能为空")
    private Integer id;
}
