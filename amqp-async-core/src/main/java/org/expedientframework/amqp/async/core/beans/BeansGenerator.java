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
import org.expedientframework.amqp.async.core.server.AmqpExchangeFactoryBean;
import org.expedientframework.amqp.async.core.server.AmqpExchangeQueueBindingFactoryBean;
import org.expedientframework.amqp.async.core.server.AmqpMessageListenerContainerFactoryBean;
import org.expedientframework.amqp.async.core.server.AmqpQueueFactoryBean;
import org.expedientframework.amqp.async.core.server.IntegrationFlowFactoryBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.PriorityOrdered;
import org.springframework.integration.dsl.IntegrationFlow;

@Configuration
public class BeansGenerator implements BeanFactoryPostProcessor, PriorityOrdered
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
    generateAmqpServerBeans(serviceInterfaceType, beanFactory, beanRegistry);
  }

  private void generateAmqpServerBeans(final Class<?> serviceInterfaceType, 
                                       final ConfigurableListableBeanFactory beanFactory,
                                       final BeanDefinitionRegistry beanRegistry)
  {
    generateAmqpQueueFactoryBean(serviceInterfaceType, beanFactory, beanRegistry);
    generateAmqpExchangeFactoryBean(serviceInterfaceType, beanFactory, beanRegistry);
    generateAmqpExchangeQueueBindingFactoryBean(serviceInterfaceType, beanFactory, beanRegistry);
    generateAmqpMessageListenerContainerFactoryBean(serviceInterfaceType, beanFactory, beanRegistry);
    generateIntegrationFlowFactoryBean(serviceInterfaceType, beanFactory, beanRegistry);
  }

  private void generateIntegrationFlowFactoryBean(final Class<?> serviceInterfaceType, 
                                                  final ConfigurableListableBeanFactory beanFactory,
                                                  final BeanDefinitionRegistry beanRegistry)
  {
    final BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(IntegrationFlowFactoryBean.class);

    builder.addConstructorArgValue(beanFactory.getBean(serviceInterfaceType));
    builder.addConstructorArgValue(beanFactory.getBean(BeanNames.messageListener(serviceInterfaceType)));
    
    final String beanName = BeanNames.integrationFlow(serviceInterfaceType);
    beanRegistry.registerBeanDefinition(beanName, builder.getBeanDefinition());
    
    LOG.info("Added bean [{}]", beanName);    
  }

  private void generateAmqpMessageListenerContainerFactoryBean(final Class<?> serviceInterfaceType,
                                                               final ConfigurableListableBeanFactory beanFactory, 
                                                               final BeanDefinitionRegistry beanRegistry)
  {
    final BeanDefinitionBuilder builder = beanDefinitionBuilder(AmqpMessageListenerContainerFactoryBean.class, 
                                                                serviceInterfaceType);
    
    builder.addConstructorArgValue(beanFactory.getBean(ConnectionFactory.class));
    
    final String beanName = BeanNames.messageListener(serviceInterfaceType);
    beanRegistry.registerBeanDefinition(beanName, builder.getBeanDefinition());
    
    LOG.info("Added bean [{}]", beanName);    
  }

  private void generateAmqpExchangeQueueBindingFactoryBean(final Class<?> serviceInterfaceType,
                                                           final ConfigurableListableBeanFactory beanFactory, 
                                                           final BeanDefinitionRegistry beanRegistry)
  {
    final BeanDefinitionBuilder builder = beanDefinitionBuilder(AmqpExchangeQueueBindingFactoryBean.class, 
                                                                serviceInterfaceType);

    builder.addConstructorArgValue(beanFactory.getBean(BeanNames.queue(serviceInterfaceType)));
    builder.addConstructorArgValue(beanFactory.getBean(BeanNames.exchange(serviceInterfaceType)));
    
    final String beanName = BeanNames.binding(serviceInterfaceType);
    beanRegistry.registerBeanDefinition(beanName, builder.getBeanDefinition());
    
    LOG.info("Added bean [{}]", beanName);    
  }

  private void generateAmqpExchangeFactoryBean(final Class<?> serviceInterfaceType, 
                                               final ConfigurableListableBeanFactory beanFactory,
                                               final BeanDefinitionRegistry beanRegistry)
  {
    //TODO: Ajey - We should check if bean with given name already exists. If not then only add. This will allow to customize.
    final BeanDefinitionBuilder builder = beanDefinitionBuilder(AmqpExchangeFactoryBean.class, 
                                                                serviceInterfaceType);
    
    final String beanName = BeanNames.exchange(serviceInterfaceType);
    beanRegistry.registerBeanDefinition(beanName, builder.getBeanDefinition());
    
    LOG.info("Added bean [{}]", beanName);
  }

  private void generateAmqpQueueFactoryBean(final Class<?> serviceInterfaceType, 
                                            final ConfigurableListableBeanFactory beanFactory,
                                            final BeanDefinitionRegistry beanRegistry)
  {
    final BeanDefinitionBuilder builder = beanDefinitionBuilder(AmqpQueueFactoryBean.class, 
                                                                serviceInterfaceType);
    
    final String beanName = BeanNames.queue(serviceInterfaceType);
    beanRegistry.registerBeanDefinition(beanName, builder.getBeanDefinition());
    
    LOG.info("Added bean [{}]", beanName);
  }

  private void generateServiceInterfaceProxyFactoryBean(final Class<?> serviceInterfaceType,
                                                        final BeanFactory beanFactory,      
                                                        final BeanDefinitionRegistry beanRegistry)
  {
    final BeanDefinitionBuilder builder = beanDefinitionBuilder(ServiceInterfaceProxyFactoryBean.class, 
                                                                serviceInterfaceType);
    
    final String asyncTemplateBeanName = BeanNames.asyncAmqpTemplate(serviceInterfaceType);
    builder.addConstructorArgValue(beanFactory.getBean(asyncTemplateBeanName));
    
    final String beanName = BeanNames.generateName(serviceInterfaceType, "serviceInterfaceProxy");
    beanRegistry.registerBeanDefinition(beanName, builder.getBeanDefinition());
    
    LOG.info("Added bean [{}]", beanName);
  }

  private void generateAsyncAmqpTemplateFactoryBean(final Class<?> serviceInterfaceType, 
                                                    final BeanFactory beanFactory,
                                                    final BeanDefinitionRegistry beanRegistry)
  {
    final BeanDefinitionBuilder builder = beanDefinitionBuilder(AsyncAmqpTemplateFactoryBean.class, 
                                                                serviceInterfaceType);
    
    builder.addConstructorArgValue(beanFactory.getBean(ConnectionFactory.class));
    
    final String beanName = BeanNames.asyncAmqpTemplate(serviceInterfaceType);
    beanRegistry.registerBeanDefinition(beanName, builder.getBeanDefinition());

    LOG.info("Added bean [{}]", beanName);
  }

  private BeanDefinitionBuilder beanDefinitionBuilder(final Class<?> beanType, final Class<?> serviceInterfaceType)
  {
    return
    BeanDefinitionBuilder.genericBeanDefinition(beanType)
                         .addConstructorArgValue(serviceInterfaceType);
  }

  private static final Logger LOG = LoggerFactory.getLogger(BeansGenerator.class);

  @Override
  public int getOrder()
  {
    return Ordered.LOWEST_PRECEDENCE;
  }
}

