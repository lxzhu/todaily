package net.lxzhu.todaily;

import android.content.Intent;
import android.os.Bundle;

public interface ActivityExtension {
	void onCreate(Bundle savedInstanceState);
	void onDestory();
	void onActivityResult(int requestCode, int resultCode, Intent data);
}
