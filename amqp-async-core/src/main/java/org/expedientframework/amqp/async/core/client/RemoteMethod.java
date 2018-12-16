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

/**
 * 
 * FOR INTERNAL USE ONLY.
 *
 */
public class RemoteMethod
{
  private RemoteMethod(final Method method, final Object[] arguments)
  {
    this.method = method;
    this.arguments = arguments;
  }
  
  public Method getMethod()
  {
    return method;
  }

  public Object[] getArguments()
  {
    return arguments;
  }

  public static void save(final Method method, final Object[] arguments)
  {
    REMOTE_METHOD.set(new RemoteMethod(method, arguments));
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
  
  private final static ThreadLocal<RemoteMethod> REMOTE_METHOD = new ThreadLocal<>();
}

