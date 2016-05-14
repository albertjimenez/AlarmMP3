package controller;

import model.Alarm;

public class ControllerAlarm {
	private Alarm myAlarm;

	public ControllerAlarm() {
		myAlarm = new Alarm();
	}

	public long getTimeInMillis() {
		return myAlarm.getTimeInMillis();
	}

	public void setHour(int hour) {
		myAlarm.setHour(hour);
	}

	public void setMinute(int minute) {
		myAlarm.setMinute(minute);
	}

	public void setSecond(int second) {
		myAlarm.setSecond(second);
	}

	public void increment() {
		myAlarm.increment();
	}

	public void decrement() {
		myAlarm.decrement();
	}

	public boolean isNew() {
		return myAlarm.isNew();
	}

	public String getStringTime() {
		return myAlarm.toString();
	}

	@Override
	public boolean equals(Object obj) {
		Alarm a = (Alarm) obj;
		return a.equals(myAlarm);
	}
	// public Alarm getMyAlarm() {
	// return myAlarm;
	// }
	//
	// public void setMyAlarm(Alarm myAlarm) {
	// this.myAlarm = myAlarm;
	// }
	// public int getHour() {
	// return myAlarm.getHour();
	// }
	//
	// public int getMinute() {
	// return myAlarm.getMinute();
	// }
	//
	// public int getSecond() {
	// return myAlarm.getSecond();
	// }

}
