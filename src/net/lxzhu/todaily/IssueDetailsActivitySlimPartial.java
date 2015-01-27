package net.lxzhu.todaily;

import java.io.File;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

public class IssueDetailsActivitySlimPartial implements ActivityExtension {
	protected Activity activity;
	protected View slimFrame;
	protected Button addAudioSlimButton;
	protected Button addImageSlimButton;
	protected AudioRecordingUtil audioRecording;
	protected PictureTakingUtil pictureTaking;

	public IssueDetailsActivitySlimPartial(Activity activity) {
		this.activity = activity;
	}

	@Override
	public void onCreate(Bundle savedInstance) {
		// TODO Auto-generated method stub
		this.findViews();
		this.setupAddAudioSlimButton();
		this.setupAddImageSlimButton();
	}

	@Override
	public void onDestory() {
		// TODO Auto-generated method stub

	}

	protected void findViews() {
		this.slimFrame = this.findViewById(R.id.activity_issue_detail_slim_frame);
		this.addAudioSlimButton = (Button) this.findViewById(R.id.activity_issue_detail_slim_add_audio);
		this.addImageSlimButton = (Button) this.findViewById(R.id.activity_issue_detail_slim_add_image);
	}

	protected View findViewById(int id) {
		return this.activity.findViewById(id);
	}

	public void setVisibility(int visibility) {
		this.slimFrame.setVisibility(visibility);
	}

	private void setupAddAudioSlimButton() {
		this.audioRecording = new AudioRecordingUtil(this.activity);
		this.addAudioSlimButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				onAddAudioSlimButtonClicked();
			}
		});
	}

	private void onAddAudioSlimButtonClicked() {
		if (this.audioRecording.isRecording()) {
			this.audioRecording.stop();
			this.audioRecording.replay();
		} else {
			this.audioRecording.start(Environment.getDataDirectory() + "/mytest.wav");
		}
	}

	private void setupAddImageSlimButton() {
		this.addImageSlimButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				onAddImageSlimButtonClicked();
			}
		});
	}

	private void onAddImageSlimButtonClicked() {
		if (this.pictureTaking == null) {
			pictureTaking = new PictureTakingUtil(this.activity);
		}
		pictureTaking.storePhotoInLibrary("20150120-001.png");
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		String imageFilePath = this.pictureTaking.fetchPhotoFile(requestCode, resultCode, data);
		AlertDialog.Builder builder = new AlertDialog.Builder(this.activity);
		ImageView view = new ImageView(activity);
		Uri uri = Uri.fromFile(new File(imageFilePath));
		view.setImageURI(uri);
		view.getLayoutParams().width = LayoutParams.MATCH_PARENT;
		view.getLayoutParams().height = LayoutParams.MATCH_PARENT;
		view.setScaleType(ScaleType.CENTER_INSIDE);
		builder.setView(view).setPositiveButton("确定", null).setNegativeButton("取消", null).show();

	}
}
