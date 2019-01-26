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

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.beans.factory.config.AbstractFactoryBean;
import org.springframework.expression.spel.support.ReflectionHelper;
import org.springframework.integration.amqp.dsl.Amqp;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.Transformers;
import org.springframework.integration.handler.GenericHandler;
import org.springframework.messaging.MessageHeaders;
import org.springframework.util.ReflectionUtils;

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
                        try
                        {
                          // Assuming we always have the methodName
                          final String methodName = (String) headers.get("remote_gateway_method"); //TODO: Ajey - Use constant !!!
                          
                          LOG.info("Executing method [{}]", methodName);
                          
                          for (Method method : serviceInstance.getClass().getMethods())
                          {
                            if(method.getName().equalsIgnoreCase(methodName))
                            {
                              LOG.info("Found method [{}]", method);
                              return method.invoke(serviceInstance, payload);
                            }
                          }
                          
                          throw new RuntimeException(String.format("Method [%s] not found on [%s]", methodName, serviceInstance.getClass())); //TODO: Ajey - Throw custom exception !!!
                        }
                        catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e)
                        {
                          LOG.error("An error occurred while executing method.", e);
                          throw new RuntimeException(e); //TODO: Ajey - Throw custom exception !!!
                        }
                      }
                    })
                    .transform(Transformers.serializer())
                    .logAndReply();
                    
  }
  
  private final Object serviceInstance;
  private final SimpleMessageListenerContainer messageListenerContainer;
  private final static Logger LOG = LoggerFactory.getLogger(IntegrationFlowFactoryBean.class);
}

