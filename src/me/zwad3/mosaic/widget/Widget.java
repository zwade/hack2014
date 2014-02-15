package me.zwad3.mosaic.widget;

import me.zwad3.mosaic.MosaicActivity;
import android.app.Activity;
import android.graphics.Bitmap;

public abstract class Widget{
	public MosaicActivity parent;
	
	public Widget(MosaicActivity a) {
		parent = a;
	}
	
	public abstract boolean needsUpdate(int dt);
		
	public abstract Bitmap renderBitmap();
}
