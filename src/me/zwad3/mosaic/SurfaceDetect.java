package me.zwad3.mosaic;

import java.util.Vector;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Camera;
import android.os.Bundle;
import android.provider.MediaStore;


public class SurfaceDetect
{
	//TODO
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
	private double[] getLine(Bitmap b)
	{
		return null;
	}
	private double[] getSurface(double[] d)
	{
		//TODO
		return null;
	}
	
}