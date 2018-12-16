/********************************************************************
 * File Name:    AsyncAmqpRemoteMethodExecutor.java
 *
 * Date Created: Dec 16, 2018
 *
 * ------------------------------------------------------------------
 * 
 * Copyright (c) 2018 ajeydudhe@gmail.com
 *
 *******************************************************************/

package org.expedientframework.amqp.async.core.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * TODO: Update with a detailed description of the interface/class.
 *
 */
public final class AsyncAmqpRemoteMethodExecutor
{
  public <T> T execute(T expectedResult)
  {
    final RemoteMethod remoteMethod = RemoteMethod.get();
    
    LOG.info("Executing remotely: {}", remoteMethod.getMethod());
    
    return null;
  }
  
  private static final Logger LOG = LoggerFactory.getLogger(AsyncAmqpRemoteMethodExecutor.class);
}

