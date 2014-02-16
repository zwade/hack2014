package me.zwad3.mosaic.widget;

import java.io.InputStream;

import me.zwad3.mosaic.MosaicActivity;
import me.zwad3.mosaic.MyApplication;
import min3d.Shared;
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
		AssetManager assetManager = parent.getApplicationContext().getAssets();
		Bitmap bmp = null;
		InputStream inp;
		try{
			inp = assetManager.open("logo/mosaic-logo-large.png");
			bmp = Bitmap.createBitmap(BitmapFactory.decodeStream(inp, null, null));
		} catch(Exception e){
			//Log.d("no", e.getMessage());
			e.printStackTrace(); 
			inp = null;
		}
		Bitmap bitmap = bmp.copy(Bitmap.Config.ARGB_8888, true);
		//bmp = Bitmap.createBitmap(BitmapFactory.decodeStream(inp, null, null));//hi
		Log.d("fun stuff", "rendering");
		Canvas canvas = new Canvas(bitmap);
		return bitmap;//BitmapFactory.decodeStream(inp,null,null);
	}
}
