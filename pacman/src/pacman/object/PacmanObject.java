package pacman.object;
import java.awt.*;

import pacman.environment.*;
import pacman.gui.*;
/**
 * <p>Title: PacmanObjec</p>
 *
 * <p>Description: PacmanObject</p>
 *
 * <p>Copyright: Copyright (c) 2008</p>
 *
 * <p>Institution: Universidad Nacional de Colombia</p>
 *
 * @author Professor Jonatan Gomez-Perdomo, Ph. D. (using code by Leandro Liu)
 * @version 1.0
 */
public abstract class PacmanObject implements Drawable{

  public Point position;
  public Point gridPosition;

  public PacmanObject( Point _position ) {
    position = _position;
    gridPosition = PacmanEnvironment.absolute2Grid( position );
  }

  public PacmanObject( int x, int y ) {
    position = new Point(x,y);
    gridPosition = PacmanEnvironment.absolute2Grid( position );
  }
}
