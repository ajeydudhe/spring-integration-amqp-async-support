/********************************************************************
 * File Name:    Main.java
 *
 * Date Created: Dec 16, 2018
 *
 * ------------------------------------------------------------------
 * 
 * Copyright (c) 2018 ajeydudhe@gmail.com
 *
 *******************************************************************/

package org.expedientframework.amqp.samples;

import javax.inject.Inject;

import org.expedientframework.amqp.async.core.client.AsyncAmqpRemoteMethodExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.integration.annotation.IntegrationComponentScan;

@SpringBootApplication
@IntegrationComponentScan
@ComponentScan(basePackages= {"org.expedientframework.amqp.async.core.client.beans", "org.expedientframework.amqp.samples"})
public class Main implements CommandLineRunner
{
  public static void main(final String[] args)
  {
    SpringApplication.run(Main.class, args);
  }

  @Override
  public void run(final String... args) throws Exception
  {
    LOG.info("Starting client...");
    
    async.execute(studentService.get(1234));
  }
  
  @Inject
  public void setStudentService(final StudentService studentService)
  {
    this.studentService = studentService;
  }
  
  @Inject
  public void setAsyncAmqpRemoteMethodExecutor(final AsyncAmqpRemoteMethodExecutor asyncAmqpRemoteMethodExecutor)
  {
    this.async = asyncAmqpRemoteMethodExecutor;
  }
  
  private StudentService studentService;
  private AsyncAmqpRemoteMethodExecutor async;
  private static final Logger LOG = LoggerFactory.getLogger(Main.class);
}

