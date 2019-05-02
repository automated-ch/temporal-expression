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


import ch.automated.temporal.ranges.DayToDayInYear;
import ch.automated.temporal.ranges.WeekDayToWeekDayRange;
import ch.automated.temporal.ranges.WeekDayInMonth;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.Month;
import static org.junit.Assert.*;
import org.junit.Test;

/**
 * specifies a weekday in a month . e.g the first monday, sencond thursday in a
 * month
 *
 * @author wit
 */
public class TemporalExpressionRangesTest {

    @Test
    public void weekDayInMonthExpressionTest() {
            WeekDayInMonth weekDayInMonthExpression = new WeekDayInMonth(DayOfWeek.MONDAY, 1);

        LocalDateTime isInInterval = LocalDateTime.of(2019, Month.FEBRUARY, 4, 14, 53);
        assertTrue("Date should match!", weekDayInMonthExpression.includes(isInInterval));
    }

    @Test
    public void rangeInYearTestOneMonthParam() {

        DayToDayInYear dayToDayInYearExpression = new DayToDayInYear(Month.FEBRUARY);

        /**
         * regular cases
         */
        LocalDateTime isNotInInterval = LocalDateTime.of(2019, Month.JULY, 4, 14, 53);
        assertFalse("Date should match!", dayToDayInYearExpression.includes(isNotInInterval));

        LocalDateTime isInInterval = LocalDateTime.of(2019, Month.FEBRUARY, 4, 14, 53);
        assertTrue("Date should match!", dayToDayInYearExpression.includes(isInInterval));

        /**
         * Edge cases begin o interval
         */
        LocalDateTime borderTestBeginIntervalNegative = LocalDateTime.of(2019, Month.JANUARY, 31, 23, 59);
        assertFalse("Date should match!", dayToDayInYearExpression.includes(borderTestBeginIntervalNegative));

        LocalDateTime borderTestBeginIntervalOne = LocalDateTime.of(2019, Month.FEBRUARY, 1, 0, 0);
        assertTrue("Sould be in interval!", dayToDayInYearExpression.includes(borderTestBeginIntervalOne));

        LocalDateTime borderTestBeginIntervaTwo = LocalDateTime.of(2019, Month.FEBRUARY, 1, 0, 1);
        assertTrue("Sould be in interval!", dayToDayInYearExpression.includes(borderTestBeginIntervaTwo));

        LocalDateTime borderTestBeginIntervaThree = LocalDateTime.of(2019, Month.FEBRUARY, 1, 1, 1);
        assertTrue("Sould be in interval!", dayToDayInYearExpression.includes(borderTestBeginIntervaThree));

        /**
         * Edge cases end o interval
         */
        LocalDateTime borderTestEndIntervalNegative = LocalDateTime.of(2019, Month.MARCH, 1, 0, 0);
        assertFalse("Date should match!", dayToDayInYearExpression.includes(borderTestEndIntervalNegative));

        LocalDateTime borderTestEndIntervalOne = LocalDateTime.of(2019, Month.FEBRUARY, 28, 0, 0);
        assertTrue("Sould be in interval!", dayToDayInYearExpression.includes(borderTestEndIntervalOne));

        LocalDateTime borderTestEndIntervaTwo = LocalDateTime.of(2019, Month.FEBRUARY, 28, 0, 1);
        assertTrue("Sould be in interval!", dayToDayInYearExpression.includes(borderTestEndIntervaTwo));

        LocalDateTime borderTestEndIntervaThree = LocalDateTime.of(2019, Month.FEBRUARY, 28, 23, 59);
        assertTrue("Sould be in interval!", dayToDayInYearExpression.includes(borderTestEndIntervaThree));

    }

