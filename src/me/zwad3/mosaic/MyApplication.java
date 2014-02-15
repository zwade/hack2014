package me.zwad3.mosaic;

import android.app.Application;
import android.content.Context;
import android.util.Log;

public class MyApplication extends Application {

    private static Context context;

	public void onCreate(){
	    super.onCreate();
	    Log.d("creating", "WOOT");
	    MyApplication.context = getApplicationContext();
	}

	public static Context getAppContext() {
		Log.d("whateva", MyApplication.context.toString());
	    return MyApplication.context;
	}
}
