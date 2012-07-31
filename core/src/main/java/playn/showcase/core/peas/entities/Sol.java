package playn.showcase.core.peas.entities;

import static playn.core.PlayN.graphics;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;

import playn.core.Canvas;
import playn.core.CanvasImage;
import playn.core.Image;
import playn.showcase.core.peas.PeaWorld;
import playn.showcase.core.peas.PeasDemo;

public class Sol extends StaticPhysicsEntity{

  private boolean isAlreadySet = false;
  
  public static String TYPE = "Sol";
  public Sol(PeaWorld peaWorld, World world, float x, float y, float angle) {
		super(peaWorld, world, x, y, angle);
		// TODO Auto-generated constructor stub
  }

  @Override
  Body initPhysicsBody(World world, float x, float y, float angle) {
	FixtureDef fixture = new FixtureDef();
	BodyDef bodyDef = new BodyDef();
	bodyDef.type = BodyType.STATIC;
	bodyDef.position = new Vec2(0, 0);
	Body body = world.createBody(bodyDef);
	
	System.out.println("###############Sol print####################");
	System.out.println("graphics().height() ="+graphics().height());
	System.out.println("graphics().width() ="+graphics().width());
	System.out.println("#############################################");
	
	PolygonShape polygonShape = new PolygonShape();
	Vec2[] polygon = new Vec2[4];
	polygon[0] = new Vec2(0, 0);
	polygon[1] = new Vec2(0, 4);
	polygon[2] = new Vec2(PeaWorld.getWidth(), 4);
	polygon[3] = new Vec2(PeaWorld.getWidth(), 0);
	polygonShape.set(polygon, polygon.length);
	
	fixture.shape = polygonShape;
	fixture.friction = 0.5f;
	fixture.restitution = 0.8f;
	body.createFixture(fixture);
	body.setTransform(new Vec2(x, y), angle);
	return body;
  }

	@Override
	float getWidth() {
//	  return 4f;
	  return PeaWorld.getWidth();
	}

	@Override
	float getHeight() {
	  return 4f;
	}

   @Override
	public Image getImage() {
//	 if(!isAlreadySet){ 
//	    if (bandeAffiche == null){
	    	CanvasImage bandeAffiche = graphics().createImage(
	    		Math.round(getWidth() / PeasDemo.physUnitPerScreenUnit),
	    		Math.round(getHeight() / PeasDemo.physUnitPerScreenUnit));
//	    }	
		
	    Canvas showInfo = bandeAffiche.canvas();
	    showInfo.setFillColor(0x00FFAA00);
	    showInfo.fillRect(bandeAffiche.width()/2f, 
			  bandeAffiche.height()/2f, 20, 20);
	    showInfo.setStrokeColor(0x00640088);
	    showInfo.drawText("E", bandeAffiche.width()/2f,
	    bandeAffiche.height()/2f);
	    isAlreadySet = true;
//	 }
	    return bandeAffiche;
	}

}
