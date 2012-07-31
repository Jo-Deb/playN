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
package playn.showcase.core.peas;

import static playn.core.PlayN.assets;
import static playn.core.PlayN.graphics;
import static playn.core.PlayN.pointer;
import static playn.core.PlayN.keyboard;
import playn.core.GroupLayer;
import playn.core.Image;
import playn.core.ImageLayer;
import playn.core.Key;
import playn.core.Keyboard;
import playn.core.PlayN;
import playn.core.Pointer;
import playn.core.ResourceCallback;
import playn.showcase.core.Demo;
import playn.showcase.core.peas.entities.DownImage;
import playn.showcase.core.peas.entities.Entity;
import playn.showcase.core.peas.entities.Pea;
import playn.showcase.core.peas.entities.Sol;
import playn.showcase.core.peas.metier.SortData;

public class PeasDemo extends Demo {

  
  // scale difference between screen space (pixels) and world space (physics).
  public static float physUnitPerScreenUnit = 1 / 26.666667f;

  ImageLayer bgLayer;

  // main layer that holds the world. note: this gets scaled to world space
  GroupLayer worldLayer;

  // main world
  PeaWorld world = null;
  boolean worldLoaded = false; 

  private void showAll(){
  	int absLim = playn.core.PlayN.graphics().width();
  	int ordLim = playn.core.PlayN.graphics().height();
  	
  	while(Pea.count != 22){
  		float abs = (float) Math.random() * absLim;
  		float ord = (float) Math.random() * ordLim;
  		Pea pea   = new Pea(world, world.world, physUnitPerScreenUnit * abs, physUnitPerScreenUnit * ord, 0);
  		world.add(pea);
  	}
  	
  } 
  
  @Override
  public String name() {
    return "Pea Physics";
  }

  @Override
  public void init() {
    // load and show our background image
    Image bgImage = assets().getImage("peas/images/bg.png");
    bgLayer = graphics().createImageLayer(bgImage);
    graphics().rootLayer().add(bgLayer);

    // create our world layer (scaled to "world space")
    worldLayer = graphics().createGroupLayer();
    worldLayer.setScale(1f / physUnitPerScreenUnit);
    graphics().rootLayer().add(worldLayer);
 
    PeaLoader.CreateWorld("peas/levels/level1.json", worldLayer, new ResourceCallback<PeaWorld>() {
      @Override
      public void done(PeaWorld resource) {
        world = resource;
        worldLoaded = true;
      }

      @Override
      public void error(Throwable err) {
        PlayN.log().error("error loading pea world: " + err.getMessage());
        err.printStackTrace();
      }
    });

    // hook up our pointer listener
    pointer().setListener(new Pointer.Adapter() {
      @Override
      public void onPointerStart(Pointer.Event event) {
        if (worldLoaded &&
        	((event.y() * physUnitPerScreenUnit) < (18 
        		- DownImage.img.height() * physUnitPerScreenUnit/2))){
          if (Pea.count < 22){
            Pea pea = new Pea(world, world.world, physUnitPerScreenUnit * event.x(),
                            physUnitPerScreenUnit * event.y(), 0);
            world.add(pea);
          }
          //check if we're on a Pea object
          for (Entity entity : world.getEntities()) {
        	  if ((entity instanceof Pea)) {
        		  Pea temp = (Pea) entity;
        		  if (temp.clickOnMe(event.x()*physUnitPerScreenUnit, 
        				  event.y()*physUnitPerScreenUnit)) {
        			  temp.showInAffiche();
        		  }
        	  }
        	  if (entity instanceof Sol) {
        	    Sol sol = (Sol) entity;
        	    System.out.println("les coord x = "+sol.getBody().getPosition().x);
        	    System.out.println("les coord y = "+sol.getBody().getPosition().y);
        	  }
          }
        }
      }
      @Override
      public void onPointerDrag(Pointer.Event event){
        
      }
    });
    
    keyboard().setListener(new Keyboard.Adapter() {
      public void onKeyDown( Keyboard.Event event) {
        if (event.key().compareTo(Key.N)==0 || event.key().compareTo(Key.P)==0) {
          shutdown();
          Pea.count = 0;
          Pea.alreadySet = false;
          if (event.key().compareTo(Key.N)==0 && Pea.yearNumber < SortData.years.length) {
            Pea.yearNumber = Pea.yearNumber + 1;
            init(); return;
          }
          if (event.key().compareTo(Key.P)==0 && Pea.yearNumber > 0) {
          	Pea.yearNumber = Pea.yearNumber - 1;
            init(); return;
          }
        }	  
        if (event.key().compareTo(Key.A) == 0){showAll();}
      }
    });
  }

  @Override
  public void shutdown() {
    bgLayer.destroy();
    bgLayer = null;
    worldLayer.destroy();
    worldLayer = null;
    world = null;
    worldLoaded = false;
  }

  @Override
  public void paint(float alpha) {
    if (worldLoaded) {
      world.paint(alpha);
    }
  }

  @Override
  public void update(float delta) {
    if (worldLoaded) {
      world.update(delta);
    }
  }
}
