/********************************************************************
 * File Name:    Student.java
 *
 * Date Created: Dec 16, 2018
 *
 * ------------------------------------------------------------------
 * 
 * Copyright (c) 2018 ajeydudhe@gmail.com
 *
 *******************************************************************/

package org.expedientframework.amqp.samples;

import java.io.Serializable;

public class Student implements Serializable
{
  public Student(final String firstName, final String lastName)
  {
    this.firstName = firstName;
    this.lastName = lastName;
  }
  
  public String getFirstName()
  {
    return this.firstName;
  }
  
  public void setFirstName(final String firstName)
  {
    this.firstName = firstName;
  }
  
  public String getLastName()
  {
    return this.lastName;
  }
  
  public void setLastName(final String lastName)
  {
    this.lastName = lastName;
  }
  
  public Integer getIdentifier()
  {
    return this.identifier;
  }
  
  public void setIdentifier(final Integer identifier)
  {
    this.identifier = identifier;
  }

  @Override
  public String toString()
  {
    return String.format("%s %s", this.firstName, this.lastName);
  }
  
  private String firstName;
  private String lastName;
  private Integer identifier;
  private static final long serialVersionUID = 7302041168900014921L;
}

