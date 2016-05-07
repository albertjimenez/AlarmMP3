package test;

import org.junit.Assert;

import model.Alarm;

public class Test {

	@org.junit.Test
	public void testToString() {
		final String resultado = "00:00:00";
		final String resultado2 = "20:10:30";

		Alarm a1 = new Alarm();
		Assert.assertEquals(resultado, a1.toString());
		Alarm a2 = new Alarm(20, 10, 30);
		Assert.assertEquals(resultado2, a2.toString());
	}

	@org.junit.Test
	public void testMillis() {
		Alarm myAlarm = new Alarm();
		Assert.assertEquals(0, myAlarm.getTimeInMillis(), 0);

		myAlarm.setHour(1);

		Assert.assertEquals(3600000, myAlarm.getTimeInMillis());

		Alarm a2 = new Alarm(0, 12, 0);

		Assert.assertEquals(720000, a2.getTimeInMillis());

		a2.clear();
		a2.setSecond(30);
		Assert.assertEquals(30000, a2.getTimeInMillis());

		a2.clear();
		a2.setHour(1);
		a2.setMinute(1);
		a2.setSecond(30);
		Assert.assertEquals(3690000, a2.getTimeInMillis());

		a2.clear();
		a2.setHour(25);
		Assert.assertEquals(90000000, a2.getTimeInMillis());

	}

	@org.junit.Test
	public void testIncrement() {
		Alarm myAlarm = new Alarm(1, 59, 59);
		myAlarm.increment();
		Assert.assertEquals("02:00:00", myAlarm.toString());

		myAlarm.clear();
		myAlarm.setMinute(59);
		myAlarm.increment();
		Assert.assertEquals("01:00:00", myAlarm.toString());

		myAlarm.clear();
		myAlarm.setSecond(59);
		myAlarm.increment();
		Assert.assertEquals("00:01:00", myAlarm.toString());

		myAlarm.clear();
		myAlarm.setHour(2);
		myAlarm.setMinute(27);
		myAlarm.setSecond(32);
		myAlarm.increment();
		Assert.assertEquals("02:27:33", myAlarm.toString());

	}

	@org.junit.Test
	public void testDecrement() {
		Alarm myAlarm = new Alarm(2, 0, 0);
		myAlarm.decrement();
		Assert.assertEquals("01:59:59", myAlarm.toString());

		myAlarm.clear();
		myAlarm.setHour(1);
		myAlarm.decrement();
		Assert.assertEquals("00:59:59", myAlarm.toString());

		myAlarm.clear();
		myAlarm.setSecond(10);
		myAlarm.decrement();
		Assert.assertEquals("00:00:09", myAlarm.toString());

		myAlarm.clear();
		myAlarm.setHour(2);
		myAlarm.setMinute(27);
		myAlarm.setSecond(32);
		myAlarm.decrement();
		Assert.assertEquals("02:27:31", myAlarm.toString());

	}

}
