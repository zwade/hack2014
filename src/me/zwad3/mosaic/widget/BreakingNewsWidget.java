package me.zwad3.mosaic.widget;

import java.io.IOException;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;

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

public class BreakingNewsWidget extends Widget {
	
	int t;
	String[] headlines;
	
	public BreakingNewsWidget(MosaicActivity a){
		super(a);
		t = 0;
		headlines = new String[0];
	}
	
	@Override
	public boolean needsUpdate() {
		t++;
		if(t % 10 == 1){
			return true;
		}
		return false;
	}
	
	@Override
	public Bitmap renderBitmap() {
		
		
		HttpGet uri = new HttpGet("http://api.usatoday.com/open/breaking?expired=true&api_key=qgvnw4xg73mmsr6m9vuks5ke");
		DefaultHttpClient client = new DefaultHttpClient();
		HttpResponse resp = null;
		try{
			resp = client.execute(uri);
		} catch(Exception e){
			Log.d("ERROR", e.getMessage());
			headlines = new String[]{"Can't connect to network."};
			return makeImage(headlines);
		}
		StatusLine status = resp.getStatusLine();
		if(status.getStatusCode() != 200){
			Log.d("ERROR", "HTTP error, invalid server status code.");
			headlines = new String[]{"Can't connect to network."};
			return makeImage(headlines);
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
			if(headlines.length == 0){
				headlines = new String[]{"No Breaking News"};
				Log.d("Nonews", "No breaking news.");
			}
			Log.d("YAY", "success... " + Arrays.toString(headlines));
		} catch(Exception e){
			Log.d("ERROR", e.getMessage());
			headlines = new String[]{"Can't connect to network."};
			return makeImage(headlines);
		}
		Log.d("...", "rendering...");
		return makeImage(headlines);
	}
		
	private Bitmap makeImage (String[] headlines) {
		
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
		
		for(int i = 0; i < Math.min(3, headlines.length); i++){
			str += "\u00BB" + headlines[i] + "\n\n";
		}
		TextPaint textp = new TextPaint(paint);
		textp.baselineShift = 100;
		StaticLayout sl = new StaticLayout(str, textp, 496, Layout.Alignment.ALIGN_NORMAL, 1.0f, 0.0f, false);
		Log.d("lines", "" + sl.getLineCount());
		/*for(int i = 0; i < headlines.length; i++){
			canvas.drawText(headlines[i], 10, 132 + 48*(i+1), paint);
		}*/
		canvas.translate(8, 132);
		sl.draw(canvas);
		return bitmap;
	}
}
