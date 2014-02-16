package me.zwad3.mosaic.widget;

import java.io.IOException;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
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

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.Log;
import android.util.Xml;

public class WeatherWidget extends Widget {
	
	private final int threshold = 15000;
	private int time = 0;
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
	
	@Override
	public Bitmap renderBitmap() {
		
		HttpGet uri = new HttpGet("http://api.worldweatheronline.com/free/v1/weather.ashx?q=London&format=json&num_of_days=5&key=9dhmskjufq6yedjyy9j32awc");
		
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
			return makeImage("Can't connect to network");
		}
		String message;
		try{
			JSONObject weather = resp.getJSONObject("data").getJSONArray("weather").getJSONObject(0);
			
			Log.d("hey",""+weather.toString());
			headlines = new ArrayList<String>();
<<<<<<< HEAD
			
			
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
			
		} catch(Exception e){
			Log.d("ERROR", e.getMessage());
			return makeImage("Can't connect to network.");
		}
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
		return makeImage(message);
	}
		
	private Bitmap makeImage (String headline) {
		
		AssetManager assetManager = MyApplication.getAppContext().getAssets();
		Bitmap bmp = null;
		try{
			InputStream inp = assetManager.open("widgets/breaking-news-widget.bmp");
			bmp = Bitmap.createBitmap(BitmapFactory.decodeStream(inp, null, null));
		} catch(Exception e){
			Log.d("no", "nop");
		}
		Paint paint = new Paint();
		paint.setTextAlign(Paint.Align.LEFT);
		paint.setTypeface(Typeface.create("Roboto",Typeface.BOLD));
		Bitmap bitmap = bmp.copy(Bitmap.Config.ARGB_8888, true);
		Canvas canvas = new Canvas(bitmap);
		paint.setColor(0xFF08418C);
		paint.setTextSize(36);
		String str = "";
		

		TextPaint textp = new TextPaint(paint);
		textp.baselineShift = 100;
		StaticLayout sl = new StaticLayout(headline, textp, 496, Layout.Alignment.ALIGN_CENTER, 1.0f, 0.0f, false);
		Log.d("lines", "" + sl.getLineCount());
		/*for(int i = 0; i < headlines.length; i++){
			canvas.drawText(headlines[i], 10, 132 + 48*(i+1), paint);
		}*/
		canvas.translate(8, 132);
		sl.draw(canvas);
		return bitmap;
	}
}
