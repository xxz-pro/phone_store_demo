package com.southwind.phone_store_demo.util;

import java.util.Random;

public class KeyUtil {
    public static synchronized String creatUniqueKey() {
        Random random = new Random();
        Integer key = random.nextInt(999999);
        return System.currentTimeMillis() + String.valueOf(key);
    }
}
