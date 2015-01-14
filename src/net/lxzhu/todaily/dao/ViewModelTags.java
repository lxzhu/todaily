package net.lxzhu.todaily.dao;

import java.util.HashMap;

public class ViewModelTags {
	protected HashMap<String, Object> mTags = new HashMap<String, Object>();

	public boolean has(String key) {
		return mTags.containsKey(key);
	}

	public Object get(String key) {
		return mTags.get(key);
	}

	public void set(String key, Object value) {
		mTags.put(key, value);
	}

	public void remove(String key) {
		mTags.remove(key);
	}

	public boolean getBoolean(String key) {
		if (!this.has(key)) {
			return false;
		}
		return (Boolean) this.get(key);
	}

	public int getInt(String key) {
		if (!this.has(key)) {
			return 0;
		}
		return (Integer) this.get(key);
	}

	public long getLong(String key) {
		if (!this.has(key)) {
			return 0;
		}
		return (Long) this.get(key);
	}

	public String getString(String key) {
		if (!this.has(key)) {
			return "";
		}
		return (String) this.get(key);
	}
}
