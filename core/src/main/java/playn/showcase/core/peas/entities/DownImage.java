package playn.showcase.core.peas.entities;

import static playn.core.PlayN.graphics;

import playn.core.Canvas;
import playn.core.CanvasImage;
import playn.core.Image;
import playn.showcase.core.peas.PeaWorld;
import playn.showcase.core.peas.PeasDemo;

public class DownImage extends Entity{

	public static String TYPE = "DownImage";
	public static CanvasImage img;
	public static Canvas canvasAffiche;
	public static boolean isAlreadySet = false;
	
	public DownImage(PeaWorld peaWorld, float px, float py, float pangle) {
		super(peaWorld, px, py, pangle);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void initPreLoad(PeaWorld peaWorld) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void initPostLoad(PeaWorld peaWorld) {
	  layer.setRotation(0f); // total hack so we can portal horizontally but not rotate the image
	  peaWorld.staticLayerFront.add(layer);		
	}

	@Override
	float getWidth() {
//	  return PeaWorld.getWidth();
	  return img.width() * PeasDemo.physUnitPerScreenUnit;
	}

	@Override
	float getHeight() {
//	  return 3f;
	  return img.height() * PeasDemo.physUnitPerScreenUnit;
	}

	@Override
	public Image getImage() {
	  CanvasImage bandeAffiche = graphics().createImage(graphics().width(), 165);
	  Canvas canvas = bandeAffiche.canvas();
	  canvas.fillRect(0, 0, bandeAffiche.width(), 
		bandeAffiche.height());
	  canvas.setFillColor(0xFFFFFFFF);
	  if(!isAlreadySet) DownImage.img = bandeAffiche;
	  canvasAffiche = canvas;
	  return img;
	}

	@Override
	public void paint(float alpha) {
	  layer.setImage(img);
	}
}