package pacman.util;

import java.awt.*;

import pacman.gui.*;
/**
 * User: Simon
 * Date: 14-Mar-2007
 * Time: 22:19:47
 * Modified by: Leandro Liu (May 2008) and Professor Jonatan Gomez Perdomo, Ph. D. (September 2008)
 */
public class ConnectedSet implements Drawable {
    // private ArrayList pixels;
    int x, y;
    int width, height;
    int fg; // the value of the FG pixels
    int xMin, xMax, yMin, yMax;
    int pTot;
    int tot;
    Color c;
    int name;

    public int px, py;
    boolean valid = false;

    public void draw(Graphics g) {
        // int div = 1;
        validate();
        g.setColor(c);
        if (ghostLike()||edibleGhost()||isFruitAble()) {
            g.fillRect(xMin, yMin, width, height);
            // System.out.println(width + " : " + height);
        } else {
            if (powerPill() || pill() && true) {
                g.drawRect(xMin, yMin, width+1, height+1);
            }
        }
        // g.fillRect(x, y, 1, 1);
    }

    public void validate() {
        if (!valid) {
            width = xMax - xMin;
            height = yMax - yMin;
            // px = xMin + (xMax - xMin) / div;
            // py = yMin + (yMax - yMin) / div;
            valid = true;
        }
    }

    public boolean ghostLike() {
        validate();
        // OLD ONE (SHOWS MIDDLE CUBE
        //return ghostColor(fg) && width >= 10 && height >= 10
        //NEW ONE
        return ghostColor(fg) && width >= 10 && height >= 10 && width < 20 && height < 20;
        // return width == 13 && height == 13; // fg == PacmanConstants.inky;
    }

    public boolean edible() {
        validate();
        return PacmanConstants.powerPill == fg && width >= 10 && height >= 10;
        // return width == 13 && height == 13; // fg == PacmanConstants.inky;
    }

    public boolean ghostColor(int c) {
        //if( c==PacmanConstants.flee){ System.out.println("Capture"); }
        if(c == PacmanConstants.blinky){
            this.name=1;
        }else if(c == PacmanConstants.pinky){
            this.name=2;
        }else if(c == PacmanConstants.inky){
            this.name=3;
        }else if(c == PacmanConstants.sue){
            this.name=4;
        }
        return c == PacmanConstants.blinky ||
                c == PacmanConstants.pinky ||
                c == PacmanConstants.inky ||
                c == PacmanConstants.sue;//||
                //c == PacmanConstants.flee;
    }

    public boolean isPacMan() {
        validate();
        return fg == PacmanConstants.pacMan && width >= 8 && height >= 8&&this.y()<278&&this.x()>12&&this.x()<217;
        // return width == 13 && height == 13; // fg == PacmanConstants.inky;
    }

    public boolean pill() { //ANY edible.
        validate();
        return ((width == 1 && height == 1) && fg != PacmanConstants.edibleGhost); // between(width, 2, 3) && between(height, 2, 3);
        //return fg==-1288;
    }

    public boolean edibleGhost(){
        return ((width >= 10 && height >= 10)&&(width<20&&height<20) && (fg == PacmanConstants.edibleGhost||fg == PacmanConstants.pill));

    }

    public boolean isFruitAble(){
        validate();
        return  (width >= 5 && height >= 5)&&(width <11 && height <11);
        // return width == 13 && height == 13; // fg == PacmanConstants.inky;

    }

    public boolean powerPill() {
        validate();
        return width == 7 && height == 7; // between(width, 2, 3) && between(height, 2, 3);
    }

    public static boolean between(int x, int low, int high) {
        return x >= low && x <= high;
    }

    public int x() {
        return (xMin + xMax)/ 2;
    }

    public int y() {
        return (yMin + yMax)/ 2;
    }

    public void rescale(double fac) {
        // do nothing
    }

    public ConnectedSet(int x, int y, int fg) {
        // pixels = new ArrayList();
        this.x = x;
        this.y = y;
        xMin = x;
        xMax = x;
        yMin = y;
        yMax = y;
        this.fg = fg;
        c = new Color((fg & 0xFF0000) >> 16, (fg & 0xFF00) >> 8, (fg & 0xFF));
        // System.out.println("Color: " + c + " : " + fg);
    }

    public void add(int px, int py, int pos, int val) {
        xMin = Math.min(px, xMin);
        xMax = Math.max(px, xMax);
        yMin = Math.min(py, yMin);
        yMax = Math.max(py, yMax);
        pTot += (1 + px - x) * (1 + py - y);
        tot++;
        valid = false;
    }

    public int hashCode() {
        return pTot;
    }

    public boolean equals(Object obj) {
        ConnectedSet cs = (ConnectedSet) obj;
        return cs.pTot == pTot;
    }

    public String toString() {
        return x + " : " + y + " : " + pTot;
    }

    /*public int dist(Position p) {
        return (int) Math.sqrt(sqr(x - p.x) + sqr(y - p.y));
    }*/
    public int dist(Position p) {
        return (int) Math.sqrt(sqr(x - p.x)) + (int)Math.sqrt(sqr(y - p.y));
    }


    public static int sqr(int x) {
        return x * x;
    }

    //ADDED BY Professor Jonatan GOMEZ
    public int getColor(){ return fg; }


}