    @Test
    public void rangeInYearTestTwoMonthParam() {

        DayToDayInYear dayToDayInYearExpressionTwoParams = new DayToDayInYear(Month.FEBRUARY, Month.FEBRUARY);

        /**
         * regular cases
         */
        LocalDateTime isInInterval = LocalDateTime.of(2019, Month.FEBRUARY, 4, 14, 53);
        assertTrue("Date should match!", dayToDayInYearExpressionTwoParams.includes(isInInterval));

        LocalDateTime isNotInInterval = LocalDateTime.of(2019, Month.JULY, 4, 14, 53);
        assertFalse("Date should match!", dayToDayInYearExpressionTwoParams.includes(isNotInInterval));

        /**
         * Edge cases begin of interval
         */
        LocalDateTime borderTestBeginIntervalNegative = LocalDateTime.of(2019, Month.JANUARY, 31, 23, 59);
        assertFalse("Date should match!", dayToDayInYearExpressionTwoParams.includes(borderTestBeginIntervalNegative));

        LocalDateTime borderTestBeginIntervalOne = LocalDateTime.of(2019, Month.FEBRUARY, 1, 0, 0);
        assertTrue("Sould be in interval!", dayToDayInYearExpressionTwoParams.includes(borderTestBeginIntervalOne));

        LocalDateTime borderTestBeginIntervaTwo = LocalDateTime.of(2019, Month.FEBRUARY, 1, 0, 1);
        assertTrue("Sould be in interval!", dayToDayInYearExpressionTwoParams.includes(borderTestBeginIntervaTwo));

        LocalDateTime borderTestBeginIntervaThree = LocalDateTime.of(2019, Month.FEBRUARY, 1, 1, 1);
        assertTrue("Sould be in interval!", dayToDayInYearExpressionTwoParams.includes(borderTestBeginIntervaThree));

        /**
         * Edge cases end of interval
         */
        LocalDateTime borderTestEndIntervalNegative = LocalDateTime.of(2019, Month.MARCH, 1, 0, 0);
        assertFalse("Date should match!", dayToDayInYearExpressionTwoParams.includes(borderTestEndIntervalNegative));

        LocalDateTime borderTestEndIntervalOne = LocalDateTime.of(2019, Month.FEBRUARY, 28, 0, 0);
        assertTrue("Sould be in interval!", dayToDayInYearExpressionTwoParams.includes(borderTestEndIntervalOne));

        LocalDateTime borderTestEndIntervaTwo = LocalDateTime.of(2019, Month.FEBRUARY, 28, 0, 1);
        assertTrue("Sould be in interval!", dayToDayInYearExpressionTwoParams.includes(borderTestEndIntervaTwo));

        LocalDateTime borderTestEndIntervaThree = LocalDateTime.of(2019, Month.FEBRUARY, 28, 23, 59);
        assertTrue("Sould be in interval!", dayToDayInYearExpressionTwoParams.includes(borderTestEndIntervaThree));

    }

    @Test
    public void rangeInYearTestTwoMonthParamWithDay() {

        DayToDayInYear dayToDayInYearExpressionTwoParams = new DayToDayInYear(Month.FEBRUARY, Month.FEBRUARY, 5, 20);

        /**
         * regular cases
         */
        LocalDateTime isNotInIntervalZreo = LocalDateTime.of(2019, Month.JANUARY, 4, 14, 53);
        assertFalse("Date should NOT match!", dayToDayInYearExpressionTwoParams.includes(isNotInIntervalZreo));

        LocalDateTime isNotInInterval = LocalDateTime.of(2019, Month.FEBRUARY, 4, 14, 53);
        assertFalse("Date should NOT match!", dayToDayInYearExpressionTwoParams.includes(isNotInInterval));

        LocalDateTime isNotInIntervaOnel = LocalDateTime.of(2019, Month.JULY, 4, 14, 53);
        assertFalse("Date should match!", dayToDayInYearExpressionTwoParams.includes(isNotInIntervaOnel));

        /**
         * Edge cases begin of interval
         */
        LocalDateTime borderTestBeginIntervalNegative = LocalDateTime.of(2019, Month.FEBRUARY, 4, 23, 59);
        assertFalse("Date should match!", dayToDayInYearExpressionTwoParams.includes(borderTestBeginIntervalNegative));

        LocalDateTime borderTestBeginIntervalOne = LocalDateTime.of(2019, Month.FEBRUARY, 5, 0, 0);
        assertTrue("Sould be in interval!", dayToDayInYearExpressionTwoParams.includes(borderTestBeginIntervalOne));

        LocalDateTime borderTestBeginIntervaTwo = LocalDateTime.of(2019, Month.FEBRUARY, 5, 0, 1);
        assertTrue("Sould be in interval!", dayToDayInYearExpressionTwoParams.includes(borderTestBeginIntervaTwo));

        LocalDateTime borderTestBeginIntervaThree = LocalDateTime.of(2019, Month.FEBRUARY, 5, 1, 1);
        assertTrue("Sould be in interval!", dayToDayInYearExpressionTwoParams.includes(borderTestBeginIntervaThree));

        /**
         * Edge cases end of interval
         */
        LocalDateTime borderTestEndIntervalOne = LocalDateTime.of(2019, Month.FEBRUARY, 20, 0, 0);
        assertTrue("Sould be in interval!", dayToDayInYearExpressionTwoParams.includes(borderTestEndIntervalOne));

        LocalDateTime borderTestEndIntervaTwo = LocalDateTime.of(2019, Month.FEBRUARY, 20, 0, 1);
        assertTrue("Sould be in interval!", dayToDayInYearExpressionTwoParams.includes(borderTestEndIntervaTwo));

        LocalDateTime borderTestEndIntervaThree = LocalDateTime.of(2019, Month.FEBRUARY, 20, 23, 59);
        assertTrue("Sould be in interval!", dayToDayInYearExpressionTwoParams.includes(borderTestEndIntervaThree));

        LocalDateTime borderTestEndIntervalZero = LocalDateTime.of(2019, Month.FEBRUARY, 21, 0, 0);
        assertFalse("Date should match!", dayToDayInYearExpressionTwoParams.includes(borderTestEndIntervalZero));

    }
    
