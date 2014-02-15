package me.zwad3.mosaic.widget;

import java.io.File;

import me.zwad3.mosaic.BitmapActivity;
import android.content.ContentResolver;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.MediaStore.Images.ImageColumns;

public class RandomPictureWidget implements Widget {
	
	private int updateCallCount;
	
	Bitmap image;
	
	public RandomPictureWidget(){
		updateCallCount = 0;
	}
	
	@Override
	public boolean needsUpdate() {
		updateCallCount++;
		return updateCallCount % 100 == 0;
	}

	@Override
	public Bitmap renderBitmap() {
		File dir = new File(Environment.getExternalStorageDirectory()+"/DCIM/Camera");
		//int ind = (Math.random()*dir.getFiles().length);
		///image = BitmapFactory.decodeFile(dir.getFiles()[ind].toString())
		//Canvas canvas = new Canvas(image);
		return null;
	}

}
