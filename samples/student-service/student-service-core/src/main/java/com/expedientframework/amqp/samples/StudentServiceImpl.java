/********************************************************************
 * File Name:    StudentServiceImpl.java
 *
 * Date Created: Dec 16, 2018
 *
 * ------------------------------------------------------------------
 * 
 * Copyright (c) 2018 ajeydudhe@gmail.com
 *
 *******************************************************************/

package com.expedientframework.amqp.samples;

import java.util.Random;

public class StudentServiceImpl implements StudentService
{
  @Override
  public Integer add(final Student student)
  {
    return RANDOM.nextInt();
  }

  @Override
  public Student get(final Integer studentId)
  {
    final Student student = new Student("firstName_" + studentId, "lastName_" + studentId);
    student.setIdentifier(studentId);
    
    return student;
  }

  @Override
  public Student search(final String firstName, final String lastName)
  {
    final Student student = new Student(firstName, lastName);
    
    student.setIdentifier(RANDOM.nextInt());

    return student;
  }

  private final static Random RANDOM = new Random();
}