    @Test
    public void rangeInYearMinutesIn(){
        
        //05.02.14:03 - 20.02. 18:34
        DayToDayInYear dayToDayInYearExpressionTwoParams = new DayToDayInYear(Month.FEBRUARY, Month.FEBRUARY, 5, 20, 15, 18,3,34);
        
        LocalDateTime borderTestEndIntervalOne = LocalDateTime.of(2019, Month.FEBRUARY, 19, 0, 0);
        assertTrue("Sould be in interval!", dayToDayInYearExpressionTwoParams.includes(borderTestEndIntervalOne));
          
    }
    
    @Test
    public void rangeInYearMinutesInEndDay(){
        
        //05.02.14:03 - 20.02. 18:34
        DayToDayInYear dayToDayInYearExpressionTwoParams = new DayToDayInYear(Month.FEBRUARY, Month.FEBRUARY, 5, 20, 15,18,3,34);
        
        LocalDateTime borderTestEndIntervalOne = LocalDateTime.of(2019, Month.FEBRUARY, 20, 23, 59);
        assertFalse("Sould be in interval!", dayToDayInYearExpressionTwoParams.includes(borderTestEndIntervalOne));
          
    }
    
    @Test
    public void rangeInYearMinutesOut(){
        
        //05.02.14:03 - 20.02. 18:34
        DayToDayInYear dayToDayInYearExpressionTwoParams = new DayToDayInYear(Month.FEBRUARY, Month.FEBRUARY, 5, 20, 15,18,3,34);
        
           
        LocalDateTime borderTestEndIntervalNot = LocalDateTime.of(2019, Month.FEBRUARY, 5, 14, 59);
        assertFalse("Sould be NOT be in interval!", dayToDayInYearExpressionTwoParams.includes(borderTestEndIntervalNot));
        
    }

