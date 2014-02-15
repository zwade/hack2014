package me.zwad3.mosaic.widget;

import me.zwad3.mosaic.MosaicActivity;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
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
	public boolean needsUpdate(int dt) {
		// TODO Auto-generated method stub
		if (updated) {
			updated = false;
			return true;
		}
		return false;
	}

	@Override
	public Bitmap renderBitmap() {
		// TODO Auto-generated method stub
		Paint paint = new Paint();
		paint.setTextSize(2);
		paint.setColor(Color.YELLOW);
		paint.setTypeface(Typeface.create("Comic Sans",0));
		paint.setTextAlign(Paint.Align.LEFT);
		Bitmap image = Bitmap.createBitmap(256, 256, Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(image);
		canvas.drawRect(0, 0, 256, 256, paint);
		paint.setColor(Color.BLACK);
		paint.setTextSize(40);
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


	
