package net.lxzhu.todaily;

import net.lxzhu.todaily.util.ToastUtil;
import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

public class LocationDetector implements LocationListener {

	protected Activity activity;
	protected LocationManager locationManager;
	protected int minUpdateInterval = 1000;
	protected int minUpdateDistance = 1;
	protected LocationChangedEventHandler eventHandler;

	protected LocationDetector(Activity activity, LocationChangedEventHandler eventHandler) {
		this.activity = activity;
		this.eventHandler = eventHandler;
	}

	public interface LocationChangedEventHandler {
		void onLocationChanged(Location lastLocation);
	}

	public void onLocationChanged(Location lastLocation) {
		this.eventHandler.onLocationChanged(lastLocation);
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onProviderDisabled(String provider) {
		ToastUtil.showText(this.activity, "GPS is required to get location the issue.");
	}

	protected void setupLocationManager() {
		try {
			locationManager = (LocationManager) this.activity.getSystemService(Context.LOCATION_SERVICE);
			if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
				locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, this.minUpdateInterval,
						this.minUpdateDistance, this);
				Location lastLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
				this.onLocationChanged(lastLocation);
			} else {
				ToastUtil.showText(this.activity, "GPS is not enabled");
			}
		} catch (Exception e) {
			ToastUtil.showText(this.activity, e.getLocalizedMessage());
			e.printStackTrace();
		}
	}

	protected void teardownLocationManager() {
		if (this.locationManager != null) {
			this.locationManager.removeUpdates(this);
		}
	}

}
