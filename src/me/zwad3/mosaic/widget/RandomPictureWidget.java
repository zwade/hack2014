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
	
	private final int threshold = 7500;
	private int time = threshold;
	
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
		File dir = new File(Environment.getExternalStorageDirectory()+"/DCIM/Camera");
		int ind = (int) (Math.random()*dir.listFiles().length);
		image = BitmapFactory.decodeFile(dir.listFiles()[ind].toString());
		Bitmap img = Bitmap.createScaledBitmap(image, 256, 256, true);
		/*Canvas canvas = new Canvas(img);
		Paint paint = new Paint();
		paint.setColor(Color.BLUE);
		canvas.drawRect(0, 0, 256, 256, paint);
		canvas.drawBitmap(image, 0, 0, paint);*/
		return img;
	}

}
