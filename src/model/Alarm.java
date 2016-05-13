package model;

import java.util.Calendar;

public class Alarm {
	private int hour;
	private int minute;
	private int second;

	public Alarm() {
		hour = 0;
		minute = 0;
		second = 0;
	}

	public Alarm(int hour, int minute, int second) {
		this.hour = hour;
		this.minute = minute;
		this.second = second;
	}

	public Alarm(Calendar c) {
		this.hour = c.get(Calendar.HOUR_OF_DAY);
		this.minute = c.get(Calendar.MINUTE);
		this.second = c.get(Calendar.SECOND);
	}
	// AdriBall:
	// public Alarm(long milliseconds) {
	// this.hour = (int) (milliseconds / 3600000);
	// milliseconds = milliseconds % 360000;
	// this.minute = (int) milliseconds / 60000;
	// milliseconds = milliseconds % 60000;
	// this.second = (int) (milliseconds / 1000);
	//
	// }

	public void clear() {
		hour = 0;
		minute = 0;
		second = 0;
	}

	public long getTimeInMillis() {

		long hourInSec = 0;
		long minuteInSec = 0;
		switch (hour) {
		case 0:
			hourInSec = 0;
			break;

		default:
			hourInSec = 3600 * hour;
			break;
		}
		switch (minute) {
		case 0:
			minuteInSec = 0;
			break;

		default:
			minuteInSec = 60 * minute;
			break;
		}

		final long allInSeconds = (hourInSec + minuteInSec + second) * 1000;

		return isNew() ? 0 : allInSeconds;

	}

	public Calendar getMyCalendar() {
		Calendar c = Calendar.getInstance();
		c.set(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DATE), hour, minute, second);
		return c;
	}

	public void increment() {
		boolean keepCount = false;
		if (minute == 59 && second == 59) {
			minute = 0;
			second = 0;
			hour++;
			keepCount = true;

		}
		if (minute == 59) {
			minute = 0;
			hour++;
			keepCount = true;
		}
		if (second == 59) {
			second = 0;
			minute++;
			keepCount = true;
		}
		if (!keepCount)
			second++;

	}

	public void decrement() {
		boolean keepCount = false;
		if (minute == 0 && second == 0 && hour == 0) {
			minute = 59;
			second = 59;
			keepCount = true;

		} else if (minute == 0 && second == 0) {
			minute = 59;
			second = 59;
			keepCount = true;
			if (hour != 0)
				hour--;
		}
		if (hour != 0 && minute == 0 && second != 0) {
			minute = 59;
			hour--;
			keepCount = true;
		}
		if (second == 0) {
			second = 59;
			minute--;
			keepCount = true;
		}
		if (!keepCount)
			second--;

	}

	public boolean isNew() {
		return hour == 0 && minute == 0 && second == 0;
	}

	public int getHour() {
		return hour;
	}

	public void setHour(int hour) {
		this.hour = hour;
	}

	public int getMinute() {
		return minute;
	}

	public void setMinute(int minute) {
		this.minute = minute;
	}

	public int getSecond() {
		return second;
	}

	public void setSecond(int second) {
		this.second = second;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof Alarm))
			return false;
		Alarm al = (Alarm) obj;

		return al.hour == hour && al.minute == minute && al.second == second;
	}

	@Override
	public String toString() {
		String c = "";
		char separador = ':';
		if (hour <= 9)
			c += "0" + hour;
		else
			c += hour;
		c += separador;
		if (minute <= 9)
			c += "0" + minute;
		else
			c += minute;
		c += separador;
		if (second <= 9)
			c += "0" + second;
		else
			c += second;

		return c;
	}
}
