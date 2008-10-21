package pacman;
import unalcol.agents.simulate.util.SimpleLanguage;

/**
 * <p>Title: PacmanLanguage</p>
 *
 * <p>Description: Pacman Language</p>
 *
 * <p>Copyright: Copyright (c) 2008</p>
 *
 * <p>Institution: Universidad Nacional de Colombia</p>
 *
 * @author Professor Jonatan Gomez-Perdomo, Ph. D.
 * @version 1.0
 */
public class PacmanLanguage extends SimpleLanguage{

  public PacmanLanguage() {
    super( new String[]{"blinky", "pinky", "inky", "sue", "ghosts", "pacman",
                        "pills", "powerPills", "board", "playing"},
           new String[]{"left", "up", "right", "down", "nop", "die"} );
  }
}
