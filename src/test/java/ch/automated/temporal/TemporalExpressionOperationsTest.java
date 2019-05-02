package ch.automated.temporal;

/*
 * The MIT License
 *
 * Copyright 2019-03-01 automated.ch, Tobi Tiggers.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */


import ch.automated.temporal.predefined.CH_PublicHolidays;
import ch.automated.temporal.operations.Difference;
import ch.automated.temporal.operations.Intersection;
import ch.automated.temporal.operations.Union;
import ch.automated.temporal.ranges.DayToDayInYear;
import ch.automated.temporal.ranges.WeekDayToWeekDayRange;
import ch.automated.temporal.ranges.WeekDayInMonth;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import static org.junit.Assert.*;
import org.junit.Test;

/**
 * specifies a weekday in a month . e.g the first monday, sencond thursday in a
 * month
 *
 * @author wit
 */
public class TemporalExpressionOperationsTest {


    /**
     * Test {@link Union} operation.
     */
    @Test
    public void unionWeekDayInMonthExpressionTest() {

        Union mon13 = new Union();
        mon13.add(new WeekDayInMonth(DayOfWeek.MONDAY, 1));
        mon13.add(new WeekDayInMonth(DayOfWeek.MONDAY, 3));

        LocalDateTime isInInterval = LocalDateTime.of(2019, Month.FEBRUARY, 4, 14, 53);
        assertTrue("Date should match!", mon13.includes(isInInterval));

        LocalDateTime isNotInInterval = LocalDateTime.of(2019, Month.FEBRUARY, 11, 14, 53);
        assertFalse("Date should match!", mon13.includes(isNotInInterval));

    }

    @Test
    public void intersectionRangeEachYearTest() {

        Intersection winterIsComing = new Intersection();
        winterIsComing.add(new DayToDayInYear(Month.MARCH, Month.SEPTEMBER));
        winterIsComing.add(new DayToDayInYear(Month.JULY, Month.DECEMBER));

        LocalDateTime thisIswinter = LocalDateTime.of(2019, Month.FEBRUARY, 4, 14, 53);
        assertFalse("Date should NOT match!", winterIsComing.includes(thisIswinter));

        LocalDateTime thisIsNotWinter = LocalDateTime.of(2019, Month.AUGUST, 4, 14, 53);
        assertTrue("Date should match!", winterIsComing.includes(thisIsNotWinter));
    }

    
    /**                
     * 
     * 
     *                     
     *                    /
     *                  /
     *                 |
     *              United
     *                 |
     *           --------------      
     *          /              \
     *         /                \
     *        |                  | 
     * WeekDayInMonth      WeekDayInMonth
     * 
     */


    /**
     * This testcase is inspired by the referenced resouces.
     */
    @Test
    public void streetCleaningTest() {

        List<TemporalExpression> teaLeafs = new ArrayList<>();
        teaLeafs.add(new WeekDayInMonth(DayOfWeek.MONDAY, 1));
        teaLeafs.add(new WeekDayInMonth(DayOfWeek.MONDAY, 3));
        
        Union mon13 = buildUnionExpression(teaLeafs);

        Intersection nonWinterMons = new Intersection();
        nonWinterMons.add(mon13);
        nonWinterMons.add(new DayToDayInYear(Month.MARCH, Month.SEPTEMBER));

        Union holidays = new Union();
        holidays.add(new WeekDayInMonth(DayOfWeek.MONDAY, 3));//third monday of the month
        holidays.add(new WeekDayInMonth(DayOfWeek.TUESDAY, 2));//second tuesday of the month\

        Difference differenceTE = new Difference(nonWinterMons, holidays);
        differenceTE.setName("PandaWecker");

        LocalDateTime isInTnterval = LocalDateTime.of(2019, Month.APRIL, 1, 14, 53);
        assertTrue("Date should match!", differenceTE.includes(isInTnterval));

        LocalDateTime isNotInInterval = LocalDateTime.of(2019, Month.FEBRUARY, 4, 14, 53);
        assertFalse("Date should NOT match!", differenceTE.includes(isNotInInterval));

        /*
        SerializationService.serializeTemporalExpression(differenceTE);
        
        List<TemporalExpression> deserializeTemporalExpression = SerializationService.deserializeTemporalExpression();
        
        if(deserializeTemporalExpression.size() > 0){
            
            boolean includes = deserializeTemporalExpression.get(0).includes(isNotInInterval);
            assertFalse("Date should NOT match!", includes);
            assertTrue("PandaWecker", "PandaWecker".equals(deserializeTemporalExpression.get(0).getName()));
            System.out.println(differenceTE.info());
            CH_PublicHolidays.ch_ch_holidays.getName();
        }
      */
                
                
    }
    
