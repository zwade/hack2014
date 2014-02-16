package me.zwad3.mosaic.widget;

import java.io.InputStream;

import me.zwad3.mosaic.MosaicActivity;
import me.zwad3.mosaic.MyApplication;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.Log;
import android.widget.Button;

public class BlankWidget extends Widget {
	
	public BlankWidget(MosaicActivity a){
		super(a);
	}
	
	@Override
	public boolean needsUpdate(int dt) {
		return false;
	}

	@Override
	public Bitmap renderBitmap() {
		AssetManager assetManager = MyApplication.getAppContext().getAssets();
		Bitmap bmp = null;
		try{
			InputStream inp = assetManager.open("logo/mosaic-logo-large.bmp");
			bmp = Bitmap.createBitmap(BitmapFactory.decodeStream(inp, null, null));
		} catch(Exception e){
			Log.d("no", "nop");
		}
		Bitmap bitmap = bmp.copy(Bitmap.Config.ARGB_8888, true);
		Log.d("fun stuff", "rendering");
		return bitmap;
	}
}
