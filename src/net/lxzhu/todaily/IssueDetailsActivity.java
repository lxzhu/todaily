/**
 * 
 */
package net.lxzhu.todaily;

import java.util.ArrayList;

import net.lxzhu.todaily.dao.Issue;
import net.lxzhu.todaily.dao.IssueDataContext;
import net.lxzhu.todaily.util.ToastUtil;
import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

/**
 * @author RFIAS
 *
 */
public class IssueDetailsActivity extends Activity {

	public static final String sExtraDataKeyIssueId = "issueid";

	protected View basicFrame;
	protected View descriptionFrame;
	protected Button basicButton;
	protected Button descriptionButton;
	protected ImageButton saveButton;
	protected ImageButton cancelButton;
	protected EditText titleText;
	protected EditText descriptionText;
	protected long issueId;
	protected Spinner importantLevelDropDownList;

	public void onCreate(Bundle savedInstanceState) {
		this.setContentView(R.layout.activity_issue_detail);
		super.onCreate(savedInstanceState);
		this.findViews();
		this.setupBasicButton();
		this.setupDescriptionButton();
		this.setupImportantLevels();
		this.setupSaveButton();
		this.setupCancelButton();
		this.getActionBar().setHomeButtonEnabled(true);
		this.getActionBar().setIcon(R.drawable.aves_arrow_left_48);
		this.getActionBar().setTitle("任务详情");
		this.bindIssueToUI();
	}

	public IssueDetailsActivity getCurrentObject() {
		return this;
	}

	protected void findViews() {
		try {
			this.basicFrame = this.findViewById(R.id.activity_issue_detail_basic_frame);
			this.descriptionFrame = this.findViewById(R.id.activity_issue_detail_description_frame);
			this.basicButton = (Button) this.findViewById(R.id.activity_issue_detail_basic_button);
			this.descriptionButton = (Button) this.findViewById(R.id.activity_issue_detail_description_button);
			this.saveButton = (ImageButton) this.findViewById(R.id.activity_issue_detail_save_button);
			this.cancelButton = (ImageButton) this.findViewById(R.id.activity_issue_detail_cancel_button);
			this.titleText = (EditText) this.findViewById(R.id.activity_issue_detail_title);
			this.descriptionText = (EditText) this.findViewById(R.id.activity_issue_detail_description);
			this.importantLevelDropDownList = (Spinner) this.findViewById(R.id.activity_issue_detail_important_level);
		} catch (Exception e) {
			ToastUtil.showText(this, e.getLocalizedMessage());
		}
	}

	private void setupBasicButton() {
		this.basicButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				try {
					// TODO Auto-generated method stub
					basicFrame.setVisibility(View.VISIBLE);
					descriptionFrame.setVisibility(View.GONE);
				} catch (Exception e) {
					e.printStackTrace();
					ToastUtil.showText(getCurrentObject(), e.getLocalizedMessage());
				}

			}
		});
	}

	private void setupDescriptionButton() {
		this.descriptionButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				try {
					// TODO Auto-generated method stub
					basicFrame.setVisibility(View.GONE);
					descriptionFrame.setVisibility(View.VISIBLE);
				} catch (Exception e) {
					e.printStackTrace();
					ToastUtil.showText(getCurrentObject(), e.getLocalizedMessage());
				}
			}
		});
	}

	private void setupSaveButton() {
		final IssueDetailsActivity that = this;
		this.saveButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				try {
					// TODO Auto-generated method stub
					if (!validateIssueData()) {
						Toast toast = Toast.makeText(getBaseContext(), "输入有误，请检查您的输入.", Toast.LENGTH_LONG);
						toast.setGravity(Gravity.CENTER, 0, 0);
						toast.show();
					} else {
						Issue issue = collectIssueData();
						IssueDataContext dc = new IssueDataContext(getBaseContext());
						dc.save(issue);
						that.setResult(RESULT_OK);
						that.finish();
					}
				} catch (Exception e) {
					e.printStackTrace();
					ToastUtil.showText(getCurrentObject(), e.getLocalizedMessage());
				}
			}
		});
	}

	private void setupCancelButton() {
		this.cancelButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
	}

	private boolean validateIssueData() {
		return true;
	}

	/**
	 * fetch issue object from database and bind its properties to ui elements
	 */
	private void bindIssueToUI() {
		try {
			Bundle extraBundle = this.getIntent().getExtras();

			if (extraBundle != null && extraBundle.containsKey(sExtraDataKeyIssueId)) {
				long issueId = extraBundle.getLong(sExtraDataKeyIssueId);
				IssueDataContext dc = new IssueDataContext(this.getApplicationContext());
				Issue issue = dc.find(issueId);
				if (issue == null) {
					ToastUtil.showText(this, "failed to load specified issue");
					this.finish();
				} else {
					bindIssueToUI(issue);
				}
			}
		} catch (Exception e) {
			ToastUtil.showText(getCurrentObject(), e.getLocalizedMessage());
			e.printStackTrace();
		}
	}

	/**
	 * bind a specified issue object to ui elements
	 * 
	 * @param issue
	 */
	private void bindIssueToUI(Issue issue) {
		this.issueId = issue.getId();
		this.titleText.setText(issue.getTitle());
		this.descriptionText.setText(issue.getDescription());
		this.importantLevelDropDownList.setSelection(issue.getImportantLevel() - 1);
	}

	/**
	 * collect value from ui elements and construct an issue object.
	 * <p>this method does not store issue to database</p>
	 * @return
	 */
	private Issue collectIssueData() {
		Issue retItem = null;
		try {
			Issue issue = new Issue();
			issue.setId(issueId);
			issue.setTitle(this.titleText.getText().toString());
			issue.setDescription(this.descriptionText.getText().toString());
			DropDownItem level = (DropDownItem) this.importantLevelDropDownList.getSelectedItem();
			issue.setImportantLevel(level.getValue());
			retItem = issue;
		} catch (Exception e) {
			e.printStackTrace();
			ToastUtil.showText(getCurrentObject(), e.getLocalizedMessage());
		}

		return retItem;
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			this.finish();
			break;
		}
		return true;
	}

	private void setupImportantLevels() {
		try {
			ArrayList<DropDownItemBase> items = new ArrayList<DropDownItemBase>();
			items.add(new DropDownItemBase(1, "年度"));
			items.add(new DropDownItemBase(2, "季度"));
			items.add(new DropDownItemBase(3, "月度"));
			items.add(new DropDownItemBase(4, "周度"));
			items.add(new DropDownItemBase(5, "天度"));
			items.add(new DropDownItemBase(6, "时度"));
			items.add(new DropDownItemBase(7, "分度"));
			DropDownListAdapter<DropDownItemBase> adapter = new DropDownListAdapter<DropDownItemBase>(this, items);
			this.importantLevelDropDownList.setAdapter(adapter);
		} catch (Exception e) {
			e.printStackTrace();
			ToastUtil.showText(getCurrentObject(), e.getLocalizedMessage());
		}

	}
}
