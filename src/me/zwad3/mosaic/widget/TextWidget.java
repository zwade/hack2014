package me.zwad3.mosaic.widget;

import me.zwad3.mosaic.MosaicActivity;
import min3d.objectPrimitives.Box;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.widget.Button;

public class TextWidget extends Widget {
	
	Bitmap myImage;
	Box myBox;
	
	public TextWidget(MosaicActivity a){
		super(a);
	}
	
	@Override
	public boolean needsUpdate(int dt) {
		return false;
	}

	@Override
	public Bitmap renderBitmap() {
		Paint paint = new Paint();
		paint.setColor(0xAAFFFFFF);
		myImage = Bitmap.createBitmap(512, 512, Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(myImage);
		canvas.drawRect(0, 0, 512, 512, paint);
		return myImage;
	}

	@Override
	protected Bitmap doInBackground(Box... params) {
		myBox = params[0];
		myImage = renderBitmap();
		return myImage;
	}
}
