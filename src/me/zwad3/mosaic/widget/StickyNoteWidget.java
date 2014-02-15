package me.zwad3.mosaic.widget;

import me.zwad3.mosaic.MosaicActivity;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.speech.RecognizerIntent;
import android.util.Log;

public class StickyNoteWidget extends Widget implements VoiceListener{


	private String myText;
	private boolean updated = false;

	
	
	public StickyNoteWidget(MosaicActivity a) {
		super(a);
		parent.getVoiceInput(this);
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void receiveSTT(String s) {
		myText = s;
		updated = true;
		Log.d("text",s);
		
	}

}
