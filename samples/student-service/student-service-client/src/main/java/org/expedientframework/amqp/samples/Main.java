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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.integration.annotation.IntegrationComponentScan;

import static org.expedientframework.amqp.async.core.client.AsyncAmqpRemoteMethodExecutor.async;

import java.io.IOException;

@SpringBootApplication
@IntegrationComponentScan
//@ComponentScan(basePackages= {"org.expedientframework.amqp.async.core.client.beans", "org.expedientframework.amqp.samples"})
@ComponentScan(basePackages= {"org.expedientframework.amqp.samples"})
@ComponentScan(basePackageClasses=org.expedientframework.amqp.async.core.client.ClientBeansGenerator.class)
public class Main implements CommandLineRunner
{
  public static void main(final String[] args) throws IOException
  {
    SpringApplication.run(Main.class, args);
  }

  @Override
  public void run(final String... args) throws Exception
  {
    LOG.info("Starting client...");
    
    async(studentService.get(1234)).handle((student, exception) -> {
      
      LOG.info("Received async result...");
      
      return student;
    });
    
    LOG.info("Waiting for async call to complete...");
  }
  
  @Inject
  public void setStudentService(final StudentService studentService)
  {
    this.studentService = studentService;
  }
  
  private StudentService studentService;
  private static final Logger LOG = LoggerFactory.getLogger(Main.class);
}

