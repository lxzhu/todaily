package net.lxzhu.todaily.dao;

import android.database.Cursor;

public class LocationDataBinder extends DataBinderBase<Location> {

	protected int columnIndexId;
	protected int columnIndexLatitude;
	protected int columnIndexLongitude;
	protected int columnIndexAltitude;
	protected int columnIndexSpeed;
	protected int columnIndexTime;
	protected int columnIndexStreet;
	protected int columnIndexCreateDateTime;
	protected int columnIndexUpdateDateTime;

	public LocationDataBinder(String tableAliasName) {
		super(tableAliasName);

	}

	public void init(Cursor cursor) {
		super.init(cursor);
		this.columnIndexId = getColumnIndex("_id");
		this.columnIndexLatitude = getColumnIndex("latitude");
		this.columnIndexLongitude = getColumnIndex("longitude");
		this.columnIndexAltitude = getColumnIndex("altitude");
		this.columnIndexSpeed = getColumnIndex("speed");
		this.columnIndexTime = getColumnIndex("time");
		this.columnIndexStreet = getColumnIndex("street");
		this.columnIndexCreateDateTime = getColumnIndex("create_datetime");
		this.columnIndexUpdateDateTime = getColumnIndex("update_datetime");
	}

	@Override
	public Location getDataObject(Cursor cursor) {
		Location location = new Location();
		location.setId(get(columnIndexId, 0L));
		location.setLatitude(get(columnIndexLatitude, 0D));
		location.setLongitude(get(columnIndexLongitude, 0D));
		location.setAltitude(get(columnIndexAltitude, 0D));
		location.setSpeed(get(columnIndexSpeed, 0D));
		location.setTime(get(columnIndexTime, 0L));
		location.setStreet(get(columnIndexStreet, ""));
		if (location.getId() <= 0) {
			location = null;
		}
		return location;
	}

}
