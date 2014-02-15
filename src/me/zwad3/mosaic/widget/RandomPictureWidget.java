package me.zwad3.mosaic.widget;

import me.zwad3.mosaic.BitmapActivity;
import android.content.ContentResolver;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.provider.MediaStore.Images.ImageColumns;

public class RandomPictureWidget implements Widget {
	
	private int updateCallCount;
	
	public RandomPictureWidget(){
		updateCallCount = 0;
	}
	
	@Override
	public boolean needsUpdate() {
		updateCallCount++;
		return updateCallCount % 100 == 0;
	}

	@Override
	public Bitmap renderBitmap() {
		String[] columns = new String[]{
				ImageColumns._ID,
				ImageColumns.TITLE,
				ImageColumns.DATA,
				ImageColumns.MIME_TYPE,
				ImageColumns.SIZE
		};
		ContentResolver cr = BitmapActivity.context.getContentResolver();
		Cursor cur = cr.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, columns, null, null, null);
		
		return null;
	}

}
