package me.zwad3.mosaic.widget;


import me.zwad3.mosaic.MosaicActivity;
import min3d.objectPrimitives.Box;
import android.app.Activity;
import android.graphics.Bitmap;
import android.os.AsyncTask;

public abstract class Widget extends AsyncTask<Box, Integer, Bitmap>{
	public MosaicActivity parent;
	
	public Widget(MosaicActivity a) {
		parent = a;
	}
	
	public abstract boolean needsUpdate(int dt);
		
	public abstract Bitmap renderBitmap();
}
