package pacman;
import pacman.object.*;
import unalcol.agents.*;
import unalcol.agents.simulate.util.*;
import java.util.Vector;
/**
 * <p>Title: RandomPacmanAgentProgram</p>
 *
 * <p>Description: A random agent program for the Ms Pacman game</p>
 *
 * <p>Copyright: Copyright (c) 2008</p>
 *
 * <p>Institution: Universidad Nacional de Colombia</p>
 *
 * @author Professor Jonatan Gomez-Perdomo, Ph. D.
 * @version 1.0
 */
public class RandomPacmanAgentProgram implements AgentProgram {
  /**
   * The language used by the agent program. See PacmanAgent for details
   */
  Language language = new PacmanLanguage();
  /**
   * Default constructor
   */
  public RandomPacmanAgentProgram() {}

  /**
   * Special initialization process
   */
  public void init() {}

  /**
   * Computes the action that should be executed by the Ms Pacman
   * @param p Perception obtainned from the environment
   * @return Action to be executed
   */
  public Action compute(Percept p) {
    Vector<Ghost> ghosts = (Vector<Ghost>) p.getAttribute("ghosts");
    // Using the language for getting the ghosts vector
    // Vector<Ghost> ghosts = (Vector<Ghost>)p.getAttribute(language.getPercept(4));
    System.out.println( "Number of ghosts =" + ghosts.size() );
    Vector<Pill> pills = (Vector<Pill>) p.getAttribute("pills");
    System.out.println( "Number of pills =" + pills.size() );
    int k = (int) (4 * Math.random());
    System.out.println( "Action to be executed: " + language.getAction(k) );
    String actionString = language.getAction(k);
    Action action = new Action(actionString);
    return action;
  }
}
