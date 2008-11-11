package pacman.util;

import java.awt.Color;
import java.util.HashSet;

/**
 * <p>Title: PacmanConstants</p>
 *
 * <p>Description: Set of constants used in Pacman (ghost's color, board position, etc)</p>
 *
 * <p>Copyright: Copyright (c) 2008</p>
 *
 * <p>Institution: Universidad Nacional de Colombia</p>
 *
 * @author Professor Jonatan Gomez-Perdomo, Ph. D. and Leandro Liu (built using code by Simon Lucas)
 * @version 1.0
 */
public class PacmanConstants {
  /**
   * Prints debugging messages
   */
  public static final boolean debug = false;
  /**
   * Background color
   */
  public static final int BG = 0;
  /**
   * Blinky's color
   */
  public static final int blinky = -65536;//(new Color(255,0,0)).getRGB(); // Using the Microsoft Ms Pacman : -65536;
  /**
   * Pinky's color
   */
  public static final int pinky = -18210;//(new Color(255,184,222)).getRGB(); // Using the Microsoft Ms Pacman : -18689;
  /**
   * Inky's color
   */
  public static final int inky = -16711714;//(new Color(0,255,222)).getRGB(); // Using the Microsoft Ms Pacman : -16711681;
  /**
   * Sue's color
   */
  public static final int sue = -18361;//(new Color(255,184,71)).getRGB(); // Using the Microsoft Ms Pacman : -18859;
  /**
   * MsPacman's color
   */
  public static final int pacMan = -256;//(new Color(255,255,0)).getRGB(); // Using the Microsoft Ms Pacman : -256;
  /**
   * Power-pill's color
   */
  public static final int powerPill = -2171170;//(new Color(222,222,222)).getRGB(); // Using the Microsoft Ms Pacman : -14408449;
  /**
   * Pill's color
   */
  public static final int pill = -2171170;//(new Color(222,222,222)).getRGB(); // Using the Microsoft Ms Pacman : -2434305;
  /**
   * Edible-ghost's color
   */
  public static final int edibleGhost = -14605858;//(new Color(33,33,222)).getRGB(); // Using the Microsoft Ms Pacman : -14408449;

  /**
   * Set of colours in MsPacman
   */
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

  /**
   * Left position of the MsPacman board
   */
  public static final int left = 220;
  /**
   * Top position of the MsPacman board
   */
  public static final int top = 222;
  /**
   * Width of the MsPacman board
   */
  public static final int width = 230;//224 MsPacman's World
  /**
   * Height of the MsPacman board
   */
  public static final int height = 285;;//248 MsPacman's World

  /**
   * Board's left margin
   */
  public static final int leftMargin = 2;
  /**
   * Board's top margin
   */
  public static final int topMargin = 25;
  /**
   * Cell's width (a cell is an abstract region that includes a single pill or power pill)
   */
  public static final int cellWidth = 8;
  /**
   * Cell's height (a cell is an abstract region that includes a single pill or power pill)
   */
  public static final int cellHeight = 8;

  /**
   * Bit used for determining the map (similar to level) being processed.
   */
  public static final int mapchk=(200*width)+113;

  public static final int first_board_type = -18281; //-18774:
  public static final int second_board_type = -12076834; //-12011777:
  public static final int third_board_type = -2584491; //-18774:
  public static final int fourth_board_type = -14212100; //-18774:

  /**
   * Milliseconds between each sensing (it is possible that graphics mode
   * tries to sense while the agent is sensing, producing a latency in the system)
   * Using 50 it is possible to sense 20 times per second
   */
  public static final long sensing_time_interval = 50;


}
