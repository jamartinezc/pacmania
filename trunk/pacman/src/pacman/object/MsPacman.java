package pacman.object;
import java.awt.*;

import pacman.util.*;

/**
 * <p>Title: MsPacman</p>
 *
 * <p>Description: MsPacman</p>
 *
 * <p>Copyright: Copyright (c) 2008</p>
 *
 * <p>Institution: Universidad Nacional de Colombia</p>
 *
 * @author Professor Jonatan Gomez-Perdomo, Ph. D. (using code by Leandro Liu)
 * @version 1.0
 */
public class MsPacman extends DynamicPacmanObject {

    protected static final Color c = new Color((PacmanConstants.pacMan & 0xFF0000) >> 16,
                                              (PacmanConstants.pacMan & 0xFF00) >> 8,
                                              (PacmanConstants.pacMan & 0xFF));


   protected static final int width = 12;
   protected static final int height = 12;



  public MsPacman( int x, int y ) {
    super( x, y );
  }

  public MsPacman( Point _position ){
    super( _position );
  }

  public void draw(Graphics g){
      g.setColor(c);
      g.fillRect( position.x-(width/2), position.y-(height/2), width, height);
  }
}
