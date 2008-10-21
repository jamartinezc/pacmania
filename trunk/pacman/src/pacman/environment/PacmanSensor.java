package pacman.environment;

import java.util.*;

import java.awt.*;
import java.awt.image.*;

import pacman.object.*;
import pacman.util.*;
import unalcol.agents.*;

/**
 * <p>Title: PacmanSensor</p>
 *
 * <p>Description: Pacman Sensor</p>
 *
 * <p>Copyright: Copyright (c) 2008</p>
 *
 * <p>Institution: Universidad Nacional de Colombia</p>
 *
 * @author Professor Jonatan Gomez-Perdomo, Ph. D. (built using code provided by Simon Lucas and Leandro Liu)
 * @version 1.0
 */
public class PacmanSensor {
  protected long previous_read_time = -1000;
  protected Percept previous = null;

  protected Robot robot;

  public static IntStack stack = new IntStack(4 * PacmanConstants.width * PacmanConstants.height);
  protected int[] pix;
  public PacmanSensor()  throws Exception {
    robot = new Robot();
    pix = new int[PacmanConstants.width * PacmanConstants.height];
  }

  public Percept get() {

    synchronized( stack ){

      long time = System.currentTimeMillis();
      if (time - previous_read_time >= PacmanConstants.sensing_time_interval) {

        Vector<Pill> pills = new Vector<Pill> ();
        Vector<Ghost> ghosts = new Vector<Ghost> ();
        Ghost gBlinky = null;
        Ghost gPinky = null;
        Ghost gInky = null;
        Ghost gSue = null;
        Vector<PowerPill> powerPills = new Vector<PowerPill> ();
        MsPacman msPacman = null;

        // Get Pixels
        BufferedImage im = robot.createScreenCapture(new Rectangle(PacmanConstants.
            left, PacmanConstants.top, PacmanConstants.width,
                                                     PacmanConstants.height));
        im.getRGB(0, 0, PacmanConstants.width, PacmanConstants.height, pix, 0,
                  PacmanConstants.width);

        int board;
        switch (pix[PacmanConstants.mapchk]) {
          case PacmanConstants.first_board_type:
            board = 1;
            break;
          case PacmanConstants.second_board_type:
            board = 2;
            break;
          case PacmanConstants.third_board_type:
            board = 3;
            break;
          case PacmanConstants.fourth_board_type:
            board = 4;
            break;
          default:

            //Not Playing
            board = 0;
            //                            System.out.println("mapchk "+pix[mapchk]);
            //System.out.println("este no es un mapa conocido");
        }
        int color;
        Ghost ghost;
        for (int p = 0; p < pix.length; p++) {
          if ( (pix[p] & 0xFFFFFF) != PacmanConstants.BG &&
              PacmanConstants.colors.contains(pix[p])) {
            ConnectedSet cs = consume(pix, p, pix[p]);
            if (cs.isPacMan()) {
              msPacman = new MsPacman(cs.x(), cs.y());
            }
            else {
              if (cs.pill()) {
                pills.add(new Pill(cs.x(), cs.y()));
              }
              else {
                if (cs.powerPill()) {
                  powerPills.add(new PowerPill(cs.x(), cs.y()));
                }
                else {
                  if (cs.ghostLike()) {

                    color = cs.getColor();
                    ghost = new Ghost(cs.x(), cs.y(), color);
                    switch (color) {
                      case PacmanConstants.blinky:
                        gBlinky = ghost;
                        break;
                      case PacmanConstants.pinky:
                        gPinky = ghost;
                        break;
                      case PacmanConstants.inky:
                        gInky = ghost;
                        break;
                      case PacmanConstants.sue:
                        gSue = ghost;
                        break;
                    }
                    ghosts.add(ghost);
                  }
                  else {
                    if (cs.edibleGhost()) {
                      color = cs.getColor();
                      ghost = new Ghost(cs.x(), cs.y(), color);
                      ghosts.add(ghost);
                    }
                    else {
                      // Check fruit

                    }
                  }
                }
              }
            }
          }
        }
        Percept p = new Percept();
        if (gBlinky != null)
          p.setAttribute("blinky", gBlinky);
        if (gPinky != null)
          p.setAttribute("pinky", gPinky);
        if (gInky != null)
          p.setAttribute("inky", gInky);
        if (gSue != null)
          p.setAttribute("sue", gSue);
        p.setAttribute("ghosts", ghosts);
        p.setAttribute("pills", pills);
        p.setAttribute("powerPills", powerPills);
        if (msPacman != null)
          p.setAttribute("pacman", msPacman);
        p.setAttribute("board", board); // pix[PacmanConstants.mapchk]);
        previous = p;
        previous_read_time = time;
        return p;
      }else{
        return previous;
      }
    }
  }

  public ConnectedSet consume(int[] pix, int p, int fg) {
    ConnectedSet cs = new ConnectedSet(p % PacmanConstants.width,
                                       p / PacmanConstants.width, fg);
// push the current pixel on the stack
    stack.reset();
// int p = x + y * w;
    stack.push(p);
// int count = 0;
// System.out.println( stack );
    while (!stack.isEmpty()) {
// count++;
      p = stack.pop();
      if (pix[p] == fg) {
// System.out.println(cx + " : " + cy + " : " + pix[p] );
// System.in.read();
        cs.add(p % PacmanConstants.width, p / PacmanConstants.width, p, pix[p]);
        pix[p] = 0;
        int cx = p % PacmanConstants.width;
        int cy = p / PacmanConstants.width;
        if (cx > 0) {
          stack.push(p - 1);
        }
        if (cy > 0) {
          stack.push(p - PacmanConstants.width);
        }
        if (cx < (PacmanConstants.width - 1)) {
          stack.push(p + 1);
        }
        if (cy < (PacmanConstants.height - 1)) {
          stack.push(p + PacmanConstants.width);
        }
      }
    }
    return cs;
  }

}
