package me.zwad3.mosaic;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.widget.PopupMenu;
import me.zwad3.mosaic.widget.BreakingNewsWidget;
import me.zwad3.mosaic.widget.ClockWidget;
import me.zwad3.mosaic.widget.RandomPictureWidget;
import me.zwad3.mosaic.widget.StickyNoteWidget;
import me.zwad3.mosaic.widget.TextWidget;
import me.zwad3.mosaic.widget.VoiceListener;
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
	private Box needsDelete;
	
	private float latestCoords[] = new float[6];
	
	private VoiceListener _vl;
	private int level = 0;
	
	private Box target;
	
	@Override
	public void onCreate(Bundle sbi) {
		if (level <= 1) {
			level++;
		}
		super.onCreate(sbi);
	}

	public void initScene() {
		Log.d("Level",""+level);
		super.initScene();
		scene.lights().add(new Light());
		if (objects == null) {
			Log.d("Object",  "are null");
			lastUpdate = Calendar.getInstance().getTime().getTime();
			objects = new HashMap<Box, Widget>();
		} else {
			for (Box i:objects.keySet()) {
				initTexture(objects.get(i),i);
				scene.addChild(i);
				
			}
			Log.d("Num",""+objects.size());
		}

		
		
	}
	public boolean onKeyDown(int keycode, KeyEvent event) {
	        if (keycode == KeyEvent.KEYCODE_DPAD_CENTER) {
	        	latestCoords[0] = scene.camera().target.x;
	        	latestCoords[1] = scene.camera().target.y;
	        	latestCoords[2] = scene.camera().target.z;
	        	latestCoords[3] = scene.camera().position.x;
	        	latestCoords[4] = scene.camera().position.y;
	        	latestCoords[5] = scene.camera().position.z;
	        	
	        	target = null;
	        	for (Box b:objects.keySet()) {
	        		if (getDistance(b)) {
	        			Log.d("Clicked Entity", b.toString());
	        			target = b;
	        		}
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
		//Log.d("Camera",""+scene.camera().target.x+" "+scene.camera().target.y+" "+scene.camera().target.z);
		if (needsUpdate != null) {
			Log.d("Objects@Needs",""+objects.size());
			
			
            Box tmp = new Box(2,2,0);
            tmp.position().x = latestCoords[0]*5;
            tmp.position().y = latestCoords[1]*5;
            tmp.position().z = latestCoords[2]*5;
            
            float x = latestCoords[0]-latestCoords[3];
            float y = latestCoords[1]-latestCoords[4];
            float z = latestCoords[2]-latestCoords[5];
            
            float normal[] = {x,y,z};
            tmp.setNormal(normal);
            //Log.d("BOB",""+scene.camera().position.y);
            
            //float den = (float) Math.sqrt(x*x+y*y+z*z);
            tmp.rotation().y =(float) ((float) Math.atan(x/z)*180/Math.PI);
            //tmp.rotation().z = 0;//(float) ((float) 90-Math.acos(y/den)*180/Math.PI);
            //tmp.rotation().x = 0;//(float) ((float) 90-Math.acos(z/den)*180/Math.PI);
            //tmp.rotation().z = (float) Math.cos(Math.acos(z/den)*Math.PI/180)*360;
            //tmp.rotation().x = (float) ((float) Math.sin(Math.acos(z/den)*Math.PI/180)*Math.sin(Math.acos(y/den)*Math.PI/180))*360;
            //tmp.rotation().y = (float) ((float) Math.sin(Math.acos(z/den)*Math.PI/180)*Math.cos(Math.acos(x/den)*Math.PI/180))*360;
        
            //("target and rotation",""+x+" "+y+" "+z+" "+tmp.rotation().x+" "+tmp.rotation().y+" "+tmp.rotation().z);
            
            //loat phi = (float)(Math.PI/2 - Math.atan(z));
            //float theta = (float)(Math.atan2(y, x));
            float r = 20;
            
            //tmp.position().x = (float)(r * Math.cos(theta) * Math.sin(phi));
            //tmp.position().y = (float)(r * Math.sin(theta) * Math.sin(phi));
            //tmp.position().z = (float)(r * Math.cos(phi));
            
            //tmp.rotation().x = (float)(180/Math.PI*Math.atan2(tmp.position().y, tmp.position().x));
            //tmp.rotation().y = (float)(180/Math.PI*Math.atan2(tmp.position().x, tmp.position().z));
            //tmp.rotation().z = (float)(180/Math.PI*Math.atan2(tmp.position().z, tmp.position().y));
           
            
            
            Log.d("pos, d, angles", x + " " + y + " " + z + " " + r + " " + tmp.rotation().x + " " + tmp.rotation().y + " " + tmp.rotation().z);
            
            //TextWidget txt = new TextWidget();
            initTexture(needsUpdate, tmp);
            objects.put(tmp, needsUpdate);
            scene.addChild(tmp);
            
            needsUpdate = null;
		} else if (needsDelete != null) {
			scene.removeChild(needsDelete);
			objects.remove(needsDelete);
			needsDelete = null;
		}
		long time = Calendar.getInstance().getTime().getTime();
		long dt = time-lastUpdate;
		lastUpdate = time;
		for (Box i:objects.keySet()) {
			if (objects.get(i).needsUpdate((int)dt)) {
				loadTexture(objects.get(i),i);
			}
		}		
	}
	private boolean loadTexture(Widget w, Box b) {
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
	private boolean initTexture(Widget w, Box b) {
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
		Log.d("target", ""+target);
	    MenuInflater inflater = getMenuInflater();
	    if (target == null) {
	    	inflater.inflate(R.menu.newitem, menu);
	    } else {
	    	inflater.inflate(R.menu.interactmenu, menu);
	    }
	    return true;
	 }
	 public boolean onPrepareOptionsMenu(Menu menu) {
			Log.d("target", ""+target);
		    MenuInflater inflater = getMenuInflater();
		    menu.clear();

		    if (target == null) {
		    	inflater.inflate(R.menu.newitem, menu);
		    } else {
		    	inflater.inflate(R.menu.interactmenu, menu);
		    }
		    return true;
		 }

	 @Override
	 public boolean onOptionsItemSelected(MenuItem item) {
	    // Handle item selection.
		 Log.d("Objects",""+objects.size());
	    switch (item.getItemId()) {
	        case R.id.clock:
	        	//.d("hi", "clock");
	        	needsUpdate = new ClockWidget(this);
	            return false;
	        case R.id.blank:
	        	needsUpdate = new TextWidget(null);
	        	return false;
	        case R.id.sticky:
	        	needsUpdate = new StickyNoteWidget(this);
	        	return false;
	        case R.id.news:
	        	needsUpdate = new BreakingNewsWidget(this);
	        	return false;
	        case R.id.pictures:
	        	needsUpdate = new RandomPictureWidget(this);
	        	return false;
	        case R.id.delete:
	        	needsDelete = target;
	        	target = null;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	 }
	 
	 public boolean getDistance(Box b) {
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
		 
		 n[0] = -b.getNormal()[0];
		 n[1] = -b.getNormal()[1];
		 n[2] = -b.getNormal()[2];
		 
		 float normd = (float) Math.sqrt(scene.camera().target.x*scene.camera().target.x+scene.camera().target.y*scene.camera().target.y+scene.camera().target.z*scene.camera().target.z);
		 
		 t[0] = scene.camera().target.x/normd;
		 t[1] = scene.camera().target.y/normd;
		 t[2] = scene.camera().target.z/normd;
		 
		 float dist = -(n[0]*(p[0]-c[0])+n[1]*(p[1]-c[1])+n[2]*(p[2]-c[2]))/(t[0]*n[0]+t[1]*n[1]+t[2]*n[2]);
		 
		 return (Math.abs(p[0]+dist*t[0]-c[0])< 1 && Math.abs(p[1]+dist*t[1]-c[1]) < 1 && Math.abs(p[2]+dist*t[2]-c[2]) < 1);
		 
	 }

	
	
}
