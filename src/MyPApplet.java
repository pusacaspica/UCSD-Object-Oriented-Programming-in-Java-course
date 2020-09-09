import processing.core.*;

import java.time.*;
import java.time.format.DateTimeFormatter;

public class MyPApplet extends PApplet {

    private String URL = "https://pbs.twimg.com/media/Eez24j6WAAA69f8?format=jpg&name=small";
    private PImage bgimage;
    private int width, height;

    public void setup(){
        width = 500; height = 500;
        size(width, height);
        bgimage = loadImage(URL, "jpg");
    }

    public void draw(){

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("ss");
        LocalDateTime now = LocalDateTime.now();
        bgimage.resize( (int)(getSize().width*0.9), (int)(getSize().height*0.9) );
        image(bgimage, 0, 0);
        ellipse((float)(getSize().width*0.6), (float)(getSize().height*0.5), (float)(getSize().width*0.1), (float)(getSize().height*0.1));
        fill(4*parseFloat(dtf.format(now)), 255, 4*parseFloat(dtf.format(now)));
        ellipse((float)(getSize().width*0.36), (float)(getSize().height*0.57), (float)(getSize().width*0.1), (float)(getSize().height*0.1));
        System.out.println(dtf.format(now));
    }

}
