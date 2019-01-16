/********************************************************************
 * File Name:    ServiceInterfaceProxyFactoryBean.java
 *
 * Date Created: Dec 16, 2018
 *
 * ------------------------------------------------------------------
 * 
 * Copyright (c) 2018 ajeydudhe@gmail.com
 *
 *******************************************************************/

package org.expedientframework.amqp.async.core.client;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import org.expedientframework.amqp.async.core.beans.AbstractFactoryBean;
import org.springframework.amqp.core.AsyncAmqpTemplate;

public class ServiceInterfaceProxyFactoryBean extends AbstractFactoryBean<Object> implements InvocationHandler
{
  public ServiceInterfaceProxyFactoryBean(final Class<?> serviceInterfaceType, final AsyncAmqpTemplate asyncAmqpTemplate)
  {
    super(serviceInterfaceType);
    
    this.asyncAmqpTemplate = asyncAmqpTemplate;
  }
  
  @Override
  public Object getObject() throws Exception
  {
    return Proxy.newProxyInstance(this.serviceInterfaceType.getClassLoader(), 
                                  new Class<?>[] {this.serviceInterfaceType},
                                  this) ;
  }

  @Override
  public Class<?> getObjectType()
  {
    return this.serviceInterfaceType;
  }

  @Override
  public Object invoke(final Object proxy, final Method method, final Object[] arguments) throws Throwable
  {
    RemoteExecutionContext.save(method, arguments, this.asyncAmqpTemplate);
    
    return null;
  }

  private final AsyncAmqpTemplate asyncAmqpTemplate;
  //private static final Logger LOG = LoggerFactory.getLogger(ServiceInterfaceProxyFactoryBean.class);
}

