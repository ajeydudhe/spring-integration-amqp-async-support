/********************************************************************
 * File Name:    AmqpExchangeFactoryBean.java
 *
 * Date Created: Dec 16, 2018
 *
 * ------------------------------------------------------------------
 * 
 * Copyright (c) 2018 ajeydudhe@gmail.com
 *
 *******************************************************************/

package org.expedientframework.amqp.async.core.client.beans;

import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.ExchangeBuilder;

/**
 * TODO: Update with a detailed description of the interface/class.
 *
 */
public class AmqpExchangeFactoryBean extends AbstractFactoryBean<Exchange>
{
  public AmqpExchangeFactoryBean(final Class<?> serviceInterfaceType)
  {
    super(serviceInterfaceType);
  }
  
  @Override
  public Exchange getObject() throws Exception
  {
    return ExchangeBuilder.topicExchange(BeanNames.exchange(serviceInterfaceType)).durable(true).autoDelete().build();
  }

  @Override
  public Class<?> getObjectType()
  {
    return Exchange.class;
  }
}

