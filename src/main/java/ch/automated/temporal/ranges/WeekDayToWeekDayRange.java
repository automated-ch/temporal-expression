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
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.temporal.ChronoField;

/**
 *
 * @author wit
 */
public class WeekDayToWeekDayRange extends TemporalExpression implements Serializable{

    private DayOfWeek startWeekDay;
    private DayOfWeek endWeekDay;
    private int startHour;
    private int endHour;
    private int startMinute=0;
    private int endMinute=59;

    /**
     * endHour belongs to the interval,
     * e.g. endHour with the value int.FIVE and minute 59 is included in the interval. 
     * 
     * 
     * @param startWeekDay
     * @param endWeekDay
     * @param startHour
     * @param endHour  
     */
    public WeekDayToWeekDayRange(DayOfWeek startWeekDay, DayOfWeek endWeekDay, int startHour, int endHour, int startMinute, int endMinute) {
        this.startWeekDay = startWeekDay;
        this.endWeekDay = endWeekDay;
        this.startHour = startHour;
        this.endHour = endHour;
        this.startMinute = startMinute;
        this.endMinute = endMinute;
    }
    
    public WeekDayToWeekDayRange(DayOfWeek startWeekDay, DayOfWeek endWeekDay, int startHour, int endHour) {
        this.startWeekDay = startWeekDay;
        this.endWeekDay = endWeekDay;
        this.startHour = startHour;
        this.endHour = endHour;     
    }

    public WeekDayToWeekDayRange(DayOfWeek startWeekDay, DayOfWeek endWeekDay) {
        this.startWeekDay = startWeekDay;
        this.endWeekDay = endWeekDay;
        this.startHour = 0;
        this.endHour = 23;
    }
    
    public WeekDayToWeekDayRange(DayOfWeek startWeekDay, int startHour, int endHour) {
        this.startWeekDay = startWeekDay;
        this.endWeekDay = startWeekDay;
        this.startHour = startHour;
        this.endHour = endHour;
      
    }
    
    
    
     public WeekDayToWeekDayRange(DayOfWeek startWeekDay, int startHour, int endHour, int startMinute, int endMinute) {
        this.startWeekDay = startWeekDay;
        this.endWeekDay = startWeekDay;
        this.startHour = startHour;
        this.endHour = endHour;
        this.startMinute = startMinute;
        this.endMinute = endMinute;
    }

    public WeekDayToWeekDayRange(DayOfWeek weekDay) {
        this.startWeekDay = weekDay;
        this.endWeekDay = weekDay;
        this.startHour = 0;
        this.endHour = 23;
    }

    @Override
    public boolean includes(LocalDateTime aDate) {       
            return monthIncludes(aDate) || 
                     (startMonthIncludes(aDate) && endMonthIncludes(aDate));                        
    }

    private boolean monthIncludes(LocalDateTime aDate) {

        DayOfWeek dayOfWeek = aDate.getDayOfWeek();       
        int hourOfDay =  aDate.get(ChronoField.HOUR_OF_DAY);
        int min = aDate.getMinute();
     
        
         // this handles the year switch in provided interval
        // e.g. ranges from november until march
        if(startWeekDay.getValue() > endWeekDay.getValue()){            
            return (dayOfWeek.getValue() >= startWeekDay.getValue() || dayOfWeek.getValue() <= endWeekDay.getValue());            
        } else {
          return (dayOfWeek.getValue() >= startWeekDay.getValue() && dayOfWeek.getValue() <= endWeekDay.getValue())
                 && (hourOfDay >= startHour && hourOfDay <= endHour)
                 && (min >= startMinute && min <= endMinute);
        }
    }
    
   
    private boolean startMonthIncludes(LocalDateTime aDate) {

       
        if (aDate.getDayOfWeek().getValue() >= startWeekDay.getValue() && startHour == 0 && startMinute == 0) {
            return true;
        }

        if (aDate.getDayOfWeek().getValue() >= startWeekDay.getValue()) {            
            if (aDate.getDayOfWeek().getValue() == startWeekDay.getValue()){
                if(aDate.get(ChronoField.HOUR_OF_DAY) >= startHour){
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

        if (aDate.getDayOfWeek().getValue() <= endWeekDay.getValue() && endHour == 23 && endMinute == 59) {
            return true;
        }

        if (aDate.getDayOfWeek().getValue() <= endWeekDay.getValue()) {            
            if (aDate.getDayOfWeek().getValue() == endWeekDay.getValue()){
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

    public DayOfWeek getStartWeekDay() {
        return startWeekDay;
    }

    public void setStartWeekDay(DayOfWeek startWeekDay) {
        this.startWeekDay = startWeekDay;
    }

    public DayOfWeek getEndWeekDay() {
        return endWeekDay;
    }

    public void setEndWeekDay(DayOfWeek endWeekDay) {
        this.endWeekDay = endWeekDay;
    }

    public int getStartHour() {
        return startHour;
    }

    public void setStartHour(int startHour) {
        this.startHour = startHour;
    }

    public int getEndHour() {
        return endHour;
    }

    public void setEndHour(int endHour) {
        this.endHour = endHour;
    }

    public int getStartMinute() {
        return startMinute;
    }

    public void setStartMinute(int startMinute) {
        this.startMinute = startMinute;
    }

    public int getEndMinute() {
        return endMinute;
    }

    public void setEndMinute(int endMinute) {
        this.endMinute = endMinute;
    }
    
    @Override
    public String info() {
        return "DayToDayInYear: StartWeekDay[" +getStartWeekDay()+"]"
                + "StartHour[" +getStartHour()+"]" 
                + "EndWeekDay[" +getEndWeekDay()+"]" 
                + "EndHour[" +getEndHour()+"]\n";
    }

}
