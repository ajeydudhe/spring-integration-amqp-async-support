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
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.integration.dsl.Transformers;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.util.concurrent.ListenableFuture;

/**
 * TODO: Update with a detailed description of the interface/class.
 *
 */
public final class AsyncAmqpRemoteMethodExecutor
{
  public static <T> CompletableFuture<T> async(T resultType)
  {
    try
    {
      final RemoteMethod remoteMethod = RemoteMethod.get();
      
      LOG.info("Executing remotely: {}", remoteMethod.getMethod());
      
      final GenericMessage<Object[]> genericMessage = new GenericMessage<Object[]>(remoteMethod.getArguments());
      
      //TODO: Ajey - We should also pass the argument types so that the method look up on server side will work in case of method overloading.
      final Message payload = MessageBuilder.withBody(Transformers.serializer().doTransform(genericMessage))
                                            .setHeaderIfAbsent("remote_gateway_method", remoteMethod.getMethod().getName())
                                            .build();
      
      final CompletableFuture<T> futureResult = new CompletableFuture<>();
      
      //TODO: Ajey - Handle methods without parameters, handle method returning void etc.
      final ListenableFuture<Message> rabbitFuture = remoteMethod.getAsyncAmqpTemplate().sendAndReceive(payload);
      
      rabbitFuture.addCallback(result -> handleResult(result, futureResult),
                               exception -> handleException(exception, futureResult));
      
      return futureResult;
    }
    catch (Exception e)
    {
      LOG.error("An error occurred while invoking remote method.", e);
      throw new RuntimeException(e); //TODO: Ajey - Throw custom exception !!!
    }
    finally
    {
      RemoteMethod.clear();
    }
  }
  
  @SuppressWarnings("unchecked")
  private static <T> void handleResult(final Message result, final CompletableFuture<T> futureResult)
  {
    try
    {
      LOG.info("Received results for remote method: {}", RemoteMethod.get().getMethod());
      
      final GenericMessage<byte[]> resultPayload = new GenericMessage<>(result.getBody());
      final Object finalResult = Transformers.deserializer().doTransform(resultPayload);
      
      futureResult.complete((T) finalResult);
    }
    catch (Exception e)
    {
      LOG.error("An error occurred while processing async results.", e);
      throw new RuntimeException(e); //TODO: Ajey - Throw custom exception !!!
    }
  }

  private static <T> void handleException(final Throwable exception, final CompletableFuture<T> futureResult)
  {
    LOG.error("Received exception for remote method: {}", RemoteMethod.get().getMethod());
    futureResult.completeExceptionally(exception);
  }

  private static final Logger LOG = LoggerFactory.getLogger(AsyncAmqpRemoteMethodExecutor.class);
}

