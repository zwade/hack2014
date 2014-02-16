package me.zwad3.mosaic.widget;

import me.zwad3.mosaic.MosaicActivity;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.Log;

public class LauncherWidget extends Widget {
	
	private ResolveInfo myResolve;
	private PackageManager pacman;
	
	private boolean isFirst = true;

	public LauncherWidget(MosaicActivity a) {
		super(a);
		// TODO Auto-generated constructor stub
	}
	public void setResolve(ResolveInfo ri,PackageManager pm) {
		myResolve = ri;
		pacman = pm;
	}
	@Override
	public boolean needsUpdate(int dt) {
		
		if (isFirst) {
			isFirst = false;
			return true;
		}
		// TODO Auto-generated method stub
		return false;
	}
	public boolean hasInteraction() {
		return true;
	}
	public void onInteraction() {
		parent.startActivity(pacman.getLaunchIntentForPackage(myResolve.activityInfo.applicationInfo.packageName));
	}

	@Override
	public Bitmap renderBitmap() {
		
		
		return textToBitmap ((String)myResolve.activityInfo.applicationInfo.loadLabel(pacman));
	
	}
	public static Bitmap textToBitmap (String s) {
	    Bitmap bitmap = Bitmap.createBitmap(512, 512, Config.ARGB_8888);
	    Canvas canvas = new Canvas(bitmap); 
	    Paint paint = new Paint();
		paint.setTextAlign(Paint.Align.LEFT);
		paint.setTypeface(Typeface.create("Roboto",Typeface.BOLD));
		paint.setColor(Color.WHITE);
		paint.setTextSize(50);
		TextPaint textp = new TextPaint(paint);
		StaticLayout sl = new StaticLayout("Launch App\n"+s, textp, 496, Layout.Alignment.ALIGN_CENTER, 1.0f, 0.0f, false);
		canvas.translate(8, 132);
		sl.draw(canvas);

	    return bitmap;
	}

}