    @Test
    public void rangeInWeekTestTwoDayParam() {

        WeekDayToWeekDayRange rangeInWeek = new WeekDayToWeekDayRange(DayOfWeek.MONDAY, DayOfWeek.THURSDAY);

        /**
         * regular cases
         */
        LocalDateTime isNotInInterval = LocalDateTime.of(2019, Month.FEBRUARY, 15, 14, 53); // friday
        assertFalse("Date should match!", rangeInWeek.includes(isNotInInterval));

        LocalDateTime isInInterval = LocalDateTime.of(2019, Month.FEBRUARY, 4, 14, 53); // monday
        assertTrue("Date should match!", rangeInWeek.includes(isInInterval));

        LocalDateTime isInIntervalButNotWithHour = LocalDateTime.of(2019, Month.FEBRUARY, 4, 11, 53); // monday, but it is to early
        assertTrue("Date should match!", rangeInWeek.includes(isInIntervalButNotWithHour));

        /**
         * Edge cases begin o interval
         */
        LocalDateTime outSideBeginInterval = LocalDateTime.of(2019, Month.FEBRUARY, 3, 23, 59); // sunday
        assertFalse("Date should match!", rangeInWeek.includes(outSideBeginInterval));

        LocalDateTime insideSideBeginIntervalOne = LocalDateTime.of(2019, Month.FEBRUARY, 4, 0, 0); // monday
        assertTrue("Date should match!", rangeInWeek.includes(insideSideBeginIntervalOne));

        LocalDateTime insideSideBeginIntervalTwo = LocalDateTime.of(2019, Month.FEBRUARY, 4, 1, 0); // monday
        assertTrue("Date should match!", rangeInWeek.includes(insideSideBeginIntervalTwo));

        LocalDateTime insideSideBeginIntervalThree = LocalDateTime.of(2019, Month.FEBRUARY, 4, 1, 1); // monday
        assertTrue("Date should match!", rangeInWeek.includes(insideSideBeginIntervalThree));

        /**
         * Edge cases end o interval
         */
        LocalDateTime outSideEndIntervalOne = LocalDateTime.of(2019, Month.FEBRUARY, 8, 23, 59);  // FRiday
        assertFalse("Date should match!", rangeInWeek.includes(outSideEndIntervalOne));

        LocalDateTime outSideEndIntervalTwo = LocalDateTime.of(2019, Month.FEBRUARY, 15, 23, 59); //FRiday
        assertFalse("Date should match!", rangeInWeek.includes(outSideEndIntervalTwo));

        LocalDateTime insideSideEndIntervalOne = LocalDateTime.of(2019, Month.FEBRUARY, 7, 0, 0); //Thursday
        assertTrue("Date should match!", rangeInWeek.includes(insideSideEndIntervalOne));

        LocalDateTime insideSideEndIntervalTwo = LocalDateTime.of(2019, Month.FEBRUARY, 7, 1, 0); //Thursday
        assertTrue("Date should match!", rangeInWeek.includes(insideSideEndIntervalTwo));

        LocalDateTime insideSideEndIntervalThree = LocalDateTime.of(2019, Month.FEBRUARY, 7, 23, 59); //Thrusday
        assertTrue("Date should match!", rangeInWeek.includes(insideSideEndIntervalThree));

    }

    @Test
    public void rangeInWeekTestFourDayParam() {

        WeekDayToWeekDayRange rangeInWeek = new WeekDayToWeekDayRange(DayOfWeek.MONDAY, DayOfWeek.THURSDAY, 15, 12);

        /**
         * regular cases
         */
        LocalDateTime isNotInInterval = LocalDateTime.of(2019, Month.FEBRUARY, 15, 14, 53); // friday
        assertFalse("Date should match!", rangeInWeek.includes(isNotInInterval));

        LocalDateTime isInInterval = LocalDateTime.of(2019, Month.FEBRUARY, 4, 13, 53); // monday
        assertFalse("Date should match!", rangeInWeek.includes(isInInterval));

        LocalDateTime isInIntervalButNotWithHour = LocalDateTime.of(2019, Month.FEBRUARY, 4, 11, 53); // monday, but it is to early
        assertFalse("Date should not match!", rangeInWeek.includes(isInIntervalButNotWithHour));

        /**
         * Edge cases begin o interval
         */
        LocalDateTime outSideBeginInterval = LocalDateTime.of(2019, Month.FEBRUARY, 3, 23, 59); // sunday
        assertFalse("Date should match!", rangeInWeek.includes(outSideBeginInterval));

        LocalDateTime insideSideBeginIntervalOne = LocalDateTime.of(2019, Month.FEBRUARY, 4, 15, 0); // monday
        assertTrue("Date should match!", rangeInWeek.includes(insideSideBeginIntervalOne));

        LocalDateTime insideSideBeginIntervalTwo = LocalDateTime.of(2019, Month.FEBRUARY, 4, 15, 1); // monday
        assertTrue("Date should match!", rangeInWeek.includes(insideSideBeginIntervalTwo));

        LocalDateTime insideSideBeginIntervalThree = LocalDateTime.of(2019, Month.FEBRUARY, 4, 14, 59); // monday, but hour too early
        assertFalse("Date should match!", rangeInWeek.includes(insideSideBeginIntervalThree));

        /**
         * Edge cases end o interval
         */
        LocalDateTime outSideEndIntervalOne = LocalDateTime.of(2019, Month.FEBRUARY, 8, 23, 59);  // Friday
        assertFalse("Date should match!", rangeInWeek.includes(outSideEndIntervalOne));

        LocalDateTime outSideEndIntervalTwo = LocalDateTime.of(2019, Month.FEBRUARY, 15, 23, 59); //Friday
        assertFalse("Date should match!", rangeInWeek.includes(outSideEndIntervalTwo));

       

        LocalDateTime insideSideEndIntervalTwo = LocalDateTime.of(2019, Month.FEBRUARY, 7, 12, 0); //Thursday
        assertTrue("Date should match!", rangeInWeek.includes(insideSideEndIntervalTwo));

        LocalDateTime insideSideEndIntervalThree = LocalDateTime.of(2019, Month.FEBRUARY, 7, 12, 59); //Thrusday
        assertTrue("Date should match!", rangeInWeek.includes(insideSideEndIntervalThree));

        LocalDateTime outSideEndIntervalThree = LocalDateTime.of(2019, Month.FEBRUARY, 7, 13, 0); //FRiday, but hour too late
        assertFalse("Date should match!", rangeInWeek.includes(outSideEndIntervalThree));

    }
    
