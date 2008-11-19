/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pacman;

import java.awt.Point;
import java.util.Vector;
import pacman.fieldsagent.MsPacmanMap;
import pacman.fieldsagent.Vector2Df;
import pacman.object.*;
import pacman.util.PacmanConstants;
import unalcol.agents.Action;
import unalcol.agents.AgentProgram;
import unalcol.agents.Percept;
import unalcol.agents.simulate.util.Language;

/**
 *
 * @author jaguar
 */
public class FieldsNmapPacmanAgentProgram implements AgentProgram{

        /** Lenguaje para el actuador */
    Language language;
    private int prevDirection=2;
    private int prevActionCode[];
    
    private static final float pacmanCharge = 25.0f;
    private static final float ghostsCharge = 50.0f;
    private static final float pillsCharge = -4.0f;// >30
    private static final float powerPillsCharge = -300.0f;
    private static final float ghostsHunger = 3.0f;//

    int operationsCounter;
    long startTime;
    
    FieldsNmapPacmanAgentProgram()
    {
        // Inicializa los atributos de la clase
        language = new PacmanLanguage();
        operationsCounter = 0;
        startTime = 0L;
        prevActionCode = new int[2];//memoria de dos operaciones anteriores
        prevActionCode[0]=0;
    }
    
    /** Función de inicialización vacía, no se necesita un procedimiento
     *  de inicialización. */
    @Override
    public void init() {}
    
