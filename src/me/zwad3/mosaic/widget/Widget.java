package me.zwad3.mosaic.widget;

import android.graphics.Bitmap;

public interface Widget{
	public boolean needsUpdate();
	
	public Bitmap renderBitmap();
}
