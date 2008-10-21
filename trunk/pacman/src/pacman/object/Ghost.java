package pacman.object;
import java.awt.*;

import pacman.util.*;

/**
 * <p>Title: Ghost</p>
 *
 * <p>Description: Ghost</p>
 *
 * <p>Copyright: Copyright (c) 2008</p>
 *
 * <p>Institution: Universidad Nacional de Colombia</p>
 *
 * @author Professor Jonatan Gomez-Perdomo, Ph. D. (using code by Leandro Liu)
 * @version 1.0
 */
public class Ghost extends DynamicPacmanObject {

   protected Color c;

   protected static final int width = 12;
   protected static final int height = 12;


  public int color;
  public boolean dangerous = true;

  public Ghost( int x, int y, int _color ) {
    super( x, y );
    color = _color;
    c = new Color((color & 0xFF0000) >> 16, (color & 0xFF00) >> 8, (color & 0xFF));
  }

  public Ghost( int x, int y ) {
    super( x, y );
    dangerous = false;
    color = PacmanConstants.edibleGhost;
    c = new Color((color & 0xFF0000) >> 16, (color & 0xFF00) >> 8, (color & 0xFF));
  }

  public Ghost( Point _position, int _color ){
    super( _position );
    color = _color;
  }

  public Ghost( Point _position ){
    super( _position );
    dangerous = false;
  }

  public void draw(Graphics g){
      g.setColor(c);
      g.fillRect( position.x-(width/2), position.y-(height/2), width, height);
  }

}
