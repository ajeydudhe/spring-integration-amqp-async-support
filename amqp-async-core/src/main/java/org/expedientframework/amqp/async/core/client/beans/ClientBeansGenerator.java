/********************************************************************
 * File Name:    ClientBeansGenerator.java
 *
 * Date Created: Dec 16, 2018
 *
 * ------------------------------------------------------------------
 * 
 * Copyright (c) 2018 ajeydudhe@gmail.com
 *
 *******************************************************************/

package org.expedientframework.amqp.async.core.client.beans;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ClientBeansGenerator implements BeanFactoryPostProcessor
{
  @Override
  public void postProcessBeanFactory(final ConfigurableListableBeanFactory beanFactory) throws BeansException
  {
    LOG.info("Searching for ServiceInterfaceProvider beans...");
    
    beanFactory.getBeansOfType(ServiceInterfaceProvider.class)
    .forEach((beanName, beanClass) -> {
      
      LOG.info("Found bean [{}] with class [{}]", beanName, beanClass);
      beanClass.serviceInterfaces()
      .forEach(serviceInterfaceClass -> {
        
        LOG.info("Generating beans for [{}]", serviceInterfaceClass);
        
        //generateBeans(serviceInterfaceClass);
      });
    });
  }
  
  
  private static final Logger LOG = LoggerFactory.getLogger(ClientBeansGenerator.class);
}

