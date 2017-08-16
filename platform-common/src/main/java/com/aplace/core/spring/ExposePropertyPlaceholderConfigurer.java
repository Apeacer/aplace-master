package com.aplace.core.spring;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

import java.util.Properties;

/**
 * Descript: 将加载的配置内容以 beanname=configurer的形式
 * 以java.util.Properties类型注册到spring容器中
 *
 * @author ningning.wei
 * @version 17/8/13.
 */
public class ExposePropertyPlaceholderConfigurer  extends PropertyPlaceholderConfigurer  {

    @Override
    protected void processProperties(ConfigurableListableBeanFactory beanFactory,
                                     Properties properties)throws BeansException {
        super.processProperties(beanFactory, properties);
        // 将读到的 Properties 注册到容器
        beanFactory.registerSingleton("configurer",properties);
    }

}
