/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pacman;

import java.util.Iterator;
import java.util.Vector;
import pacman.object.Ghost;
import pacman.object.MsPacman;
import pacman.util.PacmanConstants;
import unalcol.agents.Action;
import unalcol.agents.AgentProgram;
import unalcol.agents.Percept;
import unalcol.agents.simulate.util.Language;

/**
 *
 * @author jaguar
 */
public class PacmanAgentProgram implements AgentProgram{

    Language language;
    private int[] charge;

    
    public PacmanAgentProgram() {
        language = new PacmanLanguage();
        charge = new int[4];
        charge[0] = 10;//BLINKY's Charge
        charge[1] = 10;//INKY's Charge
        charge[2] = 10;//PINKY's Charge
        charge[3] = 10;//SUE's Charge
        //MsPacman's charge = 1.
    }
    
    @Override
    public Action compute(Percept percept) {
        Vector<Ghost> ghosts = (Vector<Ghost>) percept.getAttribute("ghosts");
        Iterator<Ghost> ghostsI = ghosts.iterator();
        int ghostCharge;
        int componentX=0, componentY=0;
        double theta;

        //Ms-Pacman position:
        MsPacman msPacman = (MsPacman)percept.getAttribute("pacman");

        if (msPacman != null) {
            int X = msPacman.position.x;
            int Y = msPacman.position.y;

            while (ghostsI.hasNext()) {
                Ghost ghost = ghostsI.next();

                switch (ghost.color) {
                    case PacmanConstants.blinky: {
                        ghostCharge = charge[0];
                        break;
                    }
                    case PacmanConstants.inky: {
                        ghostCharge = charge[1];
                        break;
                    }
                    case PacmanConstants.pinky: {
                        ghostCharge = charge[2];
                        break;
                    }
                    case PacmanConstants.sue: {
                        ghostCharge = charge[3];
                        break;
                    }
                    default: {
                        ghostCharge = 0;
                    }
                }

                //if ((msPacman.position.y - ghost.position.y) != 0) {
                    theta = Math.atan2((msPacman.position.y - ghost.position.y) , (msPacman.position.x - ghost.position.x));
                //} else {
                    //pacman and the ghost are in the same Y coordinates, so the angle is 90º
                    //theta = Math.PI / 2;
                //}

                double dist = msPacman.position.distance(ghost.position);
                double magnitude;
                if (dist != 0.0d) {
                    magnitude = (ghostCharge / dist * dist);
                } else {
                    magnitude = Double.POSITIVE_INFINITY;
                }
                componentX += magnitude * Math.cos(theta);
                componentY += magnitude * Math.sin(theta);
            }
            //after that cicle componetX and componentY have the movement vector's components
            
            if (Math.abs(componentY) > Math.abs(componentX)) {
                System.out.println(componentX + "<" + componentY);
                if (componentY < 0) {
                    System.out.println("arriba = ir a:"+language.getAction(1));
                    return new Action(language.getAction(1));//go UP
                } else {
                    System.out.println("abajo = ir a:"+language.getAction(3));
                    return new Action(language.getAction(3));//go DOWN
                }
            } else {
                System.out.println(componentX + ">" + componentY);
                if (componentX > 0) {
                    System.out.println("der = ir a:"+language.getAction(2));
                    return new Action(language.getAction(2));//go RIGHT
                } else {
                    System.out.println("izq = ir a:"+language.getAction(0));
                    return new Action(language.getAction(0));//go LEFT
                }
            }
        }else{
                System.out.println("ir a:"+language.getAction(4));
                return new Action(language.getAction(4));//"nop"
            }
    }

    @Override
    public void init() {
    }

    public static void main(String[] args) {
        PacmanAgentProgram pap = new PacmanAgentProgram();
        Percept percept = new Percept();
        MsPacman mP = new MsPacman(0, 1);
        Ghost g = new Ghost(1, 1, PacmanConstants.blinky);
        Vector<Ghost> vG = new Vector<Ghost>();
        vG.add(g);
        percept.setAttribute("pacman", mP);
        percept.setAttribute("ghosts", vG);
        pap.compute(percept);
    }
}
