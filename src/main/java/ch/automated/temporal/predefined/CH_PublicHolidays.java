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
package ch.automated.temporal.predefined;

import ch.automated.temporal.operations.Union;
import ch.automated.temporal.ranges.DayToDayInYear;

import java.time.Month;
import java.util.Calendar;

/**
 *
 * @author wit
 */
public class CH_PublicHolidays {
    
    private static final DayToDayInYear JANUARY_FIRST = new DayToDayInYear(Month.JANUARY, Month.JANUARY,1,1);
    private static final DayToDayInYear AUGUST_FIRST = new DayToDayInYear(Month.AUGUST, Month.AUGUST,1,1);
    
    private static final DayToDayInYear CHRISTMAS_HOLIADY_FIRST = new DayToDayInYear(Month.DECEMBER, Month.DECEMBER ,25,25);
    private static final DayToDayInYear CHRISTMAS_HOLIADY_SECOND = new DayToDayInYear(Month.DECEMBER, Month.DECEMBER,26,26);
    
    
    public static Union ch_ch_holidays = new Union();
   
    static {
        
        ch_ch_holidays.setName(PreDefinedTemporalExpressions.CH_PublicHolidays+"");
        
        ch_ch_holidays.getElements().add(JANUARY_FIRST);
        ch_ch_holidays.getElements().add(AUGUST_FIRST);
        ch_ch_holidays.getElements().add(CHRISTMAS_HOLIADY_FIRST);
        ch_ch_holidays.getElements().add(CHRISTMAS_HOLIADY_SECOND);
        
        Calendar instance = Calendar.getInstance();
        
        int year = instance.get(Calendar.YEAR);
        
        
        // Shitty easter logic
        if(year == 2019){
            ch_ch_holidays.getElements().add(new DayToDayInYear(Month.APRIL, Month.APRIL,19,22)); //Eastern
            ch_ch_holidays.getElements().add(new DayToDayInYear(Month.JUNE, Month.JUNE,10,10)); //Pfingsten
        }
        
        if(year == 2020){
            ch_ch_holidays.getElements().add(new DayToDayInYear(Month.APRIL, Month.APRIL,10,13)); //Eastern
            ch_ch_holidays.getElements().add(new DayToDayInYear(Month.JUNE, Month.JUNE,1,1)); //Pfingsten
        }
        
        if(year == 2021){
            ch_ch_holidays.getElements().add(new DayToDayInYear(Month.APRIL, Month.APRIL,2,5)); //Eastern
            ch_ch_holidays.getElements().add(new DayToDayInYear(Month.MAY, Month.MAY,24,24)); //Pfingsten
        }
        
    }
    
    
}
