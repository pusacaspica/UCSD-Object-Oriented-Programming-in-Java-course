package module3;

//Java utilities libraries
import java.util.ArrayList;
//import java.util.Collections;
//import java.util.Comparator;
import java.util.List;

//Processing library
import processing.core.PApplet;

//Unfolding libraries
import de.fhpotsdam.unfolding.UnfoldingMap;
import de.fhpotsdam.unfolding.marker.Marker;
import de.fhpotsdam.unfolding.data.PointFeature;
import de.fhpotsdam.unfolding.marker.SimplePointMarker;
import de.fhpotsdam.unfolding.providers.Google;
import de.fhpotsdam.unfolding.providers.MBTilesMapProvider;
import de.fhpotsdam.unfolding.utils.MapUtils;

//Map library
import de.fhpotsdam.unfolding.providers.EsriProvider;

//Parsing library
import parsing.ParseFeed;

/** EarthquakeCityMap
 * An application with an interactive map displaying earthquake data.
 * Author: UC San Diego Intermediate Software Development MOOC team
 * @author Your name here
 * Date: July 17, 2015
 * */
public class EarthquakeCityMap extends PApplet {

	// You can ignore this.  It's to keep eclipse from generating a warning.
	private static final long serialVersionUID = 1L;

	// IF YOU ARE WORKING OFFLINE, change the value of this variable to true
	private static final boolean offline = false;
	
	// Less than this threshold is a light earthquake
	public static final float THRESHOLD_MODERATE = 5;
	// Less than this threshold is a minor earthquake
	public static final float THRESHOLD_LIGHT = 4;

	/** This is where to find the local tiles, for working without an Internet connection */
	public static String mbTilesString = "blankLight-1-3.mbtiles";
	
	// The map
	private UnfoldingMap map;
	
	//feed with magnitude 2.5+ Earthquakes
	private String earthquakesURL = "https://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/2.5_week.atom";

	
	public void setup() {


		size(950, 600, OPENGL);

		if (offline) {
		    map = new UnfoldingMap(this, 200, 50, 740, 500, new MBTilesMapProvider(mbTilesString));
		    earthquakesURL = "2.5_week.atom"; 	// Same feed, saved Aug 7, 2015, for working offline
		}
		else {
			map = new UnfoldingMap(this, 200, 50, 740, 500, new EsriProvider.WorldStreetMap());
			// IF YOU WANT TO TEST WITH A LOCAL FILE, uncomment the next line
			//earthquakesURL = "2.5_week.atom";
		}

	    map.zoomToLevel(2);
	    MapUtils.createDefaultEventDispatcher(this, map);	
			
	    // The List you will populate with new SimplePointMarkers
	    List<Marker> markers = new ArrayList<Marker>();

	    //Use provided parser to collect properties for each earthquake
	    //PointFeatures have a getLocation method
	    List<PointFeature> earthquakes = ParseFeed.parseEarthquake(this, earthquakesURL);
	    for( PointFeature earthquake: earthquakes ){
			markers.add(createMarker(earthquake));
		}

	    //TODO (Step 3): Add a loop here that calls createMarker (see below) 
	    // to create a new SimplePointMarker for each PointFeature in 
	    // earthquakes.  Then add each new SimplePointMarker to the 
	    // List markers (so that it will be added to the map in the line below). Done.
	    
	    
	    // Add the markers to the map so that they are displayed
	    map.addMarkers(markers);
	}
		
	/* createMarker: A suggested helper method that takes in an earthquake 
	 * feature and returns a SimplePointMarker for that earthquake
	 * 
	 * In step 3 You can use this method as-is.  Call it from a loop in the 
	 * setp method.  
	 * 
	 * TODO (Step 4): Add code to this method so that it adds the proper. Done.
	 * styling to each marker based on the magnitude of the earthquake.  
	*/

	private SimplePointMarker createMarker(PointFeature feature)
	{  
		// To print all of the features in a PointFeature (so you can see what they are)
		// uncomment the line below.  Note this will only print if you call createMarker 
		// from setup
		//System.out.println(feature.getProperties());
		
		// Create a new SimplePointMarker at the location given by the PointFeature
		SimplePointMarker marker = new SimplePointMarker(feature.getLocation());
		
		Object magObj = feature.getProperty("magnitude");
		float mag = Float.parseFloat(magObj.toString());
		
		// Here is an example of how to use Processing's color method to generate
		int green = color(0, 255, 0);
		int greenStroke = color(0, 155, 0);
		int yellow = color(255, 255, 0);
		int yellowStroke = color(155, 155, 0);
		int red = color(255, 0, 0);
		int redStroke = color(155, 0, 0);

		if(mag <= THRESHOLD_LIGHT) {
			marker.setColor(green);
			marker.setStrokeColor(greenStroke);
			marker.setRadius(5f);
		}
	    if(mag > THRESHOLD_LIGHT){
	    	marker.setColor(yellow);
			marker.setStrokeColor(yellowStroke);
			marker.setRadius(9f);
		}
	    if(mag > THRESHOLD_MODERATE){
	    	marker.setColor(red);
			marker.setStrokeColor(redStroke);
			marker.setRadius(9f);
		}
		// TODO (Step 4): Add code below to style the marker's size and color. Done.
	    // according to the magnitude of the earthquake.  
	    // Don't forget about the constants THRESHOLD_MODERATE and 
	    // THRESHOLD_LIGHT, which are declared above.
	    // Rather than comparing the magnitude to a number directly, compare 
	    // the magnitude to these variables (and change their value in the code 
	    // above if you want to change what you mean by "moderate" and "light")
	    
	    
	    // Finally return the marker
	    return marker;
	}
	
	public void draw() {
	    background(10);
	    map.draw();
	    addKey();
	}


	// helper method to draw key in GUI
	// TODO: Implement this method to draw the key. Done.
	private void addKey() 
	{
		int green = color(0, 255, 0);
		int yellow = color(255, 255, 0);
		int red = color(255, 0, 0);

		fill(120);
		rect(10, 50, 180, 500, 5, 25, 25, 25);

		fill(240);
		text("Light earthquake", 38, 75);
		text("Moderate earthquake", 38, 95);
		text("Severe earthquake", 38, 115);
		text("Light earthquakes are below "+THRESHOLD_LIGHT+" on the Richter scale", 30, 135, 160, 500);
		text("Moderate earthquakes are below "+THRESHOLD_MODERATE+" on the Richter scale", 30, 185, 160, 500);
		text("Severe earthquakes are below "+THRESHOLD+" on the Richter scale", 30, 235, 160, 500);

		fill(green);
		ellipse(30, 70, 5, 5);

		fill(yellow);
		ellipse(30, 90, 10, 10);

		fill(red);
		ellipse(30, 110, 10, 10);


		// Remember you can use Processing's graphics methods here
	
	}
}
