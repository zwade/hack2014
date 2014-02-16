package com.kingkoolkyle.CV;

import java.io.InputStream;
import java.util.Vector;

import me.zwad3.mosaic.MyApplication;

import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Camera;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;


public class SurfaceDetect
{
	//TODO
	public void LineList()
	{
		int i =0;
		for(HoughLine l :getLine(EdgePhoto()))
		{
			Log.d("Line "+i,l.toString());
			i++;
		}
	}
	public Bitmap getEdgeFromPhoto() {
		Bitmap bmp = null;
		try{
			String str=Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).toString()+"/wallpaper-2224.jpg";
			Log.d("Str",str);
			bmp = BitmapFactory.decodeFile(str);
		} catch(Exception e){
			Log.d("no", "nop");
		}
		return getEdge(bmp);
	}
	public Bitmap EdgePhoto()
	{
		Log.d("Edge", "was called");
		return getEdge(getPic());
	}
	private Bitmap getPic()
	{
		Intent cameraIntent= new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		Bundle extras = cameraIntent.getExtras();
		Bitmap image = (Bitmap)extras.get("data");
		//TODOING
		return image;
	}
	private Bitmap getEdge(Bitmap b)
	{
		Log.d("getEdge", "was called");
		EdgeDetector detector = new EdgeDetector();
		 detector.setLowThreshold(100f);
		 detector.setHighThreshold(100f);
		 //apply it to an image
		 Log.d("Debo", ""+b);
		 detector.setSourceImage(b);
		 detector.process();
		 Bitmap edges = detector.getEdgesImage();
		 Log.d("Debug",""+edges);
		 return edges;
	}
	private Vector<HoughLine> getLine(Bitmap b)
	{
		HoughTransform h= new HoughTransform(b.getWidth(),b.getHeight());
		return h.LineDetect(b);
	}
	private double[] getSurface(double[] d)
	{
		//TODO
		return null;
	}
	
}