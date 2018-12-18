/********************************************************************
 * File Name:    AsyncAmqpTemplateFactoryBean.java
 *
 * Date Created: Dec 16, 2018
 *
 * ------------------------------------------------------------------
 * 
 * Copyright (c) 2018 ajeydudhe@gmail.com
 *
 *******************************************************************/

package org.expedientframework.amqp.async.core.client.beans;

import org.springframework.amqp.core.AsyncAmqpTemplate;
import org.springframework.amqp.rabbit.AsyncRabbitTemplate;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

/**
 * TODO: Update with a detailed description of the interface/class.
 *
 */
public class AsyncAmqpTemplateFactoryBean extends AbstractFactoryBean<AsyncAmqpTemplate>
{
  public AsyncAmqpTemplateFactoryBean(final Class<?> serviceInterfaceType, final ConnectionFactory connectionFactory)
  {
    super(serviceInterfaceType);
    this.connectionFactory = connectionFactory;
  }
  
  @Override
  public AsyncAmqpTemplate getObject() throws Exception
  {
    final RabbitTemplate rabbitTemplate = new RabbitTemplate(this.connectionFactory);
    
    final String exchangeName = BeanNames.exchange(this.serviceInterfaceType);
    rabbitTemplate.setExchange(exchangeName);
        
    final AsyncRabbitTemplate asyncRabbitTemplate = new AsyncRabbitTemplate(rabbitTemplate);
    
    asyncRabbitTemplate.start();
    
    return asyncRabbitTemplate;
  }

  @Override
  public Class<?> getObjectType()
  {
    return AsyncAmqpTemplate.class;
  }
  
  private final ConnectionFactory connectionFactory;
}

