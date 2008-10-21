package pacman.object;
import java.awt.*;

import pacman.util.*;

/**
 * <p>Title: PowerPill</p>
 *
 * <p>Description: PowerPill</p>
 *
 * <p>Copyright: Copyright (c) 2008</p>
 *
 * <p>Institution: Universidad Nacional de Colombia</p>
 *
 * @author Professor Jonatan Gomez-Perdomo, Ph. D. (using code by Leandro Liu)
 * @version 1.0
 */
public class PowerPill extends PacmanObject{

  protected static final Color c = new Color((PacmanConstants.pill & 0xFF0000) >> 16,
                                             (PacmanConstants.pill & 0xFF00) >> 8,
                                             (PacmanConstants.pill & 0xFF));

  protected static final int width = 8;
  protected static final int height = 8;

  public PowerPill(Point _position ) {
      super( _position );
  }

  public PowerPill( int x, int y ) {
     super( x, y );
  }

  public void draw(Graphics g){
      g.setColor(c);
      g.drawRect( position.x-(width/2), position.y-(height/2), width, height);
  }
}
