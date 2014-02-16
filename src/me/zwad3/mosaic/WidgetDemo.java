package me.zwad3.mosaic;

import java.util.ArrayList;

import me.zwad3.mosaic.widget.BlankWidget;
import me.zwad3.mosaic.widget.BreakingNewsWidget;
import me.zwad3.mosaic.widget.ClockWidget;
import me.zwad3.mosaic.widget.RandomPictureWidget;
import me.zwad3.mosaic.widget.WeatherWidget;
import me.zwad3.mosaic.widget.Widget;
import min3d.Shared;
import min3d.objectPrimitives.Box;
import min3d.vos.TextureVo;
import android.util.Log;

public class WidgetDemo extends ExampleImplement {
	
	private ArrayList<Widget> mine = new ArrayList<Widget>();
	private boolean done = false;
	@Override
	public void initScene() {
		super.initScene();
		
		mine.add(new RandomPictureWidget(this));
		mine.add(new BreakingNewsWidget(this));
		mine.add(new BlankWidget(this));
		mine.add(new WeatherWidget(this));
		mine.add(new ClockWidget(this));
	}
	
	@Override
	public void updateScene() {
		super.updateScene();
		
		if (!(myHeading > 0) || done) {
			return;
		} 
		
		for (int i = 0; i < 5; i++) {
		
			Box tmp = new Box(2,2,0);
			
			float tmpHeading = myHeading + 45*(i-2);
			
			float latestCoords[] = {(float) Math.cos(tmpHeading*Math.PI/180),0,(float) Math.sin(tmpHeading*Math.PI/180),0,0,0};
			
        	tmp.position().x = latestCoords[0]*5;
        	tmp.position().y = latestCoords[1]*5;
        	tmp.position().z = latestCoords[2]*5;
        	
        	float x = latestCoords[0]-latestCoords[3];
        	float y = latestCoords[1]-latestCoords[4];
        	float z = latestCoords[2]-latestCoords[5];
        	
        	float normal[] = {x,y,z};
        	tmp.setNormal(normal);
  
        	tmp.rotation().y =(float) ((float) Math.atan(x/z)*180/Math.PI);
    
        	float r = 20;
        	
        	Widget needsUpdate = mine.get(i);
        	
        	Log.d("Done",needsUpdate+"");
        	
        	initTexture(needsUpdate, tmp);
        	super.objects.put(tmp, needsUpdate);
        	scene.addChild(tmp);
        	done = true;
		}
	}
	private boolean initTexture(Widget w, Box b) {
		//Shared.textureManager().deleteTextureId(w.renderBitmap(), w.toString(), false);
		try {
			Shared.textureManager().deleteTexture(w.toString());
		} catch (Exception e) {
			
		}
		Shared.textureManager().addTextureId(w.renderBitmap(), w.toString(), false);
			
		TextureVo texture = new TextureVo(w.toString());

		b.textures().add(texture);
		
		return true;
	}
}
