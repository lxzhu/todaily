/**
 * 
 */
package net.lxzhu.todaily;

import net.lxzhu.todaily.dao.Issue;
import net.lxzhu.todaily.dao.IssueDataContext;
import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

/**
 * @author RFIAS
 *
 */
public class IssueDetailsActivity extends Activity {

	protected View basicFrame;
	protected View descriptionFrame;
	protected Button basicButton;
	protected Button descriptionButton;
	protected ImageButton saveButton;
	protected ImageButton cancelButton;
	protected EditText titleText;
	protected EditText descriptionText;
	protected long issueId;

	public void onCreate(Bundle savedInstanceState) {
		this.setContentView(R.layout.activity_issue_detail);
		super.onCreate(savedInstanceState);
		this.findViews();
		this.setupBasicButton();
		this.setupDescriptionButton();
		this.setupSaveButton();
		this.setupCancelButton();
		this.getActionBar().setHomeButtonEnabled(true);
		this.getActionBar().setIcon(R.drawable.aves_arrow_left_48);
		this.getActionBar().setTitle("任务详情");
	}

	protected void findViews() {
		this.basicFrame = this.findViewById(R.id.activity_issue_detail_basic_frame);
		this.descriptionFrame = this.findViewById(R.id.activity_issue_detail_description_frame);
		this.basicButton = (Button) this.findViewById(R.id.activity_issue_detail_basic_button);
		this.descriptionButton = (Button) this.findViewById(R.id.activity_issue_detail_description_button);
		this.saveButton = (ImageButton) this.findViewById(R.id.activity_issue_detail_save_button);
		this.cancelButton = (ImageButton) this.findViewById(R.id.activity_issue_detail_cancel_button);
		this.titleText = (EditText) this.findViewById(R.id.activity_issue_detail_title);
		this.descriptionText = (EditText) this.findViewById(R.id.activity_issue_detail_description);
	}

	private void setupBasicButton() {
		this.basicButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				basicFrame.setVisibility(View.VISIBLE);
				descriptionFrame.setVisibility(View.GONE);
			}
		});
	}

	private void setupDescriptionButton() {
		this.descriptionButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				basicFrame.setVisibility(View.GONE);
				descriptionFrame.setVisibility(View.VISIBLE);
			}
		});
	}

	private void setupSaveButton() {
		final IssueDetailsActivity that = this;
		this.saveButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
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

	private Issue collectIssueData() {
		Issue issue = new Issue();
		issue.setId(issueId);
		issue.setTitle(this.titleText.getText().toString());
		issue.setDescription(this.descriptionText.getText().toString());
		return issue;
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			this.finish();
			break;
		}
		return true;
	}
}
