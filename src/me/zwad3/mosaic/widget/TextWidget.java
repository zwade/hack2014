package me.zwad3.mosaic.widget;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.widget.Button;
import me.zwad3.mosaic.Widget;

public class TextWidget implements Widget {

	@Override
	public boolean needsUpdate() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public Bitmap renderBitmap() {
		 Paint paint = new Paint();
		 paint.setTextSize(12);
		 paint.setColor(Color.BLACK);
		 paint.setTextAlign(Paint.Align.LEFT);
		 int width = (int) (paint.measureText("Hello Texture") + 0.5f); // round
		 float baseline = (int) (-paint.ascent() + 0.5f); // ascent() is negative
		 int height = (int) (baseline + paint.descent() + 0.5f);
		 Bitmap image = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
		 Canvas canvas = new Canvas(image);
		 canvas.drawText("Hello Texture", 0, baseline, paint);
		 return image;
	}
	
	public String toString() {
		return "TextWidget";
	}

}
