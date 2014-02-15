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

	public ClockWidget(MosaicActivity a) {
		super(a);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean needsUpdate() {
		return true;
	}

	@Override
	public Bitmap renderBitmap() {
		Paint paint = new Paint();
		paint.setColor(Color.BLUE);
		paint.setTextAlign(Paint.Align.LEFT);
		Bitmap image = Bitmap.createBitmap(512, 512, Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(image);
		canvas.drawRect(0, 0, 512, 512, paint);
		paint.setColor(Color.YELLOW);
		paint.setTextSize(64);
		paint.setTypeface(Typeface.create("Roboto",0));
		paint.setTextAlign(Align.RIGHT);
		canvas.drawText(new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime()), 376, 294, paint);
		return image;
	}
}
