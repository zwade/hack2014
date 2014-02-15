package me.zwad3.mosaic.widget;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

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

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.util.Xml;

public class BreakingNewsWidget extends Widget {
	
	int t;
	String[] headlines;
	
	public BreakingNewsWidget(){
		t = 0;
	}
	
	@Override
	public boolean needsUpdate() {
		t++;
		if(t % 240 != 0){
			return false;
		}
		HttpGet uri = new HttpGet("http://api.usatoday.com/open/breaking?expired=true&api_key=qgvnw4xg73mmsr6m9vuks5ke");
		DefaultHttpClient client = new DefaultHttpClient();
		HttpResponse resp;
		try{
			resp = client.execute(uri);
		} catch(Exception e){
			Log.d("ERROR", e.getMessage());
			return false;
		}
		StatusLine status = resp.getStatusLine();
		if(status.getStatusCode() != 200){
			Log.d("ERROR", "HTTP error, invalid server status code.");
			return false;
		}
		try{
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = builder.parse(resp.getEntity().getContent());
			NodeList hls = doc.getElementsByTagName("title");
			headlines = new String[hls.getLength()-2];
			for(int i = 2; i < hls.getLength(); i++){
				headlines[i-2] = hls.item(i).getTextContent();
			}
			Log.d("YAY", "success... " + Arrays.toString(headlines));
			return true;
		} catch(Exception e){
			Log.d("ERROR", e.getMessage());
		}
		return false;
	}
	
	@Override
	public Bitmap renderBitmap() {
		/*Log.d("...", "rendering...");
		Paint paint = new Paint();
		paint.setColor(Color.RED);
		paint.setTextAlign(Paint.Align.LEFT);
		Bitmap image = Bitmap.createBitmap(256, 256, Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(image);
		canvas.drawRect(0, 0, 256, 256, paint);
		paint.setColor(Color.BLACK);
		paint.setTextSize(48);
		for(int i = 0; i < headlines.length; i++){
			canvas.drawText(headlines[i], 10, 24*i, paint);
		}
		return image;*/
		
		Paint paint = new Paint();
		paint.setColor(Color.RED);
		paint.setTextAlign(Paint.Align.LEFT);
		Bitmap image = Bitmap.createBitmap(256, 256, Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(image);
		canvas.drawRect(0, 0, 256, 256, paint);
		return image;
	}

}
