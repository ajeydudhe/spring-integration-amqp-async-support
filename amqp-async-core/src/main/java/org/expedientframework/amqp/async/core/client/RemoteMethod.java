/********************************************************************
 * File Name:    RemoteMethod.java
 *
 * Date Created: Dec 16, 2018
 *
 * ------------------------------------------------------------------
 * 
 * Copyright (c) 2018 ajeydudhe@gmail.com
 *
 *******************************************************************/

package org.expedientframework.amqp.async.core.client;

import java.lang.reflect.Method;

import org.springframework.amqp.core.AsyncAmqpTemplate;

/**
 * 
 * FOR INTERNAL USE ONLY.
 *
 */
public class RemoteMethod
{
  private RemoteMethod(final Method method, final Object[] arguments, final AsyncAmqpTemplate asyncAmqpTemplate)
  {
    this.asyncAmqpTemplate = asyncAmqpTemplate;
    this.method = method;
    this.arguments = arguments;
  }
  
  public AsyncAmqpTemplate getAsyncAmqpTemplate()
  {
    return this.asyncAmqpTemplate;
  }
  
  public Method getMethod()
  {
    return method;
  }

  public Object[] getArguments()
  {
    return arguments;
  }

  public static void save(final Method method, final Object[] arguments, final AsyncAmqpTemplate asyncAmqpTemplate)
  {
    REMOTE_METHOD.set(new RemoteMethod(method, arguments, asyncAmqpTemplate));
  }
  
  public static RemoteMethod get()
  {
    return REMOTE_METHOD.get();
  }
  
  public static void clear()
  {
    REMOTE_METHOD.remove();
  }
  
  private final Method method;
  private final Object[] arguments;
  private final AsyncAmqpTemplate asyncAmqpTemplate;
  
  private final static ThreadLocal<RemoteMethod> REMOTE_METHOD = new ThreadLocal<>();
}

