package pacman.object;
import java.awt.*;

import pacman.util.*;

/**
 * <p>Title: DynamicPacmanObject</p>
 *
 * <p>Description: Dynamic Pacman Object</p>
 *
 * <p>Copyright: Copyright (c) 2008</p>
 *
 * <p>Institution: Universidad Nacional de Colombia</p>
 *
 * @author Professor Jonatan Gomez-Perdomo, Ph. D. (using code by Leandro Liu)
 * @version 1.0
 */
public abstract class DynamicPacmanObject extends PacmanObject {
  public static final int top = 1;
  public static final int left = 4;
  public static final int right = 2;
  public static final int bottom = 3;


    public int magnitude = 0;
    public int direction = 0;

    public Point Prediction;

  public Point speed = new Point();

  public DynamicPacmanObject( int x, int y ) {
    super( x, y );
  }

  public DynamicPacmanObject( Point _position ){
    super( _position );
  }

  public void computeSpeed( DynamicPacmanObject previous ){
    speed.x = position.x - previous.position.x;
    speed.y = position.y - previous.position.y;
    magnitude = speed.x+speed.y;
    if(Math.abs(speed.x)>Math.abs(speed.y)){
        if(speed.x<0||speed.x>PacmanConstants.width/2){
            direction=left;
        }else{
            direction=right;
        }
    }else{
        if(speed.y<0){
            direction=top;
        }else{
            direction=bottom;
        }

        switch (direction){
            case 1:
                Prediction=new Point(this.gridPosition.x,this.gridPosition.y-1);
                break;
            case 2:
                if(this.gridPosition.x!=27){
                    Prediction=new Point(this.gridPosition.x+1,this.gridPosition.y);
                }else{
                    Prediction=new Point(0,this.gridPosition.y);
                }
                break;
            case 3:
                Prediction=new Point(this.gridPosition.x,this.gridPosition.y+1);
                break;
            case 4:
                if(this.gridPosition.x!=0){
                    Prediction=new Point(this.gridPosition.x-1,this.gridPosition.y);
                }else{
                    Prediction=new Point(27,this.gridPosition.y);
                }
        }
    }
   //debug purposes
   // System.out.println("D"+direction+"M"+magnitude);

  }


}
