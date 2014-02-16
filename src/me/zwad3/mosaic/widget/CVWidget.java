package me.zwad3.mosaic.widget;

import com.kingkoolkyle.CV.SurfaceDetect;

import me.zwad3.mosaic.MosaicActivity;
import min3d.Shared;
import min3d.objectPrimitives.Box;
import min3d.vos.TextureVo;
import android.graphics.Bitmap;

public class CVWidget extends Widget {
	
	private Box myBox;
	private Bitmap myImage;
	
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

	@Override
	protected Bitmap doInBackground(Box... params) {
		myBox = params[0];
		myImage = renderBitmap();
		return myImage;
	}

}
