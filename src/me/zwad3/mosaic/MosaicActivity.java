package me.zwad3.mosaic;

import me.zwad3.mosaic.widget.VoiceListener;
import min3d.core.RendererActivity;
import android.app.Activity;

public abstract class MosaicActivity extends RendererActivity {
	
	public abstract void getVoiceInput(VoiceListener v); 
	
}
