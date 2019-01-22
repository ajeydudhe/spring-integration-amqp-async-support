/********************************************************************
 * File Name:    AmqpQueueFactoryBean.java
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
import org.springframework.amqp.core.QueueBuilder;

/**
 * TODO: Update with a detailed description of the interface/class.
 *
 */
public class AmqpQueueFactoryBean extends AbstractFactoryBean<Queue>
{
  public AmqpQueueFactoryBean(final Class<?> serviceInterfaceType)
  {
    super(serviceInterfaceType);
  }

  @Override
  protected Queue createInstance() throws Exception
  {
    final String queueName = BeanNames.queue(this.serviceInterfaceType);
    final Queue queue = QueueBuilder.nonDurable(queueName).autoDelete().build();
    
    return queue;
  }

  @Override
  public Class<?> getObjectType()
  {
    return Queue.class;
  }
}

