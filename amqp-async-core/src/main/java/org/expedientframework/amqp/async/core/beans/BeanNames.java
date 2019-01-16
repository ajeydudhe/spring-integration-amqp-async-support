/********************************************************************
 * File Name:    BeanNames.java
 *
 * Date Created: Dec 16, 2018
 *
 * ------------------------------------------------------------------
 * 
 * Copyright (c) 2018 ajeydudhe@gmail.com
 *
 *******************************************************************/

package org.expedientframework.amqp.async.core.beans;
  
/**
 * TODO: Update with a detailed description of the interface/class.
 *
 */
public class BeanNames
{
  public static String generateName(final Class<?> type, final String prefix)
  {
    return prefix + "-" + type.getName();
  }
  
  public static String exchange(final Class<?> type)
  {
    return generateName(type, "exchange");
  }

  public static String asyncAmqpTemplate(final Class<?> type)
  {
    return generateName(type, "asyncAmqpTemplate");
  }

  public static String messageChannel(final Class<?> type)
  {
    return generateName(type, "messageChannel");
  }
}

