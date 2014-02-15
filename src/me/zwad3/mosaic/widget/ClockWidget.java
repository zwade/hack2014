package me.zwad3.mosaic.widget;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class ClockWidget extends Widget {

	@Override
	public boolean needsUpdate() {
		return true;
	}

	@Override
	public Bitmap renderBitmap() {
		Paint paint = new Paint();
		paint.setTextSize(1);
		paint.setColor(Color.BLUE);
		paint.setTextAlign(Paint.Align.LEFT);
		Bitmap image = Bitmap.createBitmap(256, 256, Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(image);
		canvas.drawRect(0, 0, 256, 256, paint);
		paint.setColor(Color.YELLOW);
		paint.setTextSize(48);
		canvas.drawText(new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime()), 10, 50, paint);
		return image;
	}
}
