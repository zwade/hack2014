package me.zwad3.mosaic.widget;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Typeface;
import android.text.TextPaint;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import me.zwad3.mosaic.MosaicActivity;

public class ClockWidget extends Widget {
	
	private final int threshold = 1000;
	private int time = threshold;

	public ClockWidget(MosaicActivity a) {
		super(a);
		// TODO Auto-generated constructor stub
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
		Paint paint = new Paint();
		Bitmap image = Bitmap.createBitmap(512, 512, Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(image);
		paint.setColor(0xFFFF0000);
		paint.setTextSize(100);
		paint.setTypeface(Typeface.create("Roboto",0));
		paint.setTextAlign(Align.RIGHT);
		canvas.drawText(new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime()), 440, 287, paint);
		return image;
	}
}
