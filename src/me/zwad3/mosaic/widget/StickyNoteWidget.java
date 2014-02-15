package me.zwad3.mosaic.widget;

import java.awt.List;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.speech.RecognizerIntent;

public class StickyNoteWidget extends Widget {

	private String myText;

	
	public void StickyNoteWidget() {
		parent.getVoiceInput();
	}
	
	@Override
	public boolean needsUpdate() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Bitmap renderBitmap() {
		// TODO Auto-generated method stub
		return null;
	}

}
