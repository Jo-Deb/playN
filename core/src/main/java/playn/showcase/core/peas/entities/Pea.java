/**
 * Copyright 2011 The PlayN Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package playn.showcase.core.peas.entities;


import java.util.HashMap;

import org.jbox2d.collision.shapes.CircleShape;
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
import playn.showcase.core.peas.metier.SortData;

public class Pea extends DynamicPhysicsEntity {
  //private static Image image = loadImage("pea.png");
//  private static Image chrome = loadImage("chrome.png");
  private static HashMap<String, Double[]> areaValue;
  private static HashMap<String, Double[]> unsortedAreaValue;
  private static String[] areaName;
  private static float[] areaRadius = new float[22];
  public static int yearNumber = 0;
  public static boolean alreadySet = false;
  private float rayon = 0;
  private Image cirleImage = null;
  private boolean imageSet = false;
  private Double[] sortedvalue;
  private Double[] unsortedValue;
  
  private float computeRadius( float nrj) {
    float temp = (float) Math.sqrt((nrj * Math.pow(28,2))/areaValue.get(areaName[0])[0]);
    return temp;
  }
  
  private float nrjRadius( Double[] nrj) {
	float sum = 0;
	for (int i = 0; i < nrj.length; i++) {
	  sum = sum + (float)(Math.abs(nrj[i]));
	}
    float temp = (float) Math.sqrt((sum * Math.pow(27,2))/areaValue.get(areaName[0])[0]);
	return temp;
}
  
  public static String TYPE = "Pea";
  public static int count = 0;
  public String nomDeRegion = null;
  
  public Pea(PeaWorld peaWorld, World world, float x, float y, float angle) {
    super(peaWorld, world, x, y, angle);
    nomDeRegion = areaName[count];
    rayon = areaRadius[count];
    count = count + 1;
  }

  
  public enum Energy{
	nucleaire, thermique, hydroelectrique  
  }
  
  @Override
  Body initPhysicsBody(World world, float x, float y, float angle) {
    FixtureDef fixtureDef = new FixtureDef();
    BodyDef bodyDef = new BodyDef();
    bodyDef.type = BodyType.DYNAMIC;
    bodyDef.position = new Vec2(0, 0);
    Body body = world.createBody(bodyDef);

    CircleShape circleShape = new CircleShape();
    circleShape.m_radius = getRadius();
    fixtureDef.shape = circleShape;
    fixtureDef.density = 0.4f;
    fixtureDef.friction = 0.1f;
    fixtureDef.restitution = 0.35f;
    circleShape.m_p.set(0, 0);
    body.createFixture(fixtureDef);
    body.setLinearDamping(0.2f);
    body.setTransform(new Vec2(x, y), angle);
    return body;
  }

  @Override
  float getWidth() {
    return 2 * getRadius();
  }

  @Override
  float getHeight() {
    return 2 * getRadius();
  }

  float getRadius() {
	  return rayon * PeasDemo.physUnitPerScreenUnit;
  }

  public Image createImage() {
	
	if (!alreadySet) {
	  areaValue = SortData.areaValue(SortData.getTriByYear(), yearNumber);
	  unsortedAreaValue = SortData.unsortedAreaValue;
	  areaName = SortData.areaName;
	  alreadySet = true;
	}
	
	sortedvalue = areaValue.get(areaName[count]);
	unsortedValue = unsortedAreaValue.get(areaName[count]);
	float rad1 = computeRadius((float) Math.abs(sortedvalue[0]));
	float sum = 0;
	for (int i = 0; i < sortedvalue.length; i++) {
		Double double1 = sortedvalue[i];
		sum = (float)(sum + double1);
	}
	areaRadius[count] = 2* nrjRadius(sortedvalue);
	this.rayon = areaRadius[count];
	
	CanvasImage image = playn.core.PlayN.graphics().createImage(
    		Math.round(2*rayon+2),Math.round(2*rayon+2));
	Canvas canvas = image.canvas();
//	canvas.setFillColor(0x00000000);
	canvas.fillCircle(image.width()/2f, image.height()/2f, rayon);
	canvas.setFillColor(myColor(sortedvalue[0], unsortedValue));
	canvas.fillCircle(image.width()/2f, image.height()/2f - rad1, rad1);
	canvas.setFillColor(myColor(sortedvalue[1], unsortedValue));
	
	float rad2 = computeRadius(Math.round(sortedvalue[1]));
	canvas.fillCircle(image.height()/2f + rad2, image.height()/2 + rad2, rad2);
	canvas.setFillColor(myColor(sortedvalue[2], unsortedValue));
	
	float rad3 = computeRadius(Math.round(sortedvalue[2]));
    canvas.fillCircle(image.height()/2f - rad2, image.height()/2f + rad2, rad3);
    
    return image;
  }
  
  public Image getImage(){
	if(!imageSet) {
		this.cirleImage = createImage();
		imageSet = true;
	}
    return this.cirleImage;  
  }  
  //private static Image chrome = resolveImage("chrome.png");
  
  public boolean clickOnMe(float x, float y) {
   if (x <= this.getBody().getPosition().x + this.getRadius() &&
	   x >= this.getBody().getPosition().x - getRadius()) {
     if (y <= this.getBody().getPosition().y + getRadius() &&
    	 y >= this.getBody().getPosition().y - getRadius()) {
    	 return true;
     }
   }
   return false;
  }
  
  public void showInAffiche() {
	DownImage.canvasAffiche.clear();
	DownImage.canvasAffiche.setFillColor(0xFF000000);
	DownImage.canvasAffiche.fillRect(0, 0, DownImage.img.width(), DownImage.img.height());
    
	DownImage.canvasAffiche.setStrokeColor(0xFF646400);
    DownImage.canvasAffiche.setFillColor(0xFFFFFFFF);
    DownImage.canvasAffiche.drawText(nomDeRegion, 12, 12);
    DownImage.canvasAffiche.drawText(Integer.toString(SortData.years[yearNumber]), 12, 28);
    
    DownImage.canvasAffiche.setFillColor(0xFF00FF00);
    DownImage.canvasAffiche.fillRect(DownImage.img.width()/2f, 4, 12, 12);
    DownImage.canvasAffiche.setFillColor(0xFFFFFFFF);
    DownImage.canvasAffiche.drawText("NUCLEAIRE : "+unsortedValue[0]+"GWh", DownImage.img.width()/2f + 30, 12);
    
    DownImage.canvasAffiche.setFillColor(0xFFFF0000);
    DownImage.canvasAffiche.fillRect(DownImage.img.width()/2f, 20, 12, 12);
    DownImage.canvasAffiche.setFillColor(0xFFFFFFFF);
    DownImage.canvasAffiche.drawText("THERMIQUE : "+unsortedValue[1]+"GWh", DownImage.img.width()/2f + 30, 28);
    
    DownImage.canvasAffiche.setFillColor(0x8585FFFF);
    DownImage.canvasAffiche.fillRect(DownImage.img.width()/2f, 36, 12, 12);
    DownImage.canvasAffiche.setFillColor(0xFFFFFFFF);
    DownImage.canvasAffiche.drawText("HYDROELECTRIQUE : "+unsortedValue[2]+"GWh", DownImage.img.width()/2f + 30, 44);
  }
  
  public int myColor(Double sorted, Double[] unsorted) {
    if (unsorted[0] == sorted) {return 0xFF00CC00;} //nucleaire
	if (unsorted[1] == sorted) {return 0xFFFF0000;} //thermique
	if (unsorted[2] == sorted) {return 0x8585FFFF;} //hydroelectrique
    return 0;
  }
}
