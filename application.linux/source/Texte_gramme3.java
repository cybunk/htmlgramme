import processing.core.*; 
import processing.xml.*; 

import peasy.org.apache.commons.math.*; 
import peasy.*; 
import peasy.org.apache.commons.math.geometry.*; 
import processing.opengl.*; 
import guicomponents.*; 

import java.applet.*; 
import java.awt.*; 
import java.awt.image.*; 
import java.awt.event.*; 
import java.io.*; 
import java.net.*; 
import java.text.*; 
import java.util.*; 
import java.util.zip.*; 
import java.util.regex.*; 

public class Texte_gramme3 extends PApplet {

 
 
 
 



PeasyCam cam; 
PFont font;
PImage[] frames = new PImage[17];

int AlphaGeneral=80;
int DistanceGeneral=10;

//-
GTextField txf1;
GButton btn1;

GPanel pnl;
GHorzSlider sx,sy, sz;
int ax, ay, az;

// These are needed to remember PeasyCam offset and distance
float[] offsets = new float[3];
float[] rotations = new float[3];
double distance = 0.0f;

// Remember last slider values
// PeasyCam does not provide methods to set the absolute
// rotation angles, rotateX(ang) rotates about the X axis
// by ang radians
int lastSx, lastSy, lastSz;
int currSx, currSy, currSz;

// Used to remember PGraphics3D transformation matrix
PGraphics3D g3;

//
PImage a;

public void setup(){
  font = loadFont("ArialMT-20.vlw"); 
  textFont(font, 20);
  size(screen.width, screen.height, OPENGL );
  frameRate(30);
  smooth();
  a  = loadImage("images2/cover.jpg");
  cam = new PeasyCam(this,1000); 
  //cam.pan(0,100000000); 
  
  // test image 
   for (int i = 0; i <9; i++) { 
    frames[i] = loadImage("images2/image"+i+".jpg");
   }
    for (int i = 1; i <9; i++) { 
    frames[i+8] = loadImage("images3/image"+i+".jpg");
    println(i+8);
   }
   
   GComponent.globalColor = GCScheme.getColor(this,  GCScheme.RED_SCHEME);
   GComponent.globalFont = GFont.getFont(this, "Georgia", 12);
   pnl = new GPanel(this, "Control alpha and distance", screen.width/100*5,screen.height/100*70,300,72);
   sx = new GHorzSlider(this, 10, 8, 280, 16);
   sx.setLimits(AlphaGeneral,0,250);
   sx.setInertia(20);
   
   sy = new GHorzSlider(this, 10, 28, 280, 16);
   sy.setLimits(DistanceGeneral,1,300);
   sy.setInertia(20);
     
   pnl.add(sx);
   pnl.add(sy);
   g3 = (PGraphics3D)g;
}


public void draw(){
  
    if(pnl.isCollapsed()){
    // Panel is collapsed
    cam.setMouseControlled(!pnl.isDragging());
  }
  else {
    // panel open must be using sliders
    cam.setMouseControlled(false);  
  }
  
  background( 0 ); 
  translate((frames[0].width/4)/2*-1,(frames[0].height/4)/2*-1,0);
  rotateY(TWO_PI/6);
  htmlGramme2(frames);
  Gui2D();
}
public void htmlGramme2(PImage[] frame){
  noStroke();
  for (int i = frame.length-1; i >0; i--) { 
    //print(i);
    PImage img = frame[i];
    tint(250,250,250,AlphaGeneral);
    beginShape();
    texture(img);
    vertex(0, 0,0, 0, 0);
    vertex(img.width/5, 0 ,0, img.width, 0);
    vertex(img.width/5, img.height/5,0, img.width, img.height);
    vertex(0,  img.height/5,0, 0, img.height);
    endShape();
    translate(0,0,DistanceGeneral);
  }

  if (frameCount<1800){
    pushMatrix();
    translate(0,0,DistanceGeneral);
    tint(250,250,250,AlphaGeneral);
    beginShape();
    texture(a);
    vertex(0, 0,0, 0, 0);
    vertex(a.width/5, 0 ,0, a.width, 0);
    vertex(a.width/5, a.height/5,0, a.width, a.height);
    vertex(0,  a.height/5,0, 0, a.height);
    endShape();
    popMatrix();
  }
  
}
public void Gui2D(){
  // Get the current PeasyCam details to restore later
  //  offsets = cam.getLookAt();
  rotations = cam.getRotations();
  //  distance = cam.getDistance();

  // Get a handle on the current PGraphics?D transformation matrix
  PMatrix3D currCameraMatrix = new PMatrix3D(g3.camera);

  // This statement resets the camera in PGraphics3D, this effectively 
  // sets the display to behave as 2D with the origin 0,0 at the top-left
  // of the display
  camera();

  // Draw the HUD using PApplet graphics commands and GUI for Processing
  // 2D gui components
  G4P.draw();

  // Reset the PGraphics3D transformation matrix
  g3.camera = currCameraMatrix;
}

// Handle panels events i.e. when a panel is opened or
// collapsed
public void handlePanelEvents(GPanel panel){
  // Intended to detect panel events but ended up not
  // needing it. Left the following code as an example
  if(pnl.isCollapsed())
    println("Panel has collapsed");
  else
    println("Panel has opened");

}

// Handles slider events for both horizontal and
// vertical sliders
public void handleSliderEvents(GSlider slider){
  if(slider == sx){
    AlphaGeneral = slider.getValue();
  }
  if(slider == sy){
    DistanceGeneral = slider.getValue(); 
  }
}

  static public void main(String args[]) {
    PApplet.main(new String[] { "--present", "--bgcolor=#666666", "--stop-color=#cccccc", "Texte_gramme3" });
  }
}
