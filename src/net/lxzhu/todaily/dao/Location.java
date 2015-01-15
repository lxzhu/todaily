package net.lxzhu.todaily.dao;

public class Location {
	public double latitude;
	public double longitude;
	public double altitude;
	public double speed;
	public long time;
	public String street = "";

	public Location() {
		this(0, 0, 0, 0, 0);
	}

	public Location(double latitude, double longitude, double altitude, double speed, long time) {
		this.latitude = latitude;
		this.longitude = longitude;
		this.altitude = altitude;
		this.speed = speed;
		this.time = time;
	}

}
