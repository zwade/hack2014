package me.zwad3.mosaic;

import android.graphics.Bitmap;

public interface Widget {
	public boolean needsUpdate();
	
	public Bitmap renderBitmap();
	
	public String toString();

}
