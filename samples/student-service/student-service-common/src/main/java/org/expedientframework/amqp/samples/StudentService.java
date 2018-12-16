/********************************************************************
 * File Name:    StudentService.java
 *
 * Date Created: Dec 16, 2018
 *
 * ------------------------------------------------------------------
 * 
 * Copyright (c) 2018 ajeydudhe@gmail.com
 *
 *******************************************************************/

package org.expedientframework.amqp.samples;
  
public interface StudentService
{
  public Integer add(final Student student);
  public Student get(final Integer studentId);
  public Student search(final String firstName, final String lastName);
}

