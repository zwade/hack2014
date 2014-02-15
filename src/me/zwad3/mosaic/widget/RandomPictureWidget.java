package me.zwad3.mosaic.widget;

import android.content.ContentResolver;
import android.graphics.Bitmap;
import android.provider.MediaStore.Images.ImageColumns;

public class RandomPictureWidget implements Widget {
	
	private int updateCallCount;
	
	public RandomPictureWidget(){
		updateCallCount = 0;
	}
	
	@Override
	public boolean needsUpdate() {
		updateCallCount++;
		return updateCallCount == 0;
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
		
		return null;
	}

}
