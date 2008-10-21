/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pacman.util;

import java.awt.Color;
import java.util.HashSet;
import java.util.Iterator;

/**
 *
 * @author jaguar
 */
public class Colores {
  public static final int blinky = (new Color(255,0,0)).getRGB(); // Using the Microsoft Ms Pacman : -65536;
  /**
   * Pinky's color
   */
  public static final int pinky = (new Color(255,184,222)).getRGB(); // Using the Microsoft Ms Pacman : -18689;
  /**
   * Inky's color
   */
  public static final int inky = (new Color(0,255,222)).getRGB(); // Using the Microsoft Ms Pacman : -16711681;
  /**
   * Sue's color
   */
  public static final int sue = (new Color(255,184,71)).getRGB(); // Using the Microsoft Ms Pacman : -18859;
  /**
   * MsPacman's color
   */
  public static final int pacMan = (new Color(255,255,0)).getRGB(); // Using the Microsoft Ms Pacman : -256;
  /**
   * Power-pill's color
   */
  public static final int powerPill = (new Color(222,222,222)).getRGB(); // Using the Microsoft Ms Pacman : -14408449;
  /**
   * Pill's color
   */
  public static final int pill = (new Color(222,222,222)).getRGB(); // Using the Microsoft Ms Pacman : -2434305;
  /**
   * Edible-ghost's color
   */
  public static final int edibleGhost = (new Color(33,33,222)).getRGB(); // Using the Microsoft Ms Pacman : -14408449;
  
  public static HashSet<Integer> colors = new HashSet<Integer>();
  static {
      colors.add(blinky);
      colors.add(pinky);
      colors.add(inky);
      colors.add(sue);
      colors.add(pacMan);
      colors.add(powerPill);
      colors.add(pill);
      colors.add(edibleGhost);
  }
    public static void main(String[] args) {
        System.out.println((blinky)+"\n");
        System.out.println((pinky)+"\n");
        System.out.println((inky)+"\n");
        System.out.println((sue)+"\n");
        System.out.println((pacMan)+"\n");
        System.out.println((powerPill)+"\n");
        System.out.println((pill)+"\n");
        System.out.println((edibleGhost)+"\n");
        }
    
}
