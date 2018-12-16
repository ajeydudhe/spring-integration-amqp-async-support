/********************************************************************
 * File Name:    MessageChannelFactoryBean.java
 *
 * Date Created: Dec 17, 2018
 *
 * ------------------------------------------------------------------
 * 
 * Copyright (c) 2018 ajeydudhe@gmail.com
 *
 *******************************************************************/

package org.expedientframework.amqp.async.core.client.beans;

import org.springframework.integration.dsl.MessageChannels;
import org.springframework.messaging.MessageChannel;

/**
 * TODO: Update with a detailed description of the interface/class.
 *
 */
public class MessageChannelFactoryBean extends AbstractFactoryBean<MessageChannel>
{
  protected MessageChannelFactoryBean(final Class<?> serviceInterfaceType)
  {
    super(serviceInterfaceType);
  }

  @Override
  public MessageChannel getObject() throws Exception
  {
    return MessageChannels.direct(BeanNames.messageChannel(this.serviceInterfaceType)).get();
  }

  @Override
  public Class<?> getObjectType()
  {
    return MessageChannel.class;
  }
}

