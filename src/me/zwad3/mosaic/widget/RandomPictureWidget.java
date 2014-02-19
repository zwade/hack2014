package me.zwad3.mosaic.widget;

import java.io.File;

import me.zwad3.mosaic.BitmapActivity;
import me.zwad3.mosaic.MosaicActivity;
import android.content.ContentResolver;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.MediaStore.Images.ImageColumns;
import android.util.Log;

public class RandomPictureWidget extends Widget {
	
	private int updateCallCount;
	
	private final int threshold = 60000;
	private int time = 0;
	
	Bitmap image;
	
	public RandomPictureWidget(MosaicActivity a){
		super(a);
		updateCallCount = 0;
	}
	
	@Override
	public boolean needsUpdate(int dt) {
		time -= dt;
		if (time <= 0) {
			time = threshold+time;
			return true;
		}
		return false;
	}

	@Override
	public Bitmap renderBitmap() {
		Log.d("RPW", "rendering...");
		File dir = new File("/sdcard/DCIM/Camera");
		Log.d("hi",""+dir.listFiles()[0].toString());
		int ind = (int) (Math.random()*dir.listFiles().length);
		image = BitmapFactory.decodeFile(dir.listFiles()[ind].toString());
		
		Bitmap img = Bitmap.createBitmap(image, 500,400,1592, 1144);
		
		img = Bitmap.createScaledBitmap(img, 256, 256, true);
		
		return img;
	}

}
