/********************************************************************
 * File Name:    AmqpMessageListenerContainerFactoryBean.java
 *
 * Date Created: Jan 19, 2019
 *
 * ------------------------------------------------------------------
 * 
 * Copyright (c) 2019 ajeydudhe@gmail.com
 *
 *******************************************************************/

package org.expedientframework.amqp.async.core.server;

import org.expedientframework.amqp.async.core.beans.AbstractFactoryBean;
import org.expedientframework.amqp.async.core.beans.BeanNames;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.MessageListenerContainer;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;

/**
 * TODO: Update with a detailed description of the interface/class.
 *
 */
public class AmqpMessageListenerContainerFactoryBean extends AbstractFactoryBean<SimpleMessageListenerContainer>
{
  public AmqpMessageListenerContainerFactoryBean(final Class<?> serviceInterfaceType,
                                                 final ConnectionFactory connectionFactory)
  {
    super(serviceInterfaceType);
    
    this.connectionFactory = connectionFactory;
  }

  @Override
  protected SimpleMessageListenerContainer createInstance() throws Exception
  {
    final SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(this.connectionFactory);
    
    container.setQueueNames(BeanNames.queue(this.serviceInterfaceType));
    
    container.setAutoStartup(true);
    
    return container;
  }
  
  @Override
  public void afterPropertiesSet() throws Exception
  {
    super.afterPropertiesSet();
  }
  
  @Override
  public Class<?> getObjectType()
  {
    return MessageListenerContainer.class;
  }

  private final ConnectionFactory connectionFactory;
}

