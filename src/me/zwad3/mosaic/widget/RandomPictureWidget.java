package me.zwad3.mosaic.widget;

import java.io.File;

import me.zwad3.mosaic.BitmapActivity;
import android.content.ContentResolver;
import android.database.Cursor;
import android.graphics.Bitmap;
<<<<<<< HEAD
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
=======
>>>>>>> 0d1ae309b12b8d5e3cae8cd8236625c75bb1fd18
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.MediaStore.Images.ImageColumns;
import android.util.Log;

public class RandomPictureWidget extends Widget {
	
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
		Log.d("RPW", "rendering...");
		File dir = new File(Environment.getExternalStorageDirectory()+"/DCIM/Camera");
<<<<<<< HEAD
		int ind = (int) (Math.random()*dir.listFiles().length);
		image = BitmapFactory.decodeFile(dir.listFiles()[ind].toString());
		Bitmap img = Bitmap.createScaledBitmap(image, 256, 256, true);
		/*Canvas canvas = new Canvas(img);
		Paint paint = new Paint();
		paint.setColor(Color.BLUE);
		canvas.drawRect(0, 0, 256, 256, paint);
		canvas.drawBitmap(image, 0, 0, paint);*/
		return img;
=======
		//int ind = (Math.random()*dir.getFiles().length);
		///image = BitmapFactory.decodeFile(dir.getFiles()[ind].toString())
		//Canvas canvas = new Canvas(image);
		return null;
>>>>>>> 0d1ae309b12b8d5e3cae8cd8236625c75bb1fd18
	}

}
