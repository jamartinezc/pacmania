package pacman;

import java.awt.*;

import pacman.environment.*;
import pacman.gui.*;
import pacman.util.*;
import unalcol.agents.*;
import unalcol.agents.simulate.gui.*;

public class MsPacmanMain {
    public static void main(String[] args) throws Exception {

        long delay = 50;

        //quiere mostrar o no el visor d imgs
        boolean display = true;

        AgentProgram agent_program = new FMZPacmanAgentProgram();

        PacmanEnvironment env = new PacmanEnvironment( agent_program );
        env.setDelay(delay);

        if( display ){
          DrawerPanel pd = new DrawerPanel(
               new PacmanDrawer( PacmanConstants.width, PacmanConstants.height,
                                 env),
               Color.black);
          JEasyFrame frame = new JEasyFrame(pd, "PAC Environment Vision", true);
          frame.setLocation(800, 0);
          env.registerView(frame);
        }
        env.run();
    }
}
