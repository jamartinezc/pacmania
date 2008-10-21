package pacman.environment;
import java.util.*;

import java.awt.*;

import pacman.*;
import pacman.util.*;
import unalcol.agents.*;
import unalcol.agents.Action;
import unalcol.agents.simulate.*;
import unalcol.agents.simulate.util.*;

/**
 * <p>Title: PacmanEnvironment</p>
 *
 * <p>Description: Pacman Environment</p>
 *
 * <p>Copyright: Copyright (c) 2008</p>
 *
 * <p>Institution: Universidad Nacional de Colombia</p>
 *
 * @author Professor Jonatan Gomez-Perdomo, Ph. D. (built using code provided by Simon Lucas and Leandro Liu)
 * @version 1.0
 */
public class PacmanEnvironment extends Environment{
  Language language;
  PacmanSensor sensor;
  PacmanActuator actuator;

  public Vector<Action> actions(){
    Vector<Action> actions = new Vector<Action>();
    for( int i=0; i<language.getActionsNumber(); i++ ){
      actions.add( new Action(language.getAction(i)) );
    }
    return actions;
  }
  public void init( Agent agent ){}
  public boolean execute( SimulatedAgent agent, Action action ){
    return actuator.act(action);
  }
  public Percept getPercept( SimulatedAgent agent ){
    return sensor.get();
  }


  public int lastMapColor=0;
  public boolean mapChange=false;

  public boolean playing;
  public int board;
  //A COMER
  public boolean party;

  public PacmanEnvironment( AgentProgram agent_program ) throws Exception{
    super( agent_program );
    language = new PacmanLanguage();
    sensor = new PacmanSensor();
    actuator = new PacmanActuator(language);
  }
    // @TODO: Get the board and playing flags


  public static Point absolute2Grid( Point pos ){
      return new Point( ( pos.x - PacmanConstants.leftMargin ) / PacmanConstants.cellWidth,
                        ( pos.y - PacmanConstants.topMargin ) / PacmanConstants.cellHeight );
  }

  public static Point grid2Absolute( Point pos ){
      return new Point( PacmanConstants.leftMargin  + pos.x * PacmanConstants.cellWidth,
                        PacmanConstants.topMargin + pos.y * PacmanConstants.cellHeight );
  }

  public void computeSpeed(PacmanEnvironment lastE){
/*      if(lastE.msPacman!=null&&msPacman!=null){
        msPacman.computeSpeed(lastE.msPacman);
      }
      if(lastE.gBlinky!=null&&gBlinky!=null){
        gBlinky.computeSpeed(lastE.gBlinky);
      }
      if(lastE.gPinky!=null&&gPinky!=null){
        gPinky.computeSpeed(lastE.gPinky);
      }
      if(lastE.gInky!=null&&gInky!=null){
        gInky.computeSpeed(lastE.gInky);
      }
      if(lastE.gSue!=null&&gSue!=null){
        gSue.computeSpeed(lastE.gSue);
      }
*/
  }
  public boolean boxed(int y,int x){
      if(y>=12&&x>=10){
          if(y<=16&&x<=17){
              return true;
          }
      }
      return false;
  }

}