     @Test
    public void rangeInWeekTestOneDayParamPlah() {
        
         WeekDayToWeekDayRange rangeInWeek = new WeekDayToWeekDayRange(DayOfWeek.MONDAY, DayOfWeek.THURSDAY, 15, 12);

        
        LocalDateTime insideSideEndIntervalOne = LocalDateTime.of(2019, Month.FEBRUARY, 7, 12, 1); //Thursday
        assertTrue("Date should match!", rangeInWeek.includes(insideSideEndIntervalOne));
    }

    @Test
    public void rangeInWeekTestOneDayParam() {

        WeekDayToWeekDayRange rangeInWeek = new WeekDayToWeekDayRange(DayOfWeek.MONDAY);

        /**
         * regular cases
         */
        LocalDateTime insideSideBeginInterval = LocalDateTime.of(2019, Month.FEBRUARY, 4, 15, 0); // monday
        assertTrue("Date should match!", rangeInWeek.includes(insideSideBeginInterval));

        LocalDateTime isNotInInterval = LocalDateTime.of(2019, Month.FEBRUARY, 15, 14, 53); // friday
        assertFalse("Date should match!", rangeInWeek.includes(isNotInInterval));

        /**
         * Edge cases
         */
        LocalDateTime outSideBeginInterval = LocalDateTime.of(2019, Month.FEBRUARY, 3, 23, 59); // sunday
        assertFalse("Date should match!", rangeInWeek.includes(outSideBeginInterval));

        LocalDateTime insideSideBeginIntervalOne = LocalDateTime.of(2019, Month.FEBRUARY, 4, 0, 0); // monday
        assertTrue("Date should match!", rangeInWeek.includes(insideSideBeginIntervalOne));

        LocalDateTime insideSideBeginIntervalTwo = LocalDateTime.of(2019, Month.FEBRUARY, 4, 1, 0); // monday
        assertTrue("Date should match!", rangeInWeek.includes(insideSideBeginIntervalTwo));

        LocalDateTime insideSideBeginIntervalThree = LocalDateTime.of(2019, Month.FEBRUARY, 4, 1, 1); // monday
        assertTrue("Date should match!", rangeInWeek.includes(insideSideBeginIntervalThree));

        LocalDateTime insideSideBeginIntervalfour = LocalDateTime.of(2019, Month.FEBRUARY, 4, 23, 0); // monday
        assertTrue("Date should match!", rangeInWeek.includes(insideSideBeginIntervalfour));

        LocalDateTime insideSideBeginIntervalFive = LocalDateTime.of(2019, Month.FEBRUARY, 4, 23, 59); // monday
        assertTrue("Date should match!", rangeInWeek.includes(insideSideBeginIntervalFive));

        LocalDateTime insideSideBeginIntervalsix = LocalDateTime.of(2019, Month.FEBRUARY, 5, 0, 0); // monday
        assertFalse("Date should match!", rangeInWeek.includes(insideSideBeginIntervalsix));
    }
    
