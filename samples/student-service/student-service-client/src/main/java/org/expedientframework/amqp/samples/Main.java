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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.expedientframework.amqp.async.core.client.beans.ClientBeansGenerator;

@SpringBootApplication
@IntegrationComponentScan
@ComponentScan(basePackageClasses= {ClientBeansGenerator.class, Beans.class})
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
  }
  
  private static final Logger LOG = LoggerFactory.getLogger(Main.class);
}

