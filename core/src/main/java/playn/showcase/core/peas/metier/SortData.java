package playn.showcase.core.peas.metier;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.TreeMap;
import playn.core.Json;
import playn.core.PlayN;

/**
 * @author sfeir
 *
 */
public class SortData {

  public static Json.Array nucleaire = null;
  public static Json.Array hydroelectrique = null;
  public static Json.Array thermique = null;
  public static int[] years = {2004,2005,2006,2007,2008,2009};
  public static String[] areaName;
  public static HashMap<String, Double[]> unsortedAreaValue;
  private static Comparator<Double> comparator = new Comparator<Double>() {

	@Override
	public int compare(Double o1, Double o2) {
		if (o1 > o2) return -1;
		if (o1 == o2) return 0;
		if (o1 < o2) return 1;
		return 0;
	}
	  
  };
  private static TreeMap<Double, String> triByYear = new TreeMap<Double, String>(comparator);

  private static Double[] tri(Double[] radiusVal ){
    for (int i = 0; i < radiusVal.length; i++) {
	  for (int j = 0; j < radiusVal.length -i; j++) {
	    if(j+1 < radiusVal.length)
		  if(radiusVal[j] < radiusVal[j+1]){
		    double min = radiusVal[j];
			radiusVal[j] = radiusVal[j+1];
			radiusVal[j+1] = min;
		  }
		}
	  }
    return radiusVal;
  }
  
  
 
  
  public static void sortData(String resource, int year) {
	//S'ARRETER SUR LES 22 PREMIERES REGIONS
	Json.Object document = PlayN.json().parse(resource);
	nucleaire = document.getArray("nucleaire");
	thermique = document.getArray("thermique");
	hydroelectrique = document.getArray("hydroelectrique");
    
	if( nucleaire == null){
      System.err.println("Unable to load the first JsonArray nucleaire");
      return;
    }
    
    triByYear = areaSort(year);
    areaName = triByYear.values().toArray(new String[22]);
  }

  /**
   * @param data
   * @param numYear
   * @return an HashMap<String, Double[]> containing all values for each area
   * according the numYear parameter 
   */
  public static HashMap<String, Double[]> areaValue(TreeMap<Double, String> data, 
  		  int numYear) {
  	HashMap<String, Double[]> result = new HashMap<String, Double[]>();
  	unsortedAreaValue = new HashMap<String, Double[]>(22);
      for (Iterator<Double> iterator = data.keySet().iterator(); 
      		iterator.hasNext();) {
  	    Double keyArea = (Double) iterator.next();
  	    String keyMap = data.get(keyArea);
  	    int indexArea = 0;

  	   for (int i = 0; i < 22; i++) {
  	     Json.Object currentArea = nucleaire.getObject(i);
  	     if(currentArea.getString("region").equals(keyMap)){indexArea = i;}
  	   }
  	  
  	    Json.Array nu = ((Json.Object)(nucleaire.getObject(indexArea))).getArray("values");
  	    Json.Array th = ((Json.Object)(thermique.getObject(indexArea))).getArray("values");
  	    Json.Array hy = ((Json.Object)(hydroelectrique.getObject(indexArea))).getArray("values");
  	  
  	    String val = ((Json.Object)(nu.getObject(numYear))).getString("value");
  	    Double valueNu = Double.parseDouble(val);
  	    val = ((Json.Object)(th.getObject(numYear))).getString("value");
  	    Double valueTh = Double.parseDouble(val);
  	    val = ((Json.Object)(hy.getObject(numYear))).getString("value");
  	    Double valueHy = Double.parseDouble(val);
  	    Double[] temp = {valueNu, valueTh, valueHy};
  	    Double[] temp2 = {valueNu, valueTh, valueHy};

  	    unsortedAreaValue.put(keyMap, temp2);
  	    temp = tri(temp);
  	    result.put(keyMap, temp);
  	  }
      
      return result;
    } 
  
  /**
 * With the numYear paramater indicating an int corresponding to
 * a year in the years table returns a TreeMap containing the 
 * areaName as value and total amount of energy as key.
 * The tree is sort in decreasing order. 
 * @param numYear
 * @return
 */
  public static TreeMap<Double, String> areaSort(int numYear) {
    TreeMap< Double, String> res = new TreeMap<Double, String>(comparator);
    
    for (int i = 0; i < 22; i++) {
	  double temp = 0;
	  Json.Object tempDocNu = nucleaire.getObject(i);
	  Json.Array valuesNu = tempDocNu.getArray("values");
	  String valueNu = valuesNu.getObject(numYear).getString("value");
	  String areaNameNu = tempDocNu.getString("region");
	  temp = temp + Double.parseDouble(valueNu);
	 
	  Json.Object tempDocTh = thermique.getObject(i);
	  Json.Array valuesTh = tempDocTh.getArray("values");
	  String valueTh = valuesTh.getObject(numYear).getString("value");//on avait mis 0 avant
	  String areaNameTh = tempDocTh.getString("region");
	  temp = temp + Double.parseDouble(valueTh);
	  
	  Json.Object tempDocHy = hydroelectrique.getObject(i);
	  Json.Array valuesHy = tempDocHy.getArray("values");
	  String valueHy = valuesHy.getObject(numYear).getString("value");
	  temp = temp + Double.parseDouble(valueHy);
	  String areaNameHy = tempDocHy.getString("region");
	  
	  String regionName = tempDocTh.getString("region");
	  if(areaNameNu == areaNameHy){
	    if(areaNameHy == areaNameTh) System.out.println("le non " +
	    		"de la région est: "+areaNameHy);
	  }
	  res.put(temp, regionName);
	}
    return res;
  }

  
  public static String[] getAreaName() {
	return areaName;
  }


public static TreeMap<Double, String> getTriByYear() {
	return triByYear;
}
   
}
