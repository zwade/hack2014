package me.zwad3.mosaic;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import android.app.PendingIntent;
import android.content.Intent;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuInflater;
import android.view.MotionEvent;
import me.zwad3.mosaic.widget.ClockWidget;
import me.zwad3.mosaic.widget.TextWidget;
import me.zwad3.mosaic.widget.Widget;
import min3d.Shared;
import min3d.core.Object3dContainer;
import min3d.objectPrimitives.Box;
import min3d.objectPrimitives.SkyBox;
import min3d.vos.Light;
import min3d.vos.TextureVo;

public class ExampleImplement extends Renderer3D {
	private SkyBox mSkyBox;
	private HashMap<Box, Widget> objects;
	
	private long lastUpdate;
	private long TTL = 500;
	
	private int DELAY = 1000;
	
	private boolean needsUpdate = false;
	
	
	public void initScene() {
		lastUpdate = Calendar.getInstance().getTime().getTime();
		objects = new HashMap<Box, Widget>();
		super.initScene();
		scene.lights().add(new Light());
		
		
	}
	public boolean onKeyDown(int keycode, KeyEvent event) {
	        if (keycode == KeyEvent.KEYCODE_DPAD_CENTER) {
	            needsUpdate = true;
	 
	        }
	        return super.onKeyDown(keycode, event);
	}
	public boolean onTouchEvent(MotionEvent e) {
		needsUpdate = true;
		super.onTouchEvent(e);
		return false;
	}
	@Override
	public void updateScene() {
		if (needsUpdate) {
			
			MenuInflater inflater = getMenuInflater();
			
			
			Log.d("Tap", "Tap Revolution");
            Box tmp = new Box(1,1,0);
            tmp.position().x = scene.camera().target.x*5;
            tmp.position().y = scene.camera().target.y*5;
            tmp.position().z = scene.camera().target.z*5;
            
            float x = scene.camera().target.x-scene.camera().position.x;
            float y = scene.camera().target.y-scene.camera().position.y;
            float z = scene.camera().target.z-scene.camera().position.z;
            //Log.d("BOB",""+scene.camera().position.y);
            
            //float den = (float) Math.sqrt(x*x+y*y+z*z);
            tmp.rotation().y =(float) ((float) Math.atan(x/z)*180/Math.PI);
            //tmp.rotation().z =(float) ((float) 90-Math.acos(y/den)*180/Math.PI);
            //tmp.rotation().x =(float) ((float) 90-Math.acos(z/den)*180/Math.PI);
            //tmp.rotation().z = (float) Math.cos(Math.acos(z/den)*Math.PI/180)*360;
            //tmp.rotation().x = (float) ((float) Math.sin(Math.acos(z/den)*Math.PI/180)*Math.sin(Math.acos(y/den)*Math.PI/180))*360;
            //tmp.rotation().y = (float) ((float) Math.sin(Math.acos(z/den)*Math.PI/180)*Math.cos(Math.acos(x/den)*Math.PI/180))*360;
        
            Log.d("target and rotation",""+x+" "+y+" "+z+" "+tmp.rotation().x+" "+tmp.rotation().y+" "+tmp.rotation().z);
            
            TextWidget txt = new TextWidget();
            loadTexture(txt, tmp);
            objects.put(tmp, txt);
            scene.addChild(tmp);
            
            needsUpdate = false;
		}
		long time = Calendar.getInstance().getTime().getTime();
		long dx = time-lastUpdate;
		lastUpdate = time;
		TTL -= dx;
		if (TTL < 0) {
			for (Box i:objects.keySet()) {
				if (objects.get(i).needsUpdate()) {
					loadTexture(objects.get(i),i);
				}
			}
			TTL = DELAY;
		}
		
	}
	private boolean loadTexture(Widget w, Box b) {
		if (!w.needsUpdate()) {
			return false;
		}
		//Shared.textureManager().deleteTextureId(w.renderBitmap(), w.toString(), false);
		try {
			Shared.textureManager().deleteTexture(w.toString());
		} catch (Exception e) {
			
		}
		Shared.textureManager().addTextureId(w.renderBitmap(), w.toString(), false);
			
		TextureVo texture = new TextureVo(w.toString());

		b.textures().add(texture);
		
		return true;
	}
}
