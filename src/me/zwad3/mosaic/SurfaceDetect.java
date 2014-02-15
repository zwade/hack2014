package me.zwad3.mosaic;

import java.util.Vector;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Camera;
import android.os.Bundle;
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
	public Bitmap EdgePhoto()
	{
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
		EdgeDetector detector = new EdgeDetector();
		 detector.setLowThreshold(0.5f);
		 detector.setHighThreshold(1f);
		 //apply it to an image
		 detector.setSourceImage(b);
		 detector.process();
		 Bitmap edges = detector.getEdgesImage();
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