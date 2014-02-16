package me.zwad3.mosaic.widget;

import java.io.InputStream;

import me.zwad3.mosaic.ExampleImplement;
import me.zwad3.mosaic.MosaicActivity;
import me.zwad3.mosaic.MyApplication;
import min3d.Shared;
import min3d.objectPrimitives.Box;
import min3d.vos.TextureVo;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.Log;
import android.widget.Button;

public class BlankWidget extends Widget {
	
	public BlankWidget(MosaicActivity a){
		super(a);
	}
	
	@Override
	public boolean needsUpdate(int dt) {
		return false;
	}

	@Override
	public void renderBitmap(Box b, int hash) {
		final int id = hash;
		new Thread(new Runnable() {
			public void run() {
				final Bitmap bitmap = genImage();
				parent.runOnUiThread(new Runnable() {
		            public void run() {
		            	Shared.textureManager().addTextureId(bitmap, toString(), false);
		            		
		           		TextureVo texture = new TextureVo(toString());

	            		((ExampleImplement)parent).registerTexture(texture, id);// textures().add(texture);
		            }
		           });
		        }
		    }).start();
	}
	
	public Bitmap genImage() {
		AssetManager assetManager = MyApplication.getAppContext().getAssets();
		Bitmap bmp = null;
		try{
			InputStream inp = assetManager.open("logo/mosaic-logo-large.bmp");
			bmp = Bitmap.createBitmap(BitmapFactory.decodeStream(inp, null, null));
		} catch(Exception e){
			Log.d("no", "nop");
		}
		Bitmap bitmap = bmp.copy(Bitmap.Config.ARGB_8888, true);
		Log.d("fun stuff", "rendering");
		return bitmap;
		
	}
}
