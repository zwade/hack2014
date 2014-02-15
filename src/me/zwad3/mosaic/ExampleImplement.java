package me.zwad3.mosaic;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import android.util.Log;
import android.view.KeyEvent;
import me.zwad3.mosaic.widget.TextWidget;
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
		
		mSkyBox = new SkyBox(5.0f, 2);
		mSkyBox.addTexture(SkyBox.Face.North, 	R.drawable.wood_back, 	"north");
		mSkyBox.addTexture(SkyBox.Face.East, 	R.drawable.wood_right, 	"east");
		mSkyBox.addTexture(SkyBox.Face.South, 	R.drawable.wood_back, 	"south");
		mSkyBox.addTexture(SkyBox.Face.West, 	R.drawable.wood_left, 	"west");
		mSkyBox.addTexture(SkyBox.Face.Up,		R.drawable.ceiling, 	"up");
		mSkyBox.addTexture(SkyBox.Face.Down, 	R.drawable.floor, 		"down");
		mSkyBox.scale().y = 0.8f;
		mSkyBox.scale().z = 2.0f;
		//scene.addChild(mSkyBox);
		
	}
	public boolean onKeyDown(int keycode, KeyEvent event) {
	        if (keycode == KeyEvent.KEYCODE_DPAD_CENTER) {
	            Log.d("Tap", "Tap Revolution");
	            Box tmp = new Box(1,1,1);
	            Log.d("hi", ""+tmp);
	            tmp.position().x = 2;
	            tmp.position().y = 2;
	            tmp.position().z = 2;
	            TextWidget txt = new TextWidget();
	            loadTexture(txt, tmp);
	            objects.put(tmp, txt);
	            scene.addChild(tmp);
	            
	            return true;
	        }
	        return false;
	}
	@Override
	public void updateScene() {
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
		}
		
	}
	private boolean loadTexture(Widget w, Box b) {
		if (!w.needsUpdate()) {
			return false;
		}
		//Shared.textureManager().deleteTextureId(w.renderBitmap(), w.toString(), false);
		Shared.textureManager().addTextureId(w.renderBitmap(), w.toString(), false);
			
		TextureVo texture = new TextureVo(w.toString());

		b.textures().add(texture);
		
		return true;
	}
}
