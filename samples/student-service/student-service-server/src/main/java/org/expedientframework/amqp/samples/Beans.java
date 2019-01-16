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

import org.expedientframework.amqp.async.core.beans.ServiceInterfaceProvider;
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
}

