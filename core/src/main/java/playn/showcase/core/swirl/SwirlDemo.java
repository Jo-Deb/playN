/**
 * Copyright 2010 The PlayN Authors
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
package playn.showcase.core.swirl;

import static playn.core.PlayN.assets;
import static playn.core.PlayN.graphics;
import static playn.core.PlayN.pointer;
import playn.core.GroupLayer;
import playn.core.Image;
import playn.core.ImageLayer;
import playn.core.Pointer;
import playn.showcase.core.Demo;

public class SwirlDemo extends Demo {

  GroupLayer groupLayer;
  ImageLayer bgLayer;
  ImageLayer layer0, layer1, layer2, layer3;

  @Override
  public String name() {
    return "Swirl";
  }

  @Override
  public void init() {
    pointer().setListener(new Pointer.Adapter() {
      @Override
      public void onPointerStart(Pointer.Event event) {
        doStuff();
      }
    });

    Image background = assets().getImage("swirl/background.png");
    Image catgirl = assets().getImage("swirl/girlcat.png");

    groupLayer = graphics().createGroupLayer();
    groupLayer.setOrigin(128, 128);
    groupLayer.transform().translate(256, 256);
    graphics().rootLayer().add(groupLayer);

    bgLayer = graphics().createImageLayer(background);
    bgLayer.setDepth(-1);
    graphics().rootLayer().add(bgLayer);

    layer0 = graphics().createImageLayer(catgirl);
    layer1 = graphics().createImageLayer(catgirl);
    layer2 = graphics().createImageLayer(catgirl);
    layer3 = graphics().createImageLayer(catgirl);

    groupLayer.add(layer0);
    groupLayer.add(layer1);
    groupLayer.add(layer2);
    groupLayer.add(layer3);

    layer0.setOrigin(50, 100);
    layer1.setOrigin(50, 100);
    layer2.setOrigin(50, 100);
    layer3.setOrigin(50, 100);

    layer0.transform().translate(0, 0);
    layer1.transform().translate(256, 0);
    layer2.transform().translate(256, 256);
    layer3.transform().translate(0, 256);
  }

  @Override
  public void shutdown() {
    groupLayer.destroy();
    groupLayer = null;
    bgLayer.destroy();
    bgLayer = null;
    layer0 = layer1 = layer2 = layer3 = null;
  }

  private void doStuff() {
  }

  float angle = 0;
  float scale = 1;

  @Override
  public void update(float delta) {
    angle += delta * (float) Math.PI / 5000;
    scale = (float) Math.sin(angle) + 0.5f;

    layer0.transform().setRotation(angle);
    layer1.transform().setRotation(angle);
    layer2.transform().setRotation(angle);
    layer3.transform().setRotation(angle);

    layer0.transform().setUniformScale(scale);
    layer1.transform().setUniformScale(scale);
    layer2.transform().setUniformScale(scale);
    layer3.transform().setUniformScale(scale);

    groupLayer.transform().setRotation(angle);
    groupLayer.transform().setUniformScale(scale);
  }

  @Override
  public void paint(float alpha) {
  }
}
