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

import static playn.core.PlayN.assets;
import static playn.core.PlayN.graphics;

import java.io.ObjectInputStream.GetField;

import playn.core.Canvas;
import playn.core.PlayN;
import playn.core.Image;
import playn.core.ImageLayer;
import playn.core.ResourceCallback;

import playn.showcase.core.peas.PeaWorld;

public abstract class Entity {
  final ImageLayer layer;
  float x, y, angle;

  public Entity(final PeaWorld peaWorld, float px, float py, float pangle) {
    this.x = px;
    this.y = py;
    this.angle = pangle;
    layer = graphics().createImageLayer(getImage());
    initPreLoad(peaWorld);
    getImage().addCallback(new ResourceCallback<Image>() {
      @Override
      public void done(Image image) {
        // since the image is loaded, we can use its width and height
        layer.setWidth(image.width());
        layer.setHeight(image.height());
        layer.setOrigin(image.width() / 2f, image.height() / 2f);
        
        System.out.println("###############Entity print####################");
        System.out.println("image.width()="+image.width());
        System.out.println("image.height()="+image.height());
        System.out.println("getWidth() / image.width()="+getWidth() / image.width()+" et " +
        		"getHeight() / image.height()="+getHeight() / image.height());
        layer.setScale(getWidth() / image.width(), getHeight() / image.height());
        System.out.println("layer.width"+layer.width()+"layer.heigh"+layer.height());
        System.out.println("################################################");
        
        layer.setTranslation(x, y);
        layer.setRotation(angle);
        initPostLoad(peaWorld);
      }

      @Override
      public void error(Throwable err) {
        PlayN.log().error("Error loading image: " + err.getMessage());
      }
    });
  }

  /**
   * Perform pre-image load initialization (e.g., attaching to PeaWorld layers).
   *
   * @param peaWorld
   */
  public abstract void initPreLoad(final PeaWorld peaWorld);

  /**
   * Perform post-image load initialization (e.g., attaching to PeaWorld layers).
   *
   * @param peaWorld
   */
  public abstract void initPostLoad(final PeaWorld peaWorld);

  public void paint(float alpha) {
  }

  public void update(float delta) {
  }

  public void setPos(float x, float y) {
    layer.setTranslation(x, y);
  }

  public void setAngle(float a) {
    layer.setRotation(a);
  }

  abstract float getWidth();

  abstract float getHeight();

  public abstract Image getImage();

  protected static Image loadImage(String name) {
    return assets().getImage("peas/images/" + name);
  }
}
