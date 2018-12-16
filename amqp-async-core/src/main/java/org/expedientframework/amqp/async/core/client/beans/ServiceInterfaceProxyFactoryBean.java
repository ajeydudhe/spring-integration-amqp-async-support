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

package org.expedientframework.amqp.async.core.client.beans;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import org.expedientframework.amqp.async.core.client.RemoteMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.FactoryBean;

public class ServiceInterfaceProxyFactoryBean implements FactoryBean<Object>, InvocationHandler
{
  public ServiceInterfaceProxyFactoryBean(final Class<?> serviceInterfaceType)
  {
    this.serviceInterfaceType = serviceInterfaceType;
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
    RemoteMethod.save(method, arguments);
    
    return null;
  }

  private final Class<?> serviceInterfaceType;
  private static final Logger LOG = LoggerFactory.getLogger(ServiceInterfaceProxyFactoryBean.class);
}

