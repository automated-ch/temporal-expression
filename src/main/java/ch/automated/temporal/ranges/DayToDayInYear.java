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
package ch.automated.temporal.ranges;

import ch.automated.temporal.TemporalExpression;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.temporal.ChronoField;

/**
 * This class covers day-to-day ranges in a year and supports date rollovers (e.g. 27.12. to 23.01.)
 *
 *
 *
 *
 * @author wit
 */
public class DayToDayInYear extends TemporalExpression implements Serializable{

    private Month startMonth;
    private Month endMonth;
    private int startDay;
    private int endDay;
    private int startHour;
    private int endHour;
    private int startMinute;
    private int endMinute;

    public DayToDayInYear(Month startMonth, Month endMonth, int startDay, int endDay, int startHour, int endHour, int startMinute, int endMinute) {
        this.startMonth = startMonth;
        this.endMonth = endMonth;
        this.startDay = startDay;
        this.endDay = endDay;
        this.startHour=startHour;
        this.endHour=endHour;
        this.startMinute=startMinute;
        this.endMinute=endMinute;
    }
    
    public DayToDayInYear(Month startMonth, Month endMonth, int startDay, int endDay) {
        this.startMonth = startMonth;
        this.endMonth = endMonth;
        this.startDay = startDay;
        this.endDay = endDay;
        this.startHour=0;
        this.endHour=23;
        this.startMinute=0;
        this.endMinute=59;
    }

    public DayToDayInYear(Month startMonth, Month endMonth) {
        this.startMonth = startMonth;
        this.endMonth = endMonth;
        this.startDay = 0;
        this.endDay =  endMonth.maxLength();//endMonth.adjustInto(TemporalAdjusters.lastDayOfMonth());
        this.startHour= 0;
        this.endHour= 23;
        this.startMinute=0;
        this.endMinute=59;
    }

    public DayToDayInYear(Month month) {
        this.startMonth = month;
        this.endMonth = month;
        this.startDay = 0;
        this.endDay = endMonth.maxLength();
        this.startHour= 0;
        this.endHour= 23;
        this.startMinute=0;
        this.endMinute=59;
    }

    @Override
    public boolean includes(LocalDateTime aDate) {       
            return monthIncludes(aDate) || 
                     (startMonthIncludes(aDate) && endMonthIncludes(aDate));                        
    }

    private boolean monthIncludes(LocalDateTime aDate) {

        Month month = aDate.getMonth();
        int dayOfMonth = aDate.getDayOfMonth();
        int hourOfDay =  aDate.get(ChronoField.HOUR_OF_DAY);
        int min = aDate.getMinute();
     
        
         // this handles the year switch in provided interval
        // e.g. ranges from november until march
        if(startMonth.getValue() > endMonth.getValue()){            
            return (month.getValue() >= startMonth.getValue() || month.getValue() <= endMonth.getValue());            
        } else {
          return (month.getValue() >= startMonth.getValue() && month.getValue() <= endMonth.getValue())
                && (dayOfMonth >= startDay && dayOfMonth <= endDay)
                 && (hourOfDay >= startHour && hourOfDay <= endHour)
                 && (min >= startMinute && min <= endMinute);
        }
    }
    
   
    private boolean startMonthIncludes(LocalDateTime aDate) {

        if (aDate.getMonth() != startMonth) {
            return false;
        }

        if (aDate.getDayOfMonth() >= startDay && startHour == 0 && startMinute == 0) {
            return true;
        }

        if (aDate.getDayOfMonth() >= startDay) {            
            if (aDate.getDayOfMonth() == startDay){
                if(aDate.get(ChronoField.HOUR_OF_DAY) >= startHour ){
                    if (aDate.get(ChronoField.MINUTE_OF_HOUR) >= startMinute) {
                    return true;
                    } else{
                        return false;
                    }
                } else{
                    return false;
                }          
            }                          
            return true;            
        }        
        return false;
    }

 

    private boolean endMonthIncludes(LocalDateTime aDate) {

        if (aDate.getMonth() != endMonth) {
            return false;
        }

        if (aDate.getDayOfMonth() <= endDay && endHour == 23 && endMinute == 59) {
            return true;
        }

        if (aDate.getDayOfMonth() <= endDay) {            
            if (aDate.getDayOfMonth() == endDay){
                if(aDate.get(ChronoField.HOUR_OF_DAY) <= endHour){
                    if (aDate.get(ChronoField.MINUTE_OF_HOUR) <= endMinute) {
                    return true;
                    } else{
                        return false;
                    }
                } else{
                    return false;
                }          
            }                          
            return true;            
        }        
        return false;
    }
    
    public Month getStartMonth() {
        return startMonth;
    }

    public void setStartMonth(Month startMonth) {
        this.startMonth = startMonth;
    }

    public Month getEndMonth() {
        return endMonth;
    }

    public void setEndMonth(Month endMonth) {
        this.endMonth = endMonth;
    }

    public int getStartDay() {
        return startDay;
    }

    public void setStartDay(int startDay) {
        this.startDay = startDay;
    }

    public int getEndDay() {
        return endDay;
    }

    public void setEndDay(int endDay) {
        this.endDay = endDay;
    }
    
    

    @Override
    public String info() {
        return "DayToDayInYear: StartMonth[" +getStartMonth()+"]"
                + "StartDay[" +getStartDay()+"]" 
                + "EndMonth[" +getEndMonth()+"]" 
                + "EndDay[" +getEndDay()+"]\n";
    }

}
