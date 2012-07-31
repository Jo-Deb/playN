package playn.showcase.core.peas.metier;


import playn.core.PlayN;
import playn.core.ResourceCallback;

public class GetData {
  
  public static String jSonObject;
  
  public static void getjSonData() {
	  
	  PlayN.assets().getText("peas/levels/energy.json", new ResourceCallback<String>() {
		  @Override
		  public void done(String resource) {
		    jSonObject = new String(resource);
		  }
		  @Override
		  public void error(Throwable err) {
		    err.printStackTrace();
		  }
		});
//	    return jSonObject;
	  
  }
  
}
