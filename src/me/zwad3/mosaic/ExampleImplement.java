package me.zwad3.mosaic;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.widget.PopupMenu;
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
	
	private Widget needsUpdate;
	
	private float latestCoords[] = new float[6];
	
	
	public void initScene() {
		lastUpdate = Calendar.getInstance().getTime().getTime();
		objects = new HashMap<Box, Widget>();
		super.initScene();
		scene.lights().add(new Light());
		
		
	}
	public boolean onKeyDown(int keycode, KeyEvent event) {
	        if (keycode == KeyEvent.KEYCODE_DPAD_CENTER) {
	        	latestCoords[0] = scene.camera().target.x;
	        	latestCoords[1] = scene.camera().target.y;
	        	latestCoords[2] = scene.camera().target.z;
	        	latestCoords[3] = scene.camera().position.z;
	        	latestCoords[4] = scene.camera().position.y;
	        	latestCoords[5] = scene.camera().position.z;
	        	
	        	for (Box b:objects.keySet()) {
	        		getDistance(b);
	        	}

	        	runOnUiThread(new Runnable() {
				    public void run() {
				        //Toast.makeText(activity, "Hello", Toast.LENGTH_SHORT).show();
				    	openOptionsMenu();
				    }
				});
	 
	        }
	        return super.onKeyDown(keycode, event);
	}
	@Override
	public void updateScene() {
		if (needsUpdate != null) {
		

			Log.d("Tap", "Tap Revolution");
            Box tmp = new Box(2,2,0);
            tmp.position().x = latestCoords[0]*5;
            tmp.position().y = latestCoords[1]*5;
            tmp.position().z = latestCoords[2]*5;
            
            float x = latestCoords[0]-latestCoords[3];
            float y = latestCoords[1]-latestCoords[4];
            float z = latestCoords[2]-latestCoords[5];
            //Log.d("BOB",""+scene.camera().position.y);
            
            //float den = (float) Math.sqrt(x*x+y*y+z*z);
            tmp.rotation().y =(float) ((float) Math.atan(x/z)*180/Math.PI);
            //tmp.rotation().z =(float) ((float) 90-Math.acos(y/den)*180/Math.PI);
            //tmp.rotation().x =(float) ((float) 90-Math.acos(z/den)*180/Math.PI);
            //tmp.rotation().z = (float) Math.cos(Math.acos(z/den)*Math.PI/180)*360;
            //tmp.rotation().x = (float) ((float) Math.sin(Math.acos(z/den)*Math.PI/180)*Math.sin(Math.acos(y/den)*Math.PI/180))*360;
            //tmp.rotation().y = (float) ((float) Math.sin(Math.acos(z/den)*Math.PI/180)*Math.cos(Math.acos(x/den)*Math.PI/180))*360;
        
            Log.d("target and rotation",""+x+" "+y+" "+z+" "+tmp.rotation().x+" "+tmp.rotation().y+" "+tmp.rotation().z);
            
            //TextWidget txt = new TextWidget();
            loadTexture(needsUpdate, tmp);
            objects.put(tmp, needsUpdate);
            scene.addChild(tmp);
            
            needsUpdate = null;
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
	@Override
	 public boolean onCreateOptionsMenu(Menu menu) {
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.newitem, menu);
	    return true;
	 }

	 @Override
	 public boolean onOptionsItemSelected(MenuItem item) {
	    // Handle item selection.
	    switch (item.getItemId()) {
	        case R.id.clock:
	        	Log.d("hi", "clock");
	        	needsUpdate = new ClockWidget();
	            return false;
	        case R.id.blank:
	        	needsUpdate = new TextWidget();
	        	return false;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	 }
	 
	 public float getDistance(Box b) {
		 float n[] = new float[3];
		 float p[] = new float[3];
		 float c[] = new float[3];
		 float t[] = new float[3];
		 
		 p[0] = scene.camera().position.x;
		 p[1] = scene.camera().position.y;
		 p[2] = scene.camera().position.z;
		 
		 c[0] = b.position().x;
		 c[1] = b.position().y;
		 c[2] = b.position().z;
		 
		 n[0] = -b.rotation().x;
		 n[1] = -b.rotation().y;
		 n[2] = -b.rotation().z;
		 
		 float normd = (float) Math.sqrt(scene.camera().target.x*scene.camera().target.x+scene.camera().target.y*scene.camera().target.y+scene.camera().target.z*scene.camera().target.z);
		 
		 t[0] = scene.camera().target.x/normd;
		 t[1] = scene.camera().target.y/normd;
		 t[2] = scene.camera().target.z/normd;
		 
		 float dist = -(n[0]*(p[0]-c[0])+n[1]*(p[1]-c[1])+n[2]*(p[2]-c[2]))/(t[0]*n[0]+t[1]*n[1]+t[2]*n[2]);
		 
		 Log.d("Distance", ""+dist);
		 
		 return dist;
		 
	 }

	    @Override
	 public void onOptionsMenuClosed(Menu menu) {
	        // Nothing else to do, closing the Activity.
	    //finish();
	 }
	
}
