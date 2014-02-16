package me.zwad3.mosaic.widget;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Bitmap.Config;
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
import min3d.Shared;

public class ClockWidget extends Widget {
	
	private final int threshold = 1000;
	private int time = 0;

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
		
		AssetManager assetManager = Shared.context().getAssets();
		Bitmap bmp = null;
		try{
			Log.d("assets", assetManager.list("widgets")[0]+"");

			//InputStream inp = assetManager.open("widgets/clock-widget-small.png");
			//bmp = Bitmap.createBitmap(BitmapFactory.decodeStream(inp, null, null));
		} catch(Exception e){
			//Log.d("no", "nop");
			e.printStackTrace();
		}
		Bitmap bitmap = Bitmap.createBitmap(256, 256, Config.ARGB_8888);
		paint.setTextAlign(Paint.Align.LEFT);
		paint.setTypeface(Typeface.create("Roboto",Typeface.BOLD));
		//Bitmap bitmap = bmp.copy(Bitmap.Config.ARGB_8888, true);
		Canvas canvas = new Canvas(bitmap);
		
		paint.setColor(0xFFFF0000);
		paint.setTextSize(50);
		paint.setTypeface(Typeface.create("Roboto",0));
		paint.setTextAlign(Align.RIGHT);
		canvas.drawText(new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime()), 220, 143, paint);
		
		return bitmap;
	}
}
