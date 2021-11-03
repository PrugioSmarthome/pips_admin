package com.daewooenc.pips.admin.web.service.reservation;

import lombok.experimental.UtilityClass;
import org.springframework.context.ApplicationContext;

@UtilityClass
public class BeanUtils {
    public static Object getBean(Class<?> classType) {
        ApplicationContext applicationContext = ApplicationContextProvider.getApplicationContext();
        return applicationContext.getBean(classType);
    }
}