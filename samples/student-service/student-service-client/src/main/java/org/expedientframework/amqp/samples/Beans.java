/********************************************************************
 * File Name:    Beans.java
 *
 * Date Created: Dec 16, 2018
 *
 * ------------------------------------------------------------------
 * 
 * Copyright (c) 2018 ajeydudhe@gmail.com
 *
 *******************************************************************/

package org.expedientframework.amqp.samples;

import java.util.Arrays;
import java.util.List;

import org.expedientframework.amqp.async.core.client.beans.ServiceInterfaceProvider;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Beans
{
  @Bean
  public ServiceInterfaceProvider serviceInterfaceProvider()
  {
    return new ServiceInterfaceProvider()
    {      
      @Override
      public List<Class<?>> serviceInterfaces()
      {
        return Arrays.asList(StudentService.class);
      }
    };
  }
  
  @Bean
  public ConnectionFactory rabbitConnectionFactory()
  {
    final CachingConnectionFactory factory = new CachingConnectionFactory();
    
    factory.setAddresses("localhost");
    factory.setPort(5672);
    
    return factory;
  }
}