    @Override
    public Action compute(Percept p) {
        int i;
        int n;
        Ghost ghost;
        PowerPill pPill;
        Pill pill;
        int resolution = 1;//determines the MsPacman's desire to conclude the world
        int disinterest = 1;//determines the MsPacman's interest in PowerPills
        Vector2Df fleeVector = new Vector2Df(0,0);//it's used to determine if the ghost are near
        Action action;
        Vector2Df force;
        MsPacman pacman;
        float distance;
        Vector2Df resultant;
        Vector2Df unitVector;
        String actionString;
        Vector<Ghost> ghosts;
        Vector<PowerPill> pPills;
        Vector<Pill> pills;
        Vector<Vector2Df> forces;
        
        // Obtener la posición de los fantasmas
        ghosts = (Vector<Ghost>)p.getAttribute("ghosts");
        
        // Obtener la posición de pacman
        pacman = (MsPacman)p.getAttribute("pacman");

        //obtener las posiciones de power Pills
        pPills = (Vector<PowerPill>)p.getAttribute("powerPills");
            if(pPills.size()==1) disinterest = 5;

        //obtener las Posiciones de Pills
        pills = (Vector<Pill>)p.getAttribute("pills");
            // si quedan menos de 30 pills, su carga aumenta
            if(pills.size()<=10) resolution = 50;
            else if(pills.size()<=30) resolution = 8;
        
        if(ghosts == null || pacman == null)
            return new Action("nop");
        
        // Computar el vector de fuerza para cada fantasma
        forces = new Vector<Vector2Df>();
        n = ghosts.size();
        
        for(i = 0; i < n; i++)
        {
            // Calcular un vector unitario en la dirección fantasma-pacman
            ghost = ghosts.get(i);
            unitVector = new Vector2Df(pacman.position.x - ghost.position.x, pacman.position.y - ghost.position.y);
            distance = unitVector.getModule();
            
            unitVector = unitVector.toUnitVector();
                        
            // Calcular el vector de fuerza usando la "Ley de Coulomb"
            force = new Vector2Df();
            if(ghost.color == PacmanConstants.blinky || ghost.color == PacmanConstants.inky || ghost.color == PacmanConstants.pinky || ghost.color == PacmanConstants.sue)
            //if(ghost.color != PacmanConstants.edibleGhost)
            {
                force = unitVector.scalarMultiply((float)((pacmanCharge * ghostsCharge)/Math.pow(distance, 2)));
                fleeVector = fleeVector.add(force);
            }
            else
            {
                if(distance<=60)
                    force = unitVector.scalarMultiply((float)((pacmanCharge * -ghostsHunger*ghostsCharge)/Math.pow(distance, 2)));
            }

            forces.add(force);

            //if the ghost are far, lose interest in the PowerPills
            if(fleeVector.getModule() >= 0.15) disinterest = 5;
            //System.out.println("Flee: "+fleeVector.getModule());
        }
        n=pPills.size();
        for(i=0;i<n;i++){
            // Calcular un vector unitario en la dirección PowerPill-pacman
            pPill = pPills.get(i);
            unitVector = new Vector2Df(pacman.position.x - pPill.position.x, pacman.position.y - pPill.position.y);
            distance = unitVector.getModule();
            
            unitVector = unitVector.toUnitVector();
                        
            // Calcular el vector de fuerza usando la "Ley de Coulomb"
            force = new Vector2Df();
            force = unitVector.scalarMultiply((float)((pacmanCharge * powerPillsCharge / disinterest)/Math.pow(distance, 2)));
            forces.add(force);
        }
        
        
        n=pills.size();
        for(i=0;i<n;i++){
            // Calcular un vector unitario en la dirección PowerPill-pacman
            pill = pills.get(i);
            unitVector = new Vector2Df(pacman.position.x - pill.position.x, pacman.position.y - pill.position.y);
            distance = unitVector.getModule();
            
            unitVector = unitVector.toUnitVector();
                        
            // Calcular el vector de fuerza usando la "Ley de Coulomb"
            force = new Vector2Df();
            force = unitVector.scalarMultiply((float)((pacmanCharge * pillsCharge * resolution)/Math.pow(distance, 2)));
            forces.add(force);
        }
        
        // Computar la suma vectorial de las fuerzas
        n=pPills.size() + ghosts.size() + pills.size();
        resultant = new Vector2Df();
        for(i = 0; i < n; i++)
        {
            resultant = resultant.add(forces.get(i));
        }
                        
        // Preparar las acciones
        int actionCode=0;
        int up=1;
        int down=-1;
        int left=2;
        int right=-2;
        int nop=0;


        //determine the movement acording to the terrain

        //get the posible movements
        int x = pacman.position.x;
        int y = pacman.position.y;
        int board = (Integer)(p.getAttribute("board"));
        if(board <= 0 || board >=5){
            return new Action("nop");
        }
        //System.out.println("pos: "+pacman.position);
        int[] posibleMovements = MsPacmanMap.consultPosibleMovements(prevDirection, x, y, board);
        
        //verify for each posible movement the outcome for that movement
        // and set the movement with the highest outcome.
        n = posibleMovements.length;
        float movementOutcome = 0;
        float currentMovementOutcome;
        int currentDirection;
        int direction = 0;// initialized in "don't move" (nop)
        for(i = 0 ; i < n ; i++){
            
            currentDirection = posibleMovements[i];
            currentMovementOutcome = getMovementOutcome(resultant, currentDirection);
            //System.out.println("Dir: "+currentDirection+": "+currentMovementOutcome);
            if(currentMovementOutcome > movementOutcome){
                direction = currentDirection;
                movementOutcome = currentMovementOutcome;
            }
        }
        
        prevDirection = direction;
        switch(direction){
            case  1: { actionString="up"; break;}
            case -1: { actionString="down"; break;}
            case  2: { actionString="left"; break;}
            case -2: { actionString="right"; break;}
            default://case 0
                     {actionString="nop";}
        }
        action = new Action(actionString);
        
        if (PacmanConstants.debug) System.out.println(action.getCode());
        if (PacmanConstants.debug) System.out.println("Pos:"+pacman.position);
        
        //prevActionCode[1] = prevActionCode[0];
        //prevActionCode[0] = actionCode;
        
        operationsCounter++;
        if( (java.lang.System.currentTimeMillis() - startTime) >= 1000){//si ya ha pasado un segundo
        System.out.println(operationsCounter+" operaciones X seg");
        operationsCounter = 0;
        startTime = java.lang.System.currentTimeMillis();//traer la hora actual
        }
        //System.out.println("dir: "+direction);
//        return new Action("nop");
        return action;
    }

    private float getMovementOutcome(Vector2Df force, int direction){
        Vector2Df outcomeVector = new Vector2Df();
        float comparation;
        float restMod = force.getModule()/2;
        if(Math.abs(direction) == 1){
            comparation = force.getY();
            outcomeVector.setY(comparation - direction*restMod);// direction determines the sign
        }else if(Math.abs(direction) == 2){
            comparation = force.getX();
            outcomeVector.setX(comparation - direction/2*restMod);// direction determines the sign
        }
        return outcomeVector.getModule();
    }
}
