# evaluation of recurring events
This library covers the evaluation of recurring events.

 * **DayToDayInYear** (e.g. 27.06. to 31.10.)
 * **WeekdayToWeekDay** (e.g. monday to tuesday)
 * **WeekDayInMonth** (e.g. second tuesday in a month)
 
These temporal expressions can be combined in the following operations 
 
 * **Difference** (e.g. all working days without public holidays)
 * **Intersection** 
 * **Union** (e.g. all public holidays during a year)
 
Based on the interpreter pattern it is possible to combine all of the above mentioned temporal expressions and operations

# Usage of temporal expressions

**Default values for temporal expressions**: 
The *start hour* and *start minute* is *0*, the default values for the *end hour* is *23* ad the *end minute* is *59*.


## DayToDayInYear
This temporal expression covers day-to-day ranges in a year and supports date rollovers (e.g. 27.12. to 23.01.)

Different from what the name might suggest that temporal expression covers ranges it can be usd to cover public holidays
In this case the start month and end month resp. start day and end day are the same.

###Example:
The following example shows a temporal expression for the first christmas holiday (this is in Switzerland the 25th of december):

* `DayToDayInYear CHRISTMAS_HOLIADY_FIRST = new DayToDayInYear(Month.DECEMBER, Month.DECEMBER ,25,25); // first Christmas holiday` 

This temporal expression covers the easter holidays in Switzerland for the year 2019:

* `DayToDayInYear SWISS_EASTER_HOLIDAYS_2019 = new DayToDayInYear(Month.APRIL, Month.APRIL,19,22)); //Easter holidays` 


## WeekDayToWeekDay
This temporal expression covers weekday-to-weekday ranges (e.g. monday to thursday) and supports weekday rollovers (e.g. friday to tuesday)

###Example:

This example represents the time from monday, 00:00h to friday, 23:59h:

`WeekDayToWeekDayRange workingDays  = new WeekDayToWeekDayRange(DayOfWeek.MONDAY, DayOfWeek.FRIDAY,0, 23);`

Note that in the above example we do not use start minute and end minute in the constructor. In this case the default values will be used (0 for start minute resp. 59 for end minute)

## WeekDayInMonth
This temporal expression is used to check for n-th weekday in a month, e.g. second tuesday in a month

### Example:

`WeekDayInMonth SECOND_TUESDAY_IN_MONTH = new WeekDayInMonth(DayOfWeek.TUESDAY, 2));`

# Usage of operations

##Union
This operation can be used to combine *temporal expression* and/or *operations*

###Example:
The following represents all fixed date public holidays in Switzerland (there might be some locally differing holidys)

```java
Union holidays = new Union();
holidays.getElements().add(JANUARY_FIRST); // new years day
holidays.getElements().add(AUGUST_FIRST);  // national holiday
holidays.getElements().add(CHRISTMAS_HOLIADY_FIRST); // first christmas holidy
holidays.getElements().add(CHRISTMAS_HOLIADY_SECOND); // second christmas holiday
```
A date can be evaluated as following:

```java
LocalDateTime thisIsNoPublicHoliday = LocalDateTime.of(2019, Month.APRIL, 1, 14, 53); //  1st of april, 14:53h
LocalDateTime nationalHoliday = LocalDateTime.of(2019, Month.AUGUST, 1, 14, 53); //  1st of august, 14:53h

holidays.includes(thisIsNoPublicHoliday); // this returns FALSE since this is no public holiday
holidays.includes(nationalHoliday); // this returns TRUE since this is the national holiday

```

##Difference
This operation can be used to exclude temporal expressions from an other temporal expression. A sample usecase is to exclude public holidays from a time interval.

###Example:

```java
    WeekDayToWeekDayRange workingDays = new WeekDayToWeekDayRange(DayOfWeek.MONDAY, DayOfWeek.FRIDAY, 6, 19);
        Difference workNoWithHolidays = new Difference(workingDays, CH_PublicHolidays.ch_ch_holidays);
```
The above example represents a recurring event every weekday but not on swiss public holidays (CH_PublicHolidays.ch_ch_holidays). 

##Intersection


###Example:

```java
Intersection winterIsComing = new Intersection();
winterIsComing.add(new DayToDayInYear(Month.MARCH, Month.SEPTEMBER));
winterIsComing.add(new DayToDayInYear(Month.JULY, Month.DECEMBER));

LocalDateTime thisIswinter = LocalDateTime.of(2019, Month.FEBRUARY, 4, 14, 53);
assertFalse("Date should NOT match!", winterIsComing.includes(thisIswinter));

LocalDateTime thisIsNotWinter = LocalDateTime.of(2019, Month.AUGUST, 4, 14, 53);
assertTrue("Date should match!", winterIsComing.includes(thisIsNotWinter));
```

#predefine values



## References
This code is inspired by following readings:
* [Fowler] Fowler, M. <https://www.martinfowler.com/apsupp/recurring.pdf>
