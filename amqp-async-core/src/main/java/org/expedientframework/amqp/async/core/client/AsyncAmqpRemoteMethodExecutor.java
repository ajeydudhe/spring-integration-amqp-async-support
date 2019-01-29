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

import java.lang.reflect.Method;
import java.util.concurrent.CompletableFuture;

import org.expedientframework.amqp.async.core.exceptions.RemoteMethodExecutionException;
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
      final Method remoteMethod = RemoteExecutionContext.getMethod();
      LOG.info("Executing remotely: {}", remoteMethod);
      
      final GenericMessage<Object[]> genericMessage = new GenericMessage<Object[]>(RemoteExecutionContext.getArguments());
      
      //TODO: Ajey - We should also pass the argument types so that the method look up on server side will work in case of method overloading.
      final Message payload = MessageBuilder.withBody(Transformers.serializer().doTransform(genericMessage))
                                            .setHeaderIfAbsent("remote_gateway_method", remoteMethod.getName())
                                            .build();
      
      final CompletableFuture<T> futureResult = new CompletableFuture<>();
      
      //TODO: Ajey - Handle methods without parameters, handle method returning void etc.
      final ListenableFuture<Message> rabbitFuture = RemoteExecutionContext.getAsyncAmqpTemplate().sendAndReceive(payload);
      
      LOG.info("sendAndReceive() called with payload...");
      
      rabbitFuture.addCallback(result -> handleResult(result, futureResult, remoteMethod),
                               exception -> handleException(exception, futureResult, remoteMethod));
      
      return futureResult;
    }
    catch (Exception e)
    {
      LOG.error("An error occurred while invoking remote method.", e);
      throw new RuntimeException(e); //TODO: Ajey - Throw custom exception !!!
    }
    finally
    {
      RemoteExecutionContext.clear();
    }
  }
  
  @SuppressWarnings("unchecked")
  private static <T> void handleResult(final Message result, 
                                       final CompletableFuture<T> futureResult,
                                       final Method remoteMethod)
  {
    try
    {
      LOG.info("Received results for remote method: {}", remoteMethod);
      
      final GenericMessage<byte[]> resultPayload = new GenericMessage<>(result.getBody());
      final Object finalResult = Transformers.deserializer().doTransform(resultPayload);
      
      if(finalResult instanceof RemoteMethodExecutionException)
      {
        final RemoteMethodExecutionException exception = (RemoteMethodExecutionException) finalResult;
        futureResult.completeExceptionally(exception.getCause());
      }
      
      futureResult.complete((T) finalResult);
    }
    catch (Exception e)
    {
      LOG.error("An error occurred while processing async results.", e);
      throw new RuntimeException(e); //TODO: Ajey - Throw custom exception !!!
    }
  }

  private static <T> void handleException(final Throwable exception, 
                                          final CompletableFuture<T> futureResult,
                                          final Method remoteMethod)
  {
    LOG.error("Received exception for remote method: {}", remoteMethod);
    futureResult.completeExceptionally(exception);
  }

  private static final Logger LOG = LoggerFactory.getLogger(AsyncAmqpRemoteMethodExecutor.class);
}

