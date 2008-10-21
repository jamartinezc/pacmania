package pacman.gui;
import java.util.*;

import java.awt.*;

import pacman.object.*;
import unalcol.agents.*;
import unalcol.agents.simulate.*;
import unalcol.agents.simulate.gui.*;

/**
 * <p>Title: PacmanDrawer</p>
 *
 * <p>Description: Draws a Pacman Environment</p>
 *
 * <p>Copyright: Copyright (c) 2008</p>
 *
 * <p>Institution: Universidad Nacional de Colombia</p>
 *
 * @author Professor Jonatan Gomez-Perdomo, Ph. D.
 * @version 1.0
 */
public class PacmanDrawer extends Drawer {
    protected int w, h;

    public static boolean drawGrid = true;

    public void paint(Graphics g) {
        g.setColor(Color.black);
        g.fillRect(0, 0, w, h);
        draw(g);
    }

    public void drawGrid(Graphics g){
      if( drawGrid ){
         //DRAWS THE GRID
         // @TODO Check constants using PacmanConstants

          int cuadX[]={2,w-4,w-4,2,2};
          int cuadY[]={25,25,h-12,h-12,25};
          g.setColor(Color.DARK_GRAY);
          g.drawPolyline(cuadX,cuadY,5);

          for(int i=0;i<28;i++){
              g.drawLine((int)2+((w-8)/27)*i,25,(int)2+((w-8)/27)*i,h-12);    //lineas verticales
          }
          for(int j=0;j<31;j++){
              g.drawLine(3,(int)25+((h-25)/30)*j,w-5,(int)25+((h-25)/30)*j);  //lineas Horizontales
          }
      }
    }

    public void drawPills( Graphics g, Vector<Pill> pills ){
      int n = pills.size();
      for( int i=0; i<n; i++ ){
          pills.get(i).draw(g);
      }
    }

    public void drawPowerPills( Graphics g, Vector<PowerPill> powerPills ){
      int n = powerPills.size();
      for( int i=0; i<n; i++ ){
          powerPills.get(i).draw(g);
      }
    }

    public void drawGhosts( Graphics g, Vector<Ghost> ghosts ){
      int n = ghosts.size();
      for( int i=0; i<n; i++ ){
          ghosts.get(i).draw(g);
      }
    }

    public void drawMsPacman( Graphics g, MsPacman msPacman ){
      msPacman.draw(g);
    }

    public void draw(Graphics g) {
        if (environment != null && g != null) {
          g.setColor(Color.black);
          g.fillRect(0, 0, w, h);
            // ensure only one thread gets through this at any one time
            synchronized (this) {
              drawGrid( g );

                Percept p = environment.sense(environment.getAgent());

                drawPills( g, (Vector<Pill>)p.getAttribute("pills") );
                drawPowerPills( g, (Vector<PowerPill>)p.getAttribute("powerPills") );
                drawGhosts( g, (Vector<Ghost>)p.getAttribute("ghosts") );
                Object msPacman = p.getAttribute("pacman");
                if( msPacman != null ){
                  drawMsPacman(g, (MsPacman)msPacman);
                }

                g.setColor(Color.WHITE);
                StringBuffer sb = new StringBuffer();
                sb.append("Board:"+p.getAttribute("board"));
                sb.append(" Pills:"+((Vector)p.getAttribute("pills")).size());
                sb.append(" Power Pills:"+((Vector)p.getAttribute("powerPills")).size());
                g.drawString(sb.toString(),5,20);


//                if( environment.playing ){
//                }
            }
        }
    }

    public PacmanDrawer(int w, int h, Environment environment) {
      super( environment, new Dimension(w, h) );
        this.w = w;
        this.h = h;
    }
}
