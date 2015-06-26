package com.academy.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * Created by keos on 26.06.15.
 */
public class ApplicationContextProvider implements ApplicationContextAware {

    public static ApplicationContext cxt = null;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        cxt = applicationContext;
    }

    public static ApplicationContext getApplicationContext() {
        return cxt;
    }
}
