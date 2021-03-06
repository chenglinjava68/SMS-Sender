package com.zuma.sms.util;


import com.zuma.sms.enums.system.PhoneOperatorEnum;
import com.zuma.sms.pool.CommonPool;
import com.zuma.sms.pool.OperatorPatternPoolFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

/**
 * author:Administrator
 * datetime:2017/11/13 0013 16:17
 * 正则工厂
 */
@Component
public class RegexpUtil {

    private static OperatorPatternPoolFactory factory;

    @Autowired
    public void init(OperatorPatternPoolFactory factory) {
        RegexpUtil.factory = factory;
    }

    public static boolean match(PhoneOperatorEnum operatorEnum, String phone){
        CommonPool<Pattern> pool = factory.build(operatorEnum);
        Pattern pattern = null;
        try {
            pattern = pool.borrow();
            return pattern.matcher(phone).matches();
        } finally {
            pool.returnObj(pattern);
        }
    }

    public static boolean yidongMatch(String phone){
        return match(PhoneOperatorEnum.YIDONG,phone);
    }

    public static boolean liantongMatch(String phone){
        return match(PhoneOperatorEnum.LIANTONG,phone);
    }

    public static boolean dianxinMatch(String phone){
        return match(PhoneOperatorEnum.DIANXIN,phone);
    }
}
