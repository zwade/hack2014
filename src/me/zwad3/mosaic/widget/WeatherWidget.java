package me.zwad3.mosaic.widget;

import java.io.IOException;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.SortedSet;

import org.json.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import me.zwad3.mosaic.BitmapActivity;
import me.zwad3.mosaic.MosaicActivity;
import me.zwad3.mosaic.MyApplication;

import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xmlpull.v1.XmlPullParser;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.Log;
import android.util.Xml;

public class WeatherWidget extends Widget {
	
	private final int threshold = 15000;
	private int time = 0;
	public Context con = MyApplication.getAppContext();
	ArrayList<String> headlines;
	
	public WeatherWidget(MosaicActivity a){
		super(a);
		headlines = new ArrayList<String>();
	}
	
	@Override
	public boolean needsUpdate(int dt) {
		time -= dt;
		if (time <= 0) {
			time = threshold+time;
			return true;
		}
		return false;

	}
	private class MyLocationListener implements LocationListener {

	    private String city;
	    public void onLocationChanged(Location loc) {
	    	
	        String longitude = "Longitude: " + loc.getLongitude();

	        String latitude = "Latitude: " + loc.getLatitude();
	        /*----------to get City-Name from coordinates ------------- */
	        String cityName = null;
	        Geocoder gcd = new Geocoder(con, Locale.getDefault());
	        List<Address> addresses;
	        try {
	            addresses = gcd.getFromLocation(loc.getLatitude(),
	                    loc.getLongitude(), 1);
	            if (addresses.size() > 0)
	                System.out.println(addresses.get(0).getLocality());
	            cityName = addresses.get(0).getLocality();
	            city=cityName;
	        } catch (IOException e) {
	            e.printStackTrace();
	        }

	    }
	    public String getCity()
	    {
	    	return city;
	    }
	    @Override
	    public void onProviderDisabled(String provider) {
	        // TODO Auto-generated method stub
	    }

	    @Override
	    public void onProviderEnabled(String provider) {
	        // TODO Auto-generated method stub
	    }

	    @Override
	    public void onStatusChanged(String provider, int status, Bundle extras) {
	        // TODO Auto-generated method stub
	    }
	} 
	
