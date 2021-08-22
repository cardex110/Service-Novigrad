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

public class UnitTestDeliverable4 {
    public static final String VALID= "Valid content";
    public static final String INVALID="Invalid,content invalid";

    @Test
    public void testValidDate(){
        Book context = new Book();
        Boolean result = context.validDate(2021,10,12);
        assertTrue(result);
    }

    @Test
    public void testInvalidDate(){
        Book context = new Book();
        Boolean result = context.validDate(2020,8,15);
        assertFalse(result);
    }

    @Test
    public void testValidComment(){
        Rate_Activity context = new Rate_Activity();
        Boolean result = context.checkComment("awesome job");
        assertTrue(result);
    }

    @Test
    public void testInvalidComment(){
        Rate_Activity context = new Rate_Activity();
        Boolean result = context.checkComment("23425453");
        assertFalse(result);
    }

    @Test
    public void testValidHourToSearch(){
        Search_By_Hours context = new Search_By_Hours();
        String result = context.checkHourValidToSearch("10:45");
        assertThat(result, is(VALID));
    }

    @Test
    public void testInvalidHourToSearch(){
        Search_By_Hours context = new Search_By_Hours();
        String result = context.checkHourValidToSearch("ten fourthy five");
        assertThat(result, is(INVALID));
    }

    @Test
    public void testValidServiceToSearch(){
        Search_By_Services context = new Search_By_Services();
        String result = context.checkServiceValidToSearch("Health Card");
        assertThat(result, is(VALID));
    }

    @Test
    public void testInvalidServiceToSearch(){
        Search_By_Services context = new Search_By_Services();
        String result = context.checkServiceValidToSearch("21234234");
        assertThat(result, is(INVALID));
    }

    @Test
    public void testInvalidAdressToSearch(){
        Search_By_Adress context = new Search_By_Adress();
        Boolean result = context.checkAdressToSearch("Not a correct #$%");
        assertTrue(result);
    }

    @Test
    public void testValidAdressToSearch(){
        Search_By_Adress context = new Search_By_Adress();
        Boolean result = context.checkAdressToSearch("123 valid adresse ottawa");
        assertTrue(result);
    }


}
