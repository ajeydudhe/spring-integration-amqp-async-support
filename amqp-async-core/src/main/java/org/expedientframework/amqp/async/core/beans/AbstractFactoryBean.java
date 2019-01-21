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

package org.expedientframework.amqp.async.core.beans;

/**
 * TODO: Update with a detailed description of the interface/class.
 *
 */
public abstract class AbstractFactoryBean<T> extends org.springframework.beans.factory.config.AbstractFactoryBean<T>
{
  protected AbstractFactoryBean(final Class<?> serviceInterfaceType)
  {
    this.serviceInterfaceType = serviceInterfaceType;
  }
  
  protected final Class<?> serviceInterfaceType;
}

