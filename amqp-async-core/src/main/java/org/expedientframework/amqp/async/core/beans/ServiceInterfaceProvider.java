/********************************************************************
 * File Name:    ServiceInterfaceProvider.java
 *
 * Date Created: Dec 16, 2018
 *
 * ------------------------------------------------------------------
 * 
 * Copyright (c) 2018 ajeydudhe@gmail.com
 *
 *******************************************************************/

package org.expedientframework.amqp.async.core.beans;

import java.util.List;

public interface ServiceInterfaceProvider
{
  public List<Class<?>> clients();
  public List<Class<?>> servers();
}

