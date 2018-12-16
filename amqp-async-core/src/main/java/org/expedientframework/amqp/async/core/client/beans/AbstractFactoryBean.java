/********************************************************************
 * File Name:    AbstractFactoryBean.java
 *
 * Date Created: Dec 17, 2018
 *
 * ------------------------------------------------------------------
 * 
 * Copyright (c) 2018 ajeydudhe@gmail.com
 *
 *******************************************************************/

package org.expedientframework.amqp.async.core.client.beans;

import org.springframework.beans.factory.FactoryBean;

/**
 * TODO: Update with a detailed description of the interface/class.
 *
 */
public abstract class AbstractFactoryBean<T> implements FactoryBean<T>
{
  protected AbstractFactoryBean(final Class<?> serviceInterfaceType)
  {
    this.serviceInterfaceType = serviceInterfaceType;
  }
  
  protected final Class<?> serviceInterfaceType;
}

