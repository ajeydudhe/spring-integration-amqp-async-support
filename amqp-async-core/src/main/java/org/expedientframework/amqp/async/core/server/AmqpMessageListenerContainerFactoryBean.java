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
import org.springframework.amqp.core.Queue;
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
                                                 final ConnectionFactory connectionFactory,
                                                 final Queue queue)
  {
    super(serviceInterfaceType);
    
    this.connectionFactory = connectionFactory;
    this.queue = queue;
  }

  @Override
  protected SimpleMessageListenerContainer createInstance() throws Exception
  {
    final SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(this.connectionFactory);
    
    //container.setQueues(this.queue);
    container.setQueueNames(BeanNames.queue(this.serviceInterfaceType));
    
    container.start();
    
    return container;
  }
  
  @Override
  public Class<?> getObjectType()
  {
    return MessageListenerContainer.class;
  }

  private final ConnectionFactory connectionFactory;
  private final Queue queue;
}

