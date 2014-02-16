package me.zwad3.mosaic;

import me.zwad3.mosaic.widget.*;
import min3d.Shared;
import min3d.Utils;
import min3d.core.Object3dContainer;
import min3d.core.RendererActivity;
import min3d.objectPrimitives.Box;
import min3d.objectPrimitives.SkyBox;
import min3d.parser.IParser;
import min3d.parser.Parser;
import min3d.vos.Light;
import min3d.vos.Number3d;
import min3d.vos.TextureVo;
import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;

public class BitmapActivity extends Renderer3D implements SensorEventListener {
	private final float FILTERING_FACTOR = .3f;
	
	private SkyBox mSkyBox;
	private SensorManager mSensorManager;
	private Sensor mAccelerometer;
	private Sensor mCompass;
	private Number3d mAccVals;
	private Object3dContainer mMonster;
	private float _theta = 0;
	private float _omega = 0;
	private final float _alpha = 2; 
	private final float _thresh = 10;
	private Object3dContainer _cube;
	
	private Widget widget;
	
	public static Activity context;
	
	@Override
	
    public void onCreate(Bundle savedInstanceState) {
		Log.d("At Least", "it didn't hit this");
		context = this;
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mCompass = mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        mAccVals = new Number3d();
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
	}
	/*@Override
	public void onPause() {
		
		//Log.d("exit", "boo");
		//System.exit(0);
	}*/
	
	public void initScene()
	{
		Log.d("Initing Scene", "<-- Yup");
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
		
		Log.d("hi", "init");
		
		_cube = new Box(2f,2f,2f);
		scene.addChild(_cube);
		
		//Bitmap b = Utils.makeBitmapFromResourceId(this, R.drawable.uglysquares);
		//Shared.textureManager().addTextureId(b, "uglysquares", false);
		//b.recycle();
		//TextureVo texture = new TextureVo("uglysquares");
		//_cube.textures().add(texture);
		
		//IParser parser = Parser.createParser(Parser.Type.MAX_3DS,
		//		getResources(), "min3d.sampleProject1:raw/monster_high", true);
		//parser.parse();

		//mMonster = parser.getParsedObject();
		//mMonster.scale().x = mMonster.scale().y = mMonster.scale().z  = .1f;
		//mMonster.position().y = -2.5f;
		//mMonster.position().z = -3;
		//scene.addChild(mMonster);
			
		mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_UI);
		mSensorManager.registerListener(this, mCompass, SensorManager.SENSOR_DELAY_UI);
		
		widget = new WeatherWidget(this);
		loadTexture(widget);
	}
	
	public void updateScene(){
		if(widget.needsUpdate(1000/40)){
			/**if(Shared.textureManager().contains(widget.toString())){
				Shared.textureManager().deleteTexture(widget.toString());
			}
			Shared.textureManager().addTextureId(widget.renderBitmap(), widget.toString());**/
			widget.renderBitmap((Box)_cube);
		}
	}
	
	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// TODO Auto-generated method stub
	}
	 
	float[] mGravity;
	float[] mGeomagnetic;
	public void onSensorChanged(SensorEvent event) {
		if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER)
			mGravity = event.values;
	    if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD)
	    	mGeomagnetic = event.values;
	    if (mGravity != null && mGeomagnetic != null) {
	    	float R[] = new float[9];
	    	float I[] = new float[9];
	    	boolean success = SensorManager.getRotationMatrix(R, I, mGravity, mGeomagnetic);
	    	if (success) {
	    		float orientation[] = new float[3];
	    		SensorManager.getOrientation(R, orientation);
	    		float newDir = orientation[0];
	    		int dir = getDirection(newDir, _theta);
	    		
	    		//azimuth = orientation[0]; // orientation contains: azimuth, pitch and roll
	    		//scene.camera().target.z = 1;//(float) Math.sin(orientation[0]);//Math.sin(orientation[0]*180/Math.PI);
	    		//scene.camera().target.x = 1;//(float) Math.cos(orientation[0]);//Math.cos(orientation[0]*180/Math.PI);
	    		
	    		scene.camera().target = _cube.position();
	    		
	    		scene.camera().position.z = -5f; 
	    		scene.camera().position.y = 0; 
	    		
	    		_theta = orientation[0];
	    		_omega = _omega + _theta*dir*_alpha;
	    		
	    		
	    	}
	    }
	}
	
	
	private int getDirection(float newD,float oldD) {
		
		
		return 1;
	}
	
	private boolean loadTexture(Widget w) {
		if (!w.needsUpdate(1000/40)) {
			return false;
		}
		/**Shared.textureManager().addTextureId(w.renderBitmap(), w.toString(), false);
			
		TextureVo texture = new TextureVo(w.toString());

		_cube.textures().add(texture);
		
		return true;**/
		
		w.renderBitmap((Box)_cube);
		return true;
	}

	/**
	@Override
	public void onSensorChanged(SensorEvent event) {
		if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
  
		
			// low-pass filter to make the movement more stable
			//mAccVals.x = (float) (event.values[0] * FILTERING_FACTOR + mAccVals.x * (1.0 - FILTERING_FACTOR));
			mAccVals.y = (float) (event.values[2] * FILTERING_FACTOR + mAccVals.y * (1.0 - FILTERING_FACTOR));
			
			//scene.camera().position.x = mAccVals.x * .2f;
        	scene.camera().position.y = mAccVals.y * .3f;
        	
        	//scene.camera().target.x = -scene.camera().position.x;
        	scene.camera().target.y = -scene.camera().position.y;
        
        	//Log.d("Accel Data", event.values[0]+" "+event.values[1]+" "+event.values[2]);
		} else if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
			float rot = event.values[2];
			Log.d("Woah", ""+rot);
		}
	}
	**/
}
