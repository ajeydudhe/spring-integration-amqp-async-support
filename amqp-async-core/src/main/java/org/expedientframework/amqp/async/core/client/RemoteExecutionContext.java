/********************************************************************
 * File Name:    RemoteExecutionContext.java
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
public class RemoteExecutionContext
{
  private RemoteExecutionContext(final Method method, final Object[] arguments, final AsyncAmqpTemplate asyncAmqpTemplate)
  {
    this.asyncAmqpTemplate = asyncAmqpTemplate;
    this.method = method;
    this.arguments = arguments;
  }
  
  public static void save(final Method method, final Object[] arguments, final AsyncAmqpTemplate asyncAmqpTemplate)
  {
    REMOTE_EXECUTION_CONTEXT.set(new RemoteExecutionContext(method, arguments, asyncAmqpTemplate));
  }
  
  public static void clear()
  {
    REMOTE_EXECUTION_CONTEXT.remove();
  }

  public static Method getMethod()
  {
    return get().method;
  }
  
  public static Object[] getArguments()
  {
    return get().arguments;
  }
  
  public static AsyncAmqpTemplate getAsyncAmqpTemplate()
  {
    return get().asyncAmqpTemplate;
  }
  
  private static RemoteExecutionContext get()
  {
    if(REMOTE_EXECUTION_CONTEXT.get() == null)
    {
      throw new IllegalStateException("No service interface method found.");
    }
    
    return REMOTE_EXECUTION_CONTEXT.get();
  }
  
  private final Method method;
  private final Object[] arguments;
  private final AsyncAmqpTemplate asyncAmqpTemplate;
  
  private final static ThreadLocal<RemoteExecutionContext> REMOTE_EXECUTION_CONTEXT = new ThreadLocal<>();
}

