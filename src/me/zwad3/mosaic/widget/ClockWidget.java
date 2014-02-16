package me.zwad3.mosaic.widget;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Typeface;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.Log;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import me.zwad3.mosaic.MosaicActivity;
import me.zwad3.mosaic.MyApplication;

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
		
		AssetManager assetManager = MyApplication.getAppContext().getAssets();
		Bitmap bmp = null;
		try{
			InputStream inp = assetManager.open("widgets/clock-widget.bmp");
			bmp = Bitmap.createBitmap(BitmapFactory.decodeStream(inp, null, null));
		} catch(Exception e){
			Log.d("no", "nop");
		}
		
		paint.setTextAlign(Paint.Align.LEFT);
		paint.setTypeface(Typeface.create("Roboto",Typeface.BOLD));
		Bitmap bitmap = bmp.copy(Bitmap.Config.ARGB_8888, true);
		Canvas canvas = new Canvas(bitmap);
		
		paint.setColor(0xFFFF0000);
		paint.setTextSize(100);
		paint.setTypeface(Typeface.create("Roboto",0));
		paint.setTextAlign(Align.RIGHT);
		canvas.drawText(new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime()), 440, 287, paint);
		
		return bitmap;
	}
}
