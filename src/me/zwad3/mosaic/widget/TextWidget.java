package me.zwad3.mosaic.widget;

import me.zwad3.mosaic.MosaicActivity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.widget.Button;

public class TextWidget extends Widget {
	
	Bitmap image;
	
	public TextWidget(MosaicActivity a){
		super(a);
		Paint paint = new Paint();
		paint.setColor(0xAAFFFFFF);
		image = Bitmap.createBitmap(512, 512, Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(image);
		canvas.drawRect(0, 0, 512, 512, paint);
	}
	
	@Override
	public boolean needsUpdate(int dt) {
		return false;
	}

	@Override
	public Bitmap renderBitmap() {
		return image;
	}
}
