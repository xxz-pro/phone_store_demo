package com.southwind.phone_store_demo.exception;

import com.southwind.phone_store_demo.enums.ResultEnums;

public class PhoneException extends RuntimeException {
    public PhoneException(ResultEnums resultEnum) {
        super(resultEnum.getMsg());
    }

    public PhoneException(String error) {
        super(error);
    }
}
