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
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.context.IntegrationFlowContext;

@SpringBootApplication
@IntegrationComponentScan
@EnableIntegration
@ComponentScan(basePackages= {"org.expedientframework.amqp.samples"})
@ComponentScan(basePackageClasses=org.expedientframework.amqp.async.core.beans.BeansGenerator.class)
public class Main implements CommandLineRunner
{
  public static void main(String[] args)
  {
    SpringApplication.run(Main.class, args);
  }

  @Override
  public void run(String... args) throws Exception
  {
    LOG.info("Running server..." + integrationFlow);
    
    this.integrationFlowContext.registration(this.integrationFlow).register();
  }
  
  // Private members
  @Inject
  private IntegrationFlowContext integrationFlowContext;
  
  @Inject
  private IntegrationFlow integrationFlow;
  
  private static final Logger LOG = LoggerFactory.getLogger(Main.class);
}