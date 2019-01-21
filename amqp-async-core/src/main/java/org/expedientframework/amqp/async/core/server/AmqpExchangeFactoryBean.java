/********************************************************************
 * File Name:    AmqpExchangeFactoryBean.java
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
  protected Exchange createInstance() throws Exception
  {
    final String exchangeName = BeanNames.exchange(this.serviceInterfaceType);
    return ExchangeBuilder.topicExchange(exchangeName).durable(true).autoDelete().build();
  }

  @Override
  public Class<?> getObjectType()
  {
    return Exchange.class;
  }
}

