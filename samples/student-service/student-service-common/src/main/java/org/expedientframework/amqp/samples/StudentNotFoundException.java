/********************************************************************
 * File Name:    StudentNotFoundException.java
 *
 * Date Created: Jan 29, 2019
 *
 * ------------------------------------------------------------------
 * 
 * Copyright (c) 2019 ajeydudhe@gmail.com
 *
 *******************************************************************/

package org.expedientframework.amqp.samples;
  
/**
 * TODO: Update with a detailed description of the interface/class.
 *
 */
public class StudentNotFoundException extends RuntimeException
{
  public StudentNotFoundException(final Integer identifier)
  {
    super(String.format("Student with identifier '%d' not found.", identifier));
  }

  private static final long serialVersionUID = 2521900240056311982L;  
}