    @Test
    public void createWecker(){
        
        // not during holidays
        TemporalExpression excludeHolidays = CH_PublicHolidays.ch_ch_holidays;
        
        WeekDayToWeekDayRange workingDays = new WeekDayToWeekDayRange(DayOfWeek.MONDAY, DayOfWeek.FRIDAY, 0, 23);
        
        
        Difference diff = new Difference(workingDays, excludeHolidays);
        
          // SerializationService.serializeTemporalExpression(differenceTE);
    }

    /**
     * This test covers {@link Difference}
     */
    @Test
    public void differenceOperationTest(){

        WeekDayToWeekDayRange workingDays = new WeekDayToWeekDayRange(DayOfWeek.MONDAY, DayOfWeek.FRIDAY, 6, 19);

        Difference workNoWithHolidays = new Difference(workingDays, CH_PublicHolidays.ch_ch_holidays);
        workNoWithHolidays.setName("Workdays without holidays");
        
        LocalDateTime of = LocalDateTime.of(2019, Month.FEBRUARY, 4, 5, 59); // monday
        boolean includesNot = workNoWithHolidays.includes(of);
        assertFalse("Date should NOT match!", includesNot);
        
        LocalDateTime in = LocalDateTime.of(2019, Month.FEBRUARY, 4, 6, 0); // monday
        boolean includes = workNoWithHolidays.includes(in);
        assertTrue("Date should match!", includes);
        
        LocalDateTime isInInterval = LocalDateTime.of(2019, Month.FEBRUARY, 15, 18, 59); // friday
        boolean includesInInterval = workNoWithHolidays.includes(isInInterval);
        assertTrue("Date should match!", includesInInterval);
        
        LocalDateTime isNotInInterval = LocalDateTime.of(2019, Month.FEBRUARY, 15, 20, 0); // friday
        boolean includesIsNotInInterval = workNoWithHolidays.includes(isNotInInterval);
        assertFalse("Date should NOT match!", includesIsNotInInterval);
        
        LocalDateTime isPublicHoliday = LocalDateTime.of(2019, Month.AUGUST, 1, 20, 0); // friday
        boolean includesNotPublicHoliday = workNoWithHolidays.includes(isPublicHoliday);
        assertFalse("Date should NOT match!", includesNotPublicHoliday);
        
         LocalDateTime isNoPublicHoliday = LocalDateTime.of(2019, Month.AUGUST, 2, 12, 4); // Thursday
        boolean includesPublicHoliday = workNoWithHolidays.includes(isNoPublicHoliday);
        assertTrue("Date should match!", includesPublicHoliday);
       
    }

   public Union buildUnionExpression(List<TemporalExpression> te) {
        Union mon13 = new Union();
        for (TemporalExpression temporalExpression : te) {
            mon13.add(temporalExpression);
        }
        return mon13;
    }
   
   
   
   @Test
   public void testplayGroud(){
       
        Calendar instance = Calendar.getInstance();
        
        int currentMinute = instance.get(Calendar.MINUTE);
        int currentHour = instance.get(Calendar.HOUR_OF_DAY);
        
     
        int eventMinute = 7;
        int eventHour = 6;
        
        LocalDateTime now = LocalDateTime.now();
        
        boolean isTomorrow=false;
        // if execution is due tomorrow check if event is due tomorrow
        if(currentHour > eventHour  || (currentHour == eventHour && currentMinute > eventMinute)){
            now.plus(24, ChronoUnit.HOURS);
            isTomorrow=true;
        }
       
        
        assertTrue("should be true", isTomorrow);
       
       
   }
   

}
