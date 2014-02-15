package me.zwad3.mosaic.widget;

import java.io.InputStream;

import me.zwad3.mosaic.MosaicActivity;
import me.zwad3.mosaic.MyApplication;
import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.speech.RecognizerIntent;
import android.util.Log;

public class StickyNoteWidget extends Widget implements VoiceListener{


	private String myText;
	private boolean updated = false;

	
	
	public StickyNoteWidget(MosaicActivity a) {
		super(a);
		Log.d("Instantiated", "hi");
		a.getVoiceInput(this);
	}
	
	@Override
	public boolean needsUpdate() {
		// TODO Auto-generated method stub
		if (updated) {
			updated = false;
			return true;
		}
		return false;
	}

	@Override
	public Bitmap renderBitmap() {
		AssetManager assetManager = MyApplication.getAppContext().getAssets();
		Bitmap bmp = null;
		try{
			Log.d("assetmanager", "" + (assetManager == null));
			InputStream inp = assetManager.open("widgets/sticky-note-widget.bmp");
			Log.d("texture", "" + (inp == null));
			bmp = Bitmap.createBitmap(BitmapFactory.decodeStream(inp, null, null));
		} catch(Exception e){
			Log.d("no", "nop");
		}
		Paint paint = new Paint();
		paint.setTextSize(2);
		paint.setColor(Color.YELLOW);
		paint.setTypeface(Typeface.create("Handwritten",0));
		paint.setTextAlign(Paint.Align.LEFT);
		Bitmap image = bmp.copy(Bitmap.Config.ARGB_8888, true);
		Canvas canvas = new Canvas(image);
		canvas.drawRect(0, 0, 512, 512, paint);
		paint.setColor(Color.BLACK);
		paint.setTextSize(40);
		canvas.translate(10, 10);
		if (myText != null) {
			canvas.drawText(myText, 16, 32, paint);
		} else {
			canvas.drawText("Loading", 10, 32, paint);
		}
		return image;

	}

	@Override
	public void receiveSTT(String s) {
		myText = s;
		updated = true;
		Log.d("text",s);
		
	}
}


	
