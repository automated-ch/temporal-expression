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
import ch.automated.temporal.ranges.WeekDayToWeekDayRange;
import java.time.DayOfWeek;

/**
 * this represents the regular working days of a week in one continious
 * interval: 
 * 
 * the {@link TemporalExpressionAbstract} returns true form MONDAY, 00:00 h to FRIDAY, 23:00 h.
 *
 * @author wit
 */
public class CH_NonWorkingDaysWithChHolidays {
    
  
   private static final WeekDayToWeekDayRange weekends  = new WeekDayToWeekDayRange(DayOfWeek.SATURDAY, DayOfWeek.SUNDAY,0, 23);
    
    
    public static Union nonWorkingDaysAndHolidays = new Union();
   
    static {     
        nonWorkingDaysAndHolidays.add(CH_PublicHolidays.ch_ch_holidays);
        nonWorkingDaysAndHolidays.add(weekends);
        nonWorkingDaysAndHolidays.setName(PreDefinedTemporalExpressions.CH_NonWorkingDaysAndhHolidays+"");
    }
    
    
}
