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
import org.springframework.beans.factory.config.ConstructorArgumentValues;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ClientBeansGenerator implements BeanFactoryPostProcessor
{
  @Override
  public void postProcessBeanFactory(final ConfigurableListableBeanFactory beanFactory) throws BeansException
  {
    LOG.info("Searching for ServiceInterfaceProvider beans..."); //TODO: Ajey - Change the log level to debug.
    
    beanFactory.getBeansOfType(ServiceInterfaceProvider.class)
    .forEach((beanName, beanClass) -> {
      
      LOG.info("Found bean [{}] with class [{}]", beanName, beanClass);
      beanClass.serviceInterfaces().forEach(serviceInterfaceClass -> generateBeans(serviceInterfaceClass, beanFactory));
    });
  }
    
  private void generateBeans(final Class<?> serviceInterfaceType, final ConfigurableListableBeanFactory beanFactory)
  {
    LOG.info("Generating beans for [{}]", serviceInterfaceType);
    
    final BeanDefinitionRegistry beanRegistry = (BeanDefinitionRegistry) beanFactory;
    
    generateServiceInterfaceProxyFactoryBean(serviceInterfaceType, beanRegistry);
  }

  private void generateServiceInterfaceProxyFactoryBean(final Class<?> serviceInterfaceType,
                                                        final BeanDefinitionRegistry beanRegistry)
  {
    final GenericBeanDefinition beanDefinition = new GenericBeanDefinition();
    
    beanDefinition.setAttribute("id", generateBeanName(serviceInterfaceType, "serviceInterfaceProxy"));
    beanDefinition.setBeanClass(ServiceInterfaceProxyFactoryBean.class);
    
    final ConstructorArgumentValues constructorArgumentValues = new ConstructorArgumentValues();
    constructorArgumentValues.addGenericArgumentValue(serviceInterfaceType);
    
    beanDefinition.setConstructorArgumentValues(constructorArgumentValues);
    
    beanRegistry.registerBeanDefinition(generateBeanName(serviceInterfaceType, "serviceInterfaceProxy"), beanDefinition);
  }

  private String generateBeanName(final Class<?> serviceInterfaceType, final String prefix)
  {
    return prefix + "-" + serviceInterfaceType.getName();        
  }
  
  private static final Logger LOG = LoggerFactory.getLogger(ClientBeansGenerator.class);
}