	@Override
	public Bitmap renderBitmap() {
		Log.d("rendering", "...");
		MyLocationListener bob =new MyLocationListener();
		String ct="Philadelphia";//bob.getCity();
		Log.d("city", "" + (ct == null));
		HttpGet uri = new HttpGet("http://api.worldweatheronline.com/free/v1/weather.ashx?q="+ct+"&format=json&num_of_days=5&key=9dhmskjufq6yedjyy9j32awc");
		
		DefaultHttpClient client = new DefaultHttpClient();
		HttpResponse respo = null;
		JSONObject resp;
		try{
			respo = client.execute(uri);
			InputStream is = respo.getEntity().getContent();
			//ArrayList<Character> str = new ArrayList<Character>();
			StringBuilder sb = new StringBuilder();
			
			int a = is.read(); 
			
			while(a != -1) {
				sb.append((char)a);
				a = is.read();
			}
			Log.d("string", ""+sb.toString());
			//Log.d("Potential JSON", ""+respo.getEntity().getContent().);
			resp = new JSONObject(sb.toString());
			//JSONObject.
			//resp.getJSONArray("data");
		} catch(Exception e){
			Log.d("ERROR", "Halp "+e.getMessage());
			return null; //BUT DON'T REALLY
		}
		String message;
		try{
			JSONObject weather = resp.getJSONObject("data").getJSONArray("weather").getJSONObject(0);
			
			Log.d("hey",""+weather.toString());
			headlines = new ArrayList<String>();

			
			/**for(int i = 0; i < weather.length()-1; i++){
				Set<String> s=weather.getJSONObject(i).keySet();
=======
			for(int i = 0; i < weather.length()-1; i++){
				//Set<String> s=weather.getJSONObject(i).keys();
>>>>>>> 7ef33df8615e5cd7ad6c6cc8a7c9004ad515544c
				for(String str: s){
						String[] eclipseIsStupid = new String[1];
						eclipseIsStupid[0]=(str+": "+weather.getJSONObject(i).getString(str)+ ">");
						headlines.add(eclipseIsStupid[0]);
					}
				}
			
			if(headlines.size() == 0){
				headlines = new ArrayList<String>();
				headlines.add("No Weather");
				Log.d("Nonews", "No weather.");
			}
			Log.d("YAY", "success... " + headlines);
			**/
			
			//headlines = new ArrayList<String>();
			//headlines.add(weather.getJSONArray("weatherDesc").getJSONObject(0).getString("value"));
			
			
			message = "\nTomorrow's Forecast is "+weather.getJSONArray("weatherDesc").getJSONObject(0).getString("value")+"\n\nWith a high of "+weather.getString("tempMaxF")+"\n\nAnd a Low of "+weather.getString("tempMinF");
			
		
		/**Log.d("...", "rendering...");
		String [] dumb = new String[100];
		int last=0;
		for(int i=0; i<headlines.size(); i++)
		{
			if(headlines.get(i)==">")
			{
				String listString0 = "";
				for (String srt : headlines.subList(last,i))
				{
				    listString0 += srt + "\t";
				}
				dumb[i]=listString0;
				last=i+1;
			}
		}
		**/
		
		
		
		AssetManager assetManager = MyApplication.getAppContext().getAssets();
		Bitmap bmp = null;
		try{
			InputStream inp = assetManager.open("widgets/weather-widget.bmp");
			bmp = Bitmap.createBitmap(BitmapFactory.decodeStream(inp, null, null));
		} catch(Exception e){
			Log.d("no", "nop");
		}
		Paint paint = new Paint();
		paint.setTextAlign(Paint.Align.LEFT);
		paint.setTypeface(Typeface.create("Roboto",Typeface.BOLD));
		Bitmap bitmap = bmp.copy(Bitmap.Config.ARGB_8888, true);
		Canvas canvas = new Canvas(bitmap);
		paint.setColor(0xFFDDDDDD);
		paint.setTextSize(36);
		canvas.drawText("for " + ct, 226, 50, paint);
		
		String ctemp = resp.getJSONObject("data").getJSONArray("current_condition").getJSONObject(0).getString("temp_F");
		paint.setColor(0xFF000000);
		paint.setTextSize(200);
		canvas.drawText(ctemp + "\u00B0", 8, 322, paint);
		
		JSONObject cw = resp.getJSONObject("data").getJSONArray("weather").getJSONObject(0);
		paint.setColor(0xFFFF0000);
		paint.setTextSize(100);
		canvas.drawText(cw.getString("tempMaxF") + "\u00B0", 58, 154, paint);
		paint.setColor(0xFF0000FF);
		paint.setTextSize(100);
		canvas.drawText(cw.getString("tempMinF") + "\u00B0", 58, 420, paint);
		
		JSONObject tom = resp.getJSONObject("data").getJSONArray("weather").getJSONObject(0);
		
		paint.setColor(0xFF0000FF);
		paint.setTextSize(40);
		canvas.drawText(tom.getString("tempMinF") + "\u00B0", 200, 508, paint);
		
		paint.setColor(0xFFFF0000);
		canvas.drawText(tom.getString("tempMaxF") + "\u00B0", 276, 508, paint);
		
		paint.setColor(0xFFFFFFFF);
		canvas.drawText("/", 250, 508, paint);
		paint.setTextSize(32);
		canvas.drawText("Tomorrow: ", 4, 504, paint);
		canvas.drawText(tom.getJSONArray("weatherDesc").getJSONObject(0).getString("value"), 350, 504, paint);
		
		return bitmap;
		} catch(Exception e){
			Log.d("ERROR", e.getMessage());
			return null; //CHANGE
		}
	}
}
