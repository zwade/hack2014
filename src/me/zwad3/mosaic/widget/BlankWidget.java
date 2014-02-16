package me.zwad3.mosaic.widget;


import me.zwad3.mosaic.MosaicActivity;
import min3d.Shared;
import min3d.objectPrimitives.Box;
import min3d.vos.TextureVo;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.widget.Button;

public class BlankWidget extends Widget {
	
	Bitmap image;
	Box myBox;
	
	public BlankWidget(MosaicActivity a){
		super(a);
	}
	
	@Override
	public boolean needsUpdate(int dt) {
		return false;
	}

	@Override
	public Bitmap renderBitmap() {
		// TODO Auto-generated method stub
		Paint paint = new Paint();
		paint.setColor(0xAAFFFFFF);
		image = Bitmap.createBitmap(512, 512, Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(image);
		canvas.drawRect(0, 0, 512, 512, paint);
		return image;
	}

	@Override
	protected Bitmap doInBackground(Box... params) {
		
		myBox = params[0];
		image = renderBitmap();
		return image;
	}
	
	@Override 
	protected void onPostExecute(Bitmap img) {
		try {
			Shared.textureManager().deleteTexture(toString());
		} catch (Exception e) {
			
		}
		Shared.textureManager().addTextureId(img, toString(), false);
			
		TextureVo texture = new TextureVo(toString());

		myBox.textures().add(texture);

	}


}
