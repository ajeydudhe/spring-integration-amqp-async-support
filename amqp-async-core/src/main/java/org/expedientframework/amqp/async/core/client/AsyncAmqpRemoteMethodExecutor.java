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

import java.util.concurrent.CompletableFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * TODO: Update with a detailed description of the interface/class.
 *
 */
public final class AsyncAmqpRemoteMethodExecutor
{
  public static <T> CompletableFuture<T> async(T resultType)
  {
    final RemoteMethod remoteMethod = RemoteMethod.get();
    
    LOG.info("Executing remotely: {}", remoteMethod.getMethod());
    LOG.info("AsyncAmqpTemplate: {}", remoteMethod.getAsyncAmqpTemplate());
    
    return null;
  }
  
  private static final Logger LOG = LoggerFactory.getLogger(AsyncAmqpRemoteMethodExecutor.class);
}

