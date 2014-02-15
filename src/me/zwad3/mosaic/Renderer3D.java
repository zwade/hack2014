package me.zwad3.mosaic;
//package com.google.android.glass.sample.compass;
import java.util.List;


import com.google.orientation.*;

import me.zwad3.mosaic.widget.VoiceListener;
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
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.hardware.GeomagneticField;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.util.Log;
import android.view.WindowManager;

public class Renderer3D extends MosaicActivity implements SensorEventListener {
	protected static final float TOO_STEEP_PITCH_DEGREES = 70f;

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
	private GeomagneticField mGeomagneticField;
	private float[] mOrientation= new float[9];
	private float mHeading;
    private Location mLocation;
    private boolean mHasInterference;
    private float[] mRotationMatrix= new float[16];
    private float mPitch;
    
    private VoiceListener _vl;
    
	 private boolean mTooSteep;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mCompass = mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        mAccVals = new Number3d();
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
	}

    
	public void initScene()
	{
		
		
		
		

		mSensorManager.registerListener(this,
                mSensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR),
                SensorManager.SENSOR_DELAY_UI);
		mSensorManager.registerListener(this,
                mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_UI);
	}
	
	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {

	}
	 
	float[] mGravity;
	float[] mGeomagnetic;
	
    

	
	public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ROTATION_VECTOR) {

            SensorManager.getRotationMatrixFromVector(mRotationMatrix, event.values);
            SensorManager.remapCoordinateSystem(mRotationMatrix, SensorManager.AXIS_X,
                    SensorManager.AXIS_Z, mRotationMatrix);
            SensorManager.getOrientation(mRotationMatrix, mOrientation);

            mPitch = (float) Math.toDegrees(mOrientation[1]);

            float magneticHeading = (float) Math.toDegrees(mOrientation[0]);
            mHeading = MathUtils.mod(computeTrueNorth(magneticHeading), 360.0f)
                    - 6;
            scene.camera().target.z = (float) Math.sin(mHeading*Math.PI/180);
    		scene.camera().target.x = (float) Math.cos(mHeading*Math.PI/180);
    		
    		
    		scene.camera().position.z = 0f; 
    		scene.camera().position.y = 0; 
         
        } else if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
  
			mAccVals.y = (float) (event.values[2] * FILTERING_FACTOR + mAccVals.y * (1.0 - FILTERING_FACTOR));
			
        	scene.camera().position.y = mAccVals.y * .3f;
        	
        	scene.camera().target.y = -scene.camera().position.y;
        
        }
    }
	


	private void updateGeomagneticField() {
        mGeomagneticField = new GeomagneticField((float) mLocation.getLatitude(),
                (float) mLocation.getLongitude(), (float) mLocation.getAltitude(),
                mLocation.getTime());
    }

    /**
     * Use the magnetic field to compute true (geographic) north from the specified heading
     * relative to magnetic north.
     *
     * @param heading the heading (in degrees) relative to magnetic north
     * @return the heading (in degrees) relative to true north
     */
    private float computeTrueNorth(float heading) {
        if (mGeomagneticField != null) {
            return heading + mGeomagneticField.getDeclination();
        } else {
            return heading;
        }
    }
    @Override
	public void getVoiceInput(VoiceListener v) {
    	Log.d("voice request","boo");
    	if (_vl == null) {
    		_vl = v;
    		displaySpeechRecognizer();
    	}
		
	}
	
	private void displaySpeechRecognizer() {
	    Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
	    startActivityForResult(intent, 0);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode,
	        Intent data) {
	    if (requestCode == 0 && resultCode == RESULT_OK) {
	        List<String> results = data.getStringArrayListExtra(
	                RecognizerIntent.EXTRA_RESULTS);
	        String spokenText = results.get(0);
	        // Do something with spokenText.
	        if (_vl != null) {
	        	_vl.receiveSTT(spokenText);
	        	_vl = null;
	        }
	    }
	    super.onActivityResult(requestCode, resultCode, data);
	}

}
