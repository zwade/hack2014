package me.zwad3.mosaic.widget;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.widget.Button;

public class TextWidget implements Widget {
	
	Bitmap image;
	
	public TextWidget(){
		Paint paint = new Paint();
		paint.setTextSize(1);
		paint.setColor(Color.GREEN);
		paint.setTextAlign(Paint.Align.LEFT);
		image = Bitmap.createBitmap(256, 256, Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(image);
		canvas.drawRect(0, 0, 256, 256, paint);
		paint.setColor(Color.RED);
		paint.setTextSize(24);
		canvas.drawText("Hello texture!", 10, 32, paint);
	}
	
	@Override
	public boolean needsUpdate() {
		return true;
	}

	@Override
	public Bitmap renderBitmap() {
		return image;
	}
}
