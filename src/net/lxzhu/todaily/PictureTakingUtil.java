package net.lxzhu.todaily;

import java.io.File;

import net.lxzhu.todaily.util.ToastUtil;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;

public class PictureTakingUtil {
	protected Activity context;
	public static final int sRequestCodeTakingPhoto = "net.lxzhu.todaily.PictureTakingUtil".hashCode();
	protected static final String[] sImageFormats = new String[] { ".jpg", ".png", ".bmp" };
	protected String filePath;

	public PictureTakingUtil(Activity context) {
		this.context = context;
	}

	public void storePhotoInLibrary(String fileName) {
		String filePath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
				.getAbsolutePath();
		if (!filePath.endsWith("/"))
			filePath += "/";
		filePath += fileName;
		storePhoto(filePath);
	}

	public void storePhoto(String filePath) {
		try {
			this.filePath = filePath;
			File file = new File(filePath);			
			Uri outputFileUri = Uri.fromFile(file);
			Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
			//cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
			this.context.startActivityForResult(cameraIntent, sRequestCodeTakingPhoto);
		} catch (Exception e) {
			e.printStackTrace();
			ToastUtil.showText(this.context, e.getLocalizedMessage());
		}
	}

	public String fetchPhotoFile(int requestCode, int resultCode, Intent data) {
		if (sRequestCodeTakingPhoto == requestCode && Activity.RESULT_OK == resultCode) {
			if (this.filePath != null) {
				return this.filePath;
			}
		}
		return null;
	}

	public void fetchPhotoStream(int requestCode, int resultCode, Intent data) {
		if (sRequestCodeTakingPhoto == requestCode && Activity.RESULT_OK == resultCode) {

		}
	}
}
