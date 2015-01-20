package net.lxzhu.todaily;

import android.content.Intent;
import android.os.Bundle;

public interface PartialActivity {
	void onCreate(Bundle savedInstanceState);
	void onDestory();
	void onActivityResult(int requestCode, int resultCode, Intent data);
}
