package me.zwad3.mosaic.widget;

import java.io.InputStream;

import me.zwad3.mosaic.MosaicActivity;
import me.zwad3.mosaic.MyApplication;
import min3d.Shared;
import min3d.objectPrimitives.Box;
import min3d.vos.TextureVo;
import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.speech.RecognizerIntent;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.Log;

public class StickyNoteWidget extends Widget implements VoiceListener{

	private Box myBox;
	private Bitmap myImage;

	private String myText;
	private boolean updated = false;

	
	
	public StickyNoteWidget(MosaicActivity a) {
		super(a);
		Log.d("Instantiated", "hi");
		a.getVoiceInput(this);
	}
	
	@Override
	public boolean needsUpdate(int dt) {
		// TODO Auto-generated method stub
		if (updated) {
			updated = false;
			return true;
		}
		return false;
	}

	@Override
	public Bitmap renderBitmap() {
		AssetManager assetManager = MyApplication.getAppContext().getAssets();
		Bitmap bmp = null;
		try{
			Log.d("assetmanager", "" + (assetManager == null));
			InputStream inp = assetManager.open("widgets/sticky-note-widget.bmp");
			Log.d("texture", "" + (inp == null));
			bmp = Bitmap.createBitmap(BitmapFactory.decodeStream(inp, null, null));
		} catch(Exception e){
			Log.d("no", "nop");
		}
		Paint paint = new Paint();
		paint.setTextAlign(Paint.Align.LEFT);
		paint.setTypeface(Typeface.create("Droid Sans",Typeface.BOLD));
		Bitmap bitmap = bmp.copy(Bitmap.Config.ARGB_8888, true);
		Canvas canvas = new Canvas(bitmap);
		paint.setColor(0xEE001122);
		paint.setTextSize(36);
		TextPaint textp = new TextPaint(paint);
		textp.baselineShift = 100;
		String text = myText==null? "Loading" : myText;
		StaticLayout sl = new StaticLayout(text, textp, 496, Layout.Alignment.ALIGN_NORMAL, 1.2f, 0.0f, false);
		Log.d("lines", "" + sl.getLineCount());
		/*for(int i = 0; i < headlines.length; i++){
			canvas.drawText(headlines[i], 10, 132 + 48*(i+1), paint);
		}*/
		canvas.translate(8, 24);
		sl.draw(canvas);
		return bitmap;
	}

	@Override
	public void receiveSTT(String s) {
		myText = s;
		updated = true;
		Log.d("text",s);
		
	}

	@Override
	protected Bitmap doInBackground(Box... params) {
		myBox = params[0];
		myImage = renderBitmap();
		return myImage;
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


	
