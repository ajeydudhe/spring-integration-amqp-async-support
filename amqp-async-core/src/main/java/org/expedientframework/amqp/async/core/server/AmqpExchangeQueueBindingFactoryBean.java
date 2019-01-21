/********************************************************************
 * File Name:    AmqpExchangeQueueBindingFactoryBean.java
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
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.Queue;

/**
 * TODO: Update with a detailed description of the interface/class.
 *
 */
public class AmqpExchangeQueueBindingFactoryBean extends AbstractFactoryBean<Binding>
{
  public AmqpExchangeQueueBindingFactoryBean(final Class<?> serviceInterfaceType,
                                             final Queue queue,
                                             final Exchange exchange)
  {
    super(serviceInterfaceType);
    
    this.queue = queue;
    this.exchange = exchange;
  }

  @Override
  protected Binding createInstance() throws Exception
  {
    return BindingBuilder.bind(this.queue).to(this.exchange).with("#").noargs();
  }

  @Override
  public Class<?> getObjectType()
  {
    return Binding.class;
  }
  
  private final Queue queue;
  private final Exchange exchange;
}

