package me.zwad3.mosaic.widget;

import com.kingkoolkyle.CV.SurfaceDetect;

import me.zwad3.mosaic.MosaicActivity;
import android.graphics.Bitmap;

public class CVWidget extends Widget {

	boolean hasFired = false;
	
	public CVWidget(MosaicActivity a) {
		super(a);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean needsUpdate(int dt) {
		if (!hasFired) {
			hasFired = true;
			return true;
		}
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Bitmap renderBitmap() {
		// TODO Auto-generated method stub
		return new SurfaceDetect().getEdgeFromPhoto();
	}

}