    @Test
    public void rangeInWeekTestOneDayParamWithHour() {

        WeekDayToWeekDayRange rangeInWeek = new WeekDayToWeekDayRange(DayOfWeek.MONDAY, 15, 21);

        /**
         * regular cases
         */
        LocalDateTime insideSideBeginInterval = LocalDateTime.of(2019, Month.FEBRUARY, 4, 15, 0); // monday
        assertTrue("Date should match!", rangeInWeek.includes(insideSideBeginInterval));

        LocalDateTime isNotInInterval = LocalDateTime.of(2019, Month.FEBRUARY, 15, 14, 53); // friday
        assertFalse("Date should match!", rangeInWeek.includes(isNotInInterval));

        /**
         * Edge cases
         */
        LocalDateTime outSideBeginInterval = LocalDateTime.of(2019, Month.FEBRUARY, 3, 23, 59); // sunday
        assertFalse("Date should match!", rangeInWeek.includes(outSideBeginInterval));


        LocalDateTime insideSideBeginIntervalfour = LocalDateTime.of(2019, Month.FEBRUARY, 4, 20, 59); // monday
        assertTrue("Date should match!", rangeInWeek.includes(insideSideBeginIntervalfour));

        LocalDateTime insideSideBeginIntervalFive = LocalDateTime.of(2019, Month.FEBRUARY, 4, 21, 0); // monday
        assertTrue("Date should match!", rangeInWeek.includes(insideSideBeginIntervalFive));
    }

    
    @Test
    public void rangeOverNewYearsEve() {

        DayToDayInYear dayToDayInYearExpression = new DayToDayInYear(Month.NOVEMBER, Month.MARCH);

        /**
         * ***********
         * In interval **********
         */
        LocalDateTime isInTnterval1 = LocalDateTime.of(2019, Month.DECEMBER, 23, 14, 13);
        assertTrue("Date should match!", dayToDayInYearExpression.includes(isInTnterval1));

        LocalDateTime isInTnterval = LocalDateTime.of(2019, Month.JANUARY, 1, 14, 53);
        assertTrue("Date should match!", dayToDayInYearExpression.includes(isInTnterval));

        LocalDateTime isInTnterval2 = LocalDateTime.of(2019, Month.MARCH, 23, 14, 13);
        assertTrue("Date should match!", dayToDayInYearExpression.includes(isInTnterval2));

        /**
         * ****************
         * not in interval ***************
         */
        LocalDateTime isNotInTnterval2 = LocalDateTime.of(2019, Month.APRIL, 23, 14, 13);
        assertFalse("Date should NOT match!", dayToDayInYearExpression.includes(isNotInTnterval2));

        LocalDateTime isNotInTnterval3 = LocalDateTime.of(2019, Month.SEPTEMBER, 23, 14, 13);
        assertFalse("Date should NOT match!", dayToDayInYearExpression.includes(isNotInTnterval3));

        /**
         * **********
         * Edge cases **********
         */
        LocalDateTime latestPossibleNotInterval = LocalDateTime.of(2019, Month.OCTOBER, 31, 23, 59);
        assertFalse("Date should match!", dayToDayInYearExpression.includes(latestPossibleNotInterval));

        LocalDateTime earliestPossibleInInterval = LocalDateTime.of(2019, Month.NOVEMBER, 1, 0, 0);
        assertTrue("Date should match!", dayToDayInYearExpression.includes(earliestPossibleInInterval));

        LocalDateTime latestPossibleInInterval = LocalDateTime.of(2019, Month.MARCH, 31, 23, 59);
        assertTrue("Date should match!", dayToDayInYearExpression.includes(latestPossibleInInterval));

        LocalDateTime earliestPossibleNotInterval = LocalDateTime.of(2019, Month.APRIL, 1, 0, 0);
        assertFalse("Date should NOT match!", dayToDayInYearExpression.includes(earliestPossibleNotInterval));

    }
    
     @Test
    public void rangeOverSunday() {

        WeekDayToWeekDayRange rangeEachWeekExpression = new WeekDayToWeekDayRange(DayOfWeek.FRIDAY, DayOfWeek.TUESDAY);

        /**
         * ***********
         * In interval **********
         */
         LocalDateTime insideSideBeginInterval = LocalDateTime.of(2019, Month.FEBRUARY, 8, 15, 0); // monday
        assertTrue("Date should match!", rangeEachWeekExpression.includes(insideSideBeginInterval));

        LocalDateTime isNotInInterval = LocalDateTime.of(2019, Month.FEBRUARY, 10, 14, 53); // friday
        assertTrue("Date should match!", rangeEachWeekExpression.includes(isNotInInterval));
        
        System.out.println("DOW...."+DayOfWeek.values()[0].name());

    }

}
