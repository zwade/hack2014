package me.zwad3.mosaic;

import min3d.objectPrimitives.SkyBox;
import min3d.vos.Light;

public class ExampleImplement extends Renderer3D {
	private SkyBox mSkyBox;
	public void initScene() {
		super.initScene();
		scene.lights().add(new Light());
		
		mSkyBox = new SkyBox(5.0f, 2);
		mSkyBox.addTexture(SkyBox.Face.North, 	R.drawable.wood_back, 	"north");
		mSkyBox.addTexture(SkyBox.Face.East, 	R.drawable.wood_right, 	"east");
		mSkyBox.addTexture(SkyBox.Face.South, 	R.drawable.wood_back, 	"south");
		mSkyBox.addTexture(SkyBox.Face.West, 	R.drawable.wood_left, 	"west");
		mSkyBox.addTexture(SkyBox.Face.Up,		R.drawable.ceiling, 	"up");
		mSkyBox.addTexture(SkyBox.Face.Down, 	R.drawable.floor, 		"down");
		mSkyBox.scale().y = 0.8f;
		mSkyBox.scale().z = 2.0f;
		scene.addChild(mSkyBox);
	}
}
