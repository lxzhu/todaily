package net.lxzhu.todaily;

import net.lxzhu.todaily.util.RequestCodes;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabHost.TabSpec;

/**
 * 
 */

/**
 * @author RFIAS
 *
 */
public class MainActivity extends FragmentActivity implements OnTabChangeListener

{

	protected static final String TABSPEC_TODAY = "tabspec_today";
	protected static final String TABSPEC_STAT = "tabspec_stat";
	protected static final String TABSPEC_SETTINGS = "tabspec_settings";
	private String mCurrentTabId = TABSPEC_TODAY;

	private FragmentTabHost mTabHost;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_main);

		mTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
		mTabHost.setup(this, getSupportFragmentManager(), android.R.id.tabcontent);

		TabSpec tabToday = mTabHost.newTabSpec(MainActivity.TABSPEC_TODAY).setIndicator("今日");
		mTabHost.addTab(tabToday, FragmentToday.class, null);

		TabSpec tabStat = mTabHost.newTabSpec(MainActivity.TABSPEC_STAT).setIndicator("统计");
		mTabHost.addTab(tabStat, FragmentStat.class, null);

		TabSpec tabSetting = mTabHost.newTabSpec(MainActivity.TABSPEC_SETTINGS).setIndicator("设置");
		mTabHost.addTab(tabSetting, FragmentSetting.class, null);

		mTabHost.setOnTabChangedListener(this);

		this.getActionBar().setTitle("时光机器");

	}

	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater mi = this.getMenuInflater();
		if (this.mCurrentTabId.equals(TABSPEC_TODAY)) {
			mi.inflate(R.menu.optionmenu_fragment_today, menu);
		} else if (this.mCurrentTabId.equals(TABSPEC_STAT)) {
			mi.inflate(R.menu.optionmenu_fragment_stat, menu);
		} else if (this.mCurrentTabId.equals(TABSPEC_SETTINGS)) {
			mi.inflate(R.menu.optionmenu_fragment_setting, menu);
		}
		return true;
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_fragment_today_new_issue:
			Intent intent=new Intent(this,IssueDetailsActivity.class);
			this.startActivityForResult(intent, RequestCodes.RequestCreateIssue);
			break;
		case R.id.action_fragment_today_new_case:
			break;
		default:
		}
		return true;
	}

	public void onTabChanged(String tabId) {
		this.mCurrentTabId = tabId;
		this.invalidateOptionsMenu();
	}

	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		
		String currentTabTag = this.mTabHost.getCurrentTabTag();
		Fragment fragment = this.getSupportFragmentManager().findFragmentByTag(currentTabTag);
		if (fragment != null) {
			fragment.onActivityResult(requestCode, resultCode, data);
		}
		super.onActivityResult(requestCode, resultCode, data);
	}
}
