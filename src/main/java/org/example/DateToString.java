package org.example;

import java.util.Date;

import org.joda.time.DateTime;

public class DateToString
{
	public static void main(String[] args)
	{
		// Only interested in the date and not the time.
		DateTime currentDate = new DateTime();
		DateTime midDay = new DateTime().hourOfDay().setCopy(12).minuteOfHour().setCopy(0);

		// If the job is run before mid day we use yesterday as todays rates wouldn't be available to build from
		if (currentDate.isBefore(midDay))
		{
			currentDate = currentDate.dayOfMonth().addToCopy( -1);
		}

		Date predictionDate = currentDate.withTime(0, 0, 0, 0).toDate();

		extracted(currentDate, predictionDate);

	}

	private static void extracted(DateTime currentDate, Date predictionDate) {
		System.out.println("org.joda.DateTime.toString: " + currentDate.toString());
		System.out.println("java.util.Date.toString: " + predictionDate.toString());
		System.out.println("java.util.Calendar.toString: " + currentDate.toGregorianCalendar().toString());
		System.out.println("org.joda.time.LocalDate.toString: " + currentDate.toLocalDate().toString());
	}
}
