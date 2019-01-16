/********************************************************************
 * File Name:    BeansGenerator.java
 *
 * Date Created: Jan 15, 2019
 *
 * ------------------------------------------------------------------
 * 
 * Copyright (c) 2019 ajeydudhe@gmail.com
 *
 *******************************************************************/

package org.expedientframework.amqp.async.core.beans;

import org.expedientframework.amqp.async.core.client.AsyncAmqpTemplateFactoryBean;
import org.expedientframework.amqp.async.core.client.ServiceInterfaceProxyFactoryBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.ConstructorArgumentValues;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeansGenerator implements BeanFactoryPostProcessor
{
  @Override
  public void postProcessBeanFactory(final ConfigurableListableBeanFactory beanFactory) throws BeansException
  {
    LOG.info("Searching for ServiceInterfaceProvider beans..."); //TODO: Ajey - Change the log level to debug.
    
    beanFactory.getBeansOfType(ServiceInterfaceProvider.class)
               .forEach((beanName, beanClass) -> {
                  
                 LOG.info("Found bean [{}] with class [{}]", beanName, beanClass);
                 if(beanClass.clients() != null)
                 {
                   beanClass.clients().forEach(serviceInterfaceClass -> generateClientBeans(serviceInterfaceClass, beanFactory));
                 }
                 if(beanClass.servers() != null)
                 {
                   beanClass.servers().forEach(serviceInterfaceClass -> generateServerBeans(serviceInterfaceClass, beanFactory));
                 }
               });
  }
    
  private void generateClientBeans(final Class<?> serviceInterfaceType, final ConfigurableListableBeanFactory beanFactory)
  {
    LOG.info("Generating client beans for [{}]", serviceInterfaceType);
    
    final BeanDefinitionRegistry beanRegistry = (BeanDefinitionRegistry) beanFactory;
    
    //TODO: Ajey - For each bean we should first check if there is bean with name already added to override or manually configure it.
    generateAsyncAmqpTemplateFactoryBean(serviceInterfaceType, beanFactory, beanRegistry);
    generateServiceInterfaceProxyFactoryBean(serviceInterfaceType, beanFactory, beanRegistry);
  }

  private void generateServerBeans(final Class<?> serviceInterfaceType, final ConfigurableListableBeanFactory beanFactory)
  {
    LOG.info("Generating server beans for [{}]", serviceInterfaceType);
    
    final BeanDefinitionRegistry beanRegistry = (BeanDefinitionRegistry) beanFactory;
    
    //TODO: Ajey - For each bean we should first check if there is bean with name already added to override or manually configure it.
  }

  private void generateServiceInterfaceProxyFactoryBean(final Class<?> serviceInterfaceType,
                                                        final BeanFactory beanFactory,      
                                                        final BeanDefinitionRegistry beanRegistry)
  {
    final GenericBeanDefinition beanDefinition = new GenericBeanDefinition();
    
    beanDefinition.setAttribute("id", BeanNames.generateName(serviceInterfaceType, "serviceInterfaceProxy"));
    beanDefinition.setBeanClass(ServiceInterfaceProxyFactoryBean.class);
    
    final ConstructorArgumentValues constructorArgumentValues = generateConstructorArguments(serviceInterfaceType);
    
    final String asyncTemplateBeanName = BeanNames.asyncAmqpTemplate(serviceInterfaceType);
    constructorArgumentValues.addGenericArgumentValue(beanFactory.getBean(asyncTemplateBeanName));
    
    beanDefinition.setConstructorArgumentValues(constructorArgumentValues);
    
    final String beanName = BeanNames.generateName(serviceInterfaceType, "serviceInterfaceProxy");
    beanRegistry.registerBeanDefinition(beanName, beanDefinition);
    
    LOG.info("Added bean [{}]", beanName);
  }

  private void generateAsyncAmqpTemplateFactoryBean(final Class<?> serviceInterfaceType, 
                                                    final BeanFactory beanFactory,
                                                    final BeanDefinitionRegistry beanRegistry)
  {
    final GenericBeanDefinition beanDefinition = new GenericBeanDefinition();
    
    beanDefinition.setAttribute("id", BeanNames.asyncAmqpTemplate(serviceInterfaceType));
    beanDefinition.setBeanClass(AsyncAmqpTemplateFactoryBean.class);
    
    final ConstructorArgumentValues constructorArgumentValues = generateConstructorArguments(serviceInterfaceType);
    constructorArgumentValues.addGenericArgumentValue(beanFactory.getBean(ConnectionFactory.class));
    
    beanDefinition.setConstructorArgumentValues(constructorArgumentValues);
        
    final String beanName = BeanNames.asyncAmqpTemplate(serviceInterfaceType);
    beanRegistry.registerBeanDefinition(beanName, beanDefinition);

    LOG.info("Added bean [{}]", beanName);
  }

  private ConstructorArgumentValues generateConstructorArguments(final Class<?> serviceInterfaceType)
  {
    final ConstructorArgumentValues constructorArgumentValues = new ConstructorArgumentValues();
    constructorArgumentValues.addGenericArgumentValue(serviceInterfaceType);
    return constructorArgumentValues;
  }

  private static final Logger LOG = LoggerFactory.getLogger(BeansGenerator.class);
}

