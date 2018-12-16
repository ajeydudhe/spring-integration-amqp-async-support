/********************************************************************
 * File Name:    BeanDefinitions.java
 *
 * Date Created: Dec 16, 2018
 *
 * ------------------------------------------------------------------
 * 
 * Copyright (c) 2018 ajeydudhe@gmail.com
 *
 *******************************************************************/

package org.expedientframework.amqp.async.core.client.beans;

import org.expedientframework.amqp.async.core.client.AsyncAmqpRemoteMethodExecutor;
import org.springframework.amqp.core.AsyncAmqpTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * TODO: Update with a detailed description of the interface/class.
 *
 */
@Configuration
public class BeanDefinitions
{
  @Bean
  public AsyncAmqpRemoteMethodExecutor asyncAmqpServiceMethodExecutor(final AsyncAmqpTemplate asyncAmqpTemplate)
  {
    return new AsyncAmqpRemoteMethodExecutor(asyncAmqpTemplate);
  }
}

