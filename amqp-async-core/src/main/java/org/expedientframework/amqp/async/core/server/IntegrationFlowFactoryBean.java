/********************************************************************
 * File Name:    IntegrationFlowFactoryBean.java
 *
 * Date Created: Jan 19, 2019
 *
 * ------------------------------------------------------------------
 * 
 * Copyright (c) 2019 ajeydudhe@gmail.com
 *
 *******************************************************************/

package org.expedientframework.amqp.async.core.server;

import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.beans.factory.config.AbstractFactoryBean;
import org.springframework.integration.amqp.dsl.Amqp;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.Transformers;
import org.springframework.integration.handler.GenericHandler;
import org.springframework.messaging.MessageHeaders;

/**
 * TODO: Update with a detailed description of the interface/class.
 *
 */
public class IntegrationFlowFactoryBean extends AbstractFactoryBean<IntegrationFlow>
{
  public IntegrationFlowFactoryBean(final Object serviceInstance,
                                    final SimpleMessageListenerContainer messageListenerContainer)
  {
    this.serviceInstance = serviceInstance;
    this.messageListenerContainer = messageListenerContainer;
  }

  @Override
  public Class<?> getObjectType()
  {
    return IntegrationFlow.class;
  }

  @Override
  protected IntegrationFlow createInstance() throws Exception
  {
    return
    IntegrationFlows.from(Amqp.inboundGateway(this.messageListenerContainer))
                    .log()
                    .transform(Transformers.deserializer())
                    .log()
                    .handle(new GenericHandler<Object[]>()
                    {
                      @Override
                      public Object handle(final Object[] payload, final MessageHeaders headers)
                      {
                        logger.info("### Server called...");
                        
                        return "dummyValue";
                      }
                    })
                    .transform(Transformers.serializer())
                    .logAndReply();
                    
  }
  
  private final Object serviceInstance;
  private final SimpleMessageListenerContainer messageListenerContainer;
}

