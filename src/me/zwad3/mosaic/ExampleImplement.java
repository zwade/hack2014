package me.zwad3.mosaic;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Set;

import android.util.Log;
import android.view.KeyEvent;
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
	
	private final int DELAY = 500;
	
	
	public void initScene() {
		lastUpdate = Calendar.getInstance().getTime().getTime();
		objects = new HashMap<Box, Widget>();
		super.initScene();
		scene.lights().add(new Light());
		
		
	}
	public boolean onKeyDown(int keycode, KeyEvent event) {
	        if (keycode == KeyEvent.KEYCODE_DPAD_CENTER) {
	            Log.d("Tap", "Tap Revolution");
	            Box tmp = new Box(1,1,1);
	            tmp.position().x = scene.camera().target.x*5;
	            tmp.position().y = scene.camera().target.y*5;
	            tmp.position().z = scene.camera().target.z*5;
	            ClockWidget txt = new ClockWidget();
	            loadTexture(txt, tmp);
	            objects.put(tmp, txt);
	            scene.addChild(tmp);
	            
	            return true;
	 
	        }
	        return super.onKeyDown(keycode, event);
	}
	@Override
	public void updateScene() {
		long time = Calendar.getInstance().getTime().getTime();
		long dx = time-lastUpdate;
		lastUpdate = time;
		TTL -= dx;
		if (TTL < 0) {

			Set<Box> obj = objects.keySet();
			for (Box i:obj) {
				if (objects.get(i).needsUpdate()) {
					loadTexture(objects.get(i),i);
				}
			}
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
