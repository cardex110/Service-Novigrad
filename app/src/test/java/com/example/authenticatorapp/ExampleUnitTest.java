package com.example.authenticatorapp;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class ExampleUnitTest {
    
    public static final String VALID= "Valid content";
    public static final String INVALID="Invalid,content invalid";

    @Test
    public void testWorkingHour1(){
        Activity_Hours_Day hoursDayContext = new Activity_Hours_Day();
        String result = hoursDayContext.validateTimeInferior("06:23","17:34");
        assertThat(result, is(VALID));
    }

    @Test
    public void testWorkingHour2(){
        Activity_Hours_Day hoursDayContext = new Activity_Hours_Day();
        String result = hoursDayContext.validateTimeInferior("13:09","02:34");
        assertThat(result, is(INVALID));
    }

    @Test
    public void testNumberName(){
        Service serviceToTest = new Service("123","","","","","");
        adminWelcome serviceToTestContext = new adminWelcome();
        String result = serviceToTestContext.validateName(serviceToTest);
        assertThat(result, is(INVALID));
    }

    @Test
    public void testNotNumberName(){
        Service serviceToTest = new Service("NotNumberService","","","","","");
        adminWelcome serviceToTestContext = new adminWelcome();
        String result = serviceToTestContext.validateName(serviceToTest);
        assertThat(result, is(VALID));
    }

    @Test
    public void testForm(){
        Service serviceToTest = new Service("","logicTest","","","","");
        adminWelcome serviceToTestContext = new adminWelcome();
        String result = serviceToTestContext.validateForm(serviceToTest);
        assertThat(result, is(VALID));
    }

    @Test
    public void testEmailGood(){
        String email = "badEmail";
        adminWelcome test = new adminWelcome();
        String result = test.validateDeleteUser(email);
        assertThat(result, is(INVALID));
    }

    @Test
    public void testFalseEmail(){
        String email = "goodEmail@gmail.com";
        adminWelcome test = new adminWelcome();
        String result = test.validateDeleteUser(email);
        assertThat(result, is(VALID));
    }

    }
