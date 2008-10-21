package pacman.environment;
import java.awt.*;
import java.awt.event.*;

import unalcol.agents.Action;
import unalcol.agents.simulate.util.*;

/**
 * <p>Title: PacmanActuator</p>
 *
 * <p>Description: Pacman Actuator</p>
 *
 * <p>Copyright: Copyright (c) 2008</p>
 *
 * <p>Institution: Universidad Nacional de Colombia</p>
 *
 * @author Professor Jonatan Gomez-Perdomo, Ph. D. (built using code provided by Simon Lucas and Leandro Liu)
 * @version 1.0
 */
public class PacmanActuator {
  protected Robot robot;
  boolean keyPressed;
  int curKey;
  int curDir;
  // not used when waitForIdle isset to false
  static int autoDelay = 20;
  // these are related to the definitions in PacController
  static int[] keys = {KeyEvent.VK_LEFT, KeyEvent.VK_UP, KeyEvent.VK_RIGHT,
                       KeyEvent.VK_DOWN, -1, -1};
  protected Language language;

  public PacmanActuator(Language language) throws Exception{
    this.language = language;
    curKey = -1;
    keyPressed = false;
    robot = new Robot();
    robot.setAutoWaitForIdle(false);
    robot.setAutoDelay(autoDelay);
  }

  public boolean act( Action action ){
    int id = language.getActionIndex(action.getCode());
    // release the current key if it is pressed
    if (keyPressed) {
        robot.keyRelease( curKey );
        keyPressed = false;
    }
    // now work out the action
    if (id < 4 ) {
        curKey = keys[id];
        robot.keyPress(curKey);
        robot.keyRelease(curKey);
        robot.waitForIdle();
//            keyPressed = true;
    }
    curDir = id;
    return true;
  }
}
