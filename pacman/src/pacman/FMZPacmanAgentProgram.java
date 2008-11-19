/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pacman;

import java.awt.Point;
import java.util.Vector;
import pacman.fieldsagent.*;
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
public class FMZPacmanAgentProgram  implements AgentProgram{

        /** Lenguaje para el actuador */
    Language language;
    private int prevActionCode[];

    private static final double pacmanCharge = 1.0;
    private static final double ghostsCharge = 1000.0;
    private static final double pillsCharge = -5.0;// >30
    private static final double powerPillsCharge = -1.0;
    private static final double ghostsHunger = 3.0;//

    private static final int zSize=3;//ancho de la zona a verificar

    private double upPreferece=0;
    private double downPreferece=0;
    private double leftPreferece=0;
    private double rightPreferece=0;
    
    private int prevDirection=0;
    private Vector<Point> prevPositions=new Vector<Point>();
    private final int PositionsPerceptMax = 20;
    private boolean stuck=false;

    int operationsCounter;
    long startTime;
    private int stuckDir;


    FMZPacmanAgentProgram()
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
        Vector2D fleeVector = new Vector2D(0,0);//it's used to determine if the ghost are near
        Action action;
        Vector2D force;
        MsPacman pacman;
        double distance;
        Vector2D pillsForce;
        Vector2D unitVector;
        String actionString;
        Vector<Ghost> ghosts;
        Vector<PowerPill> pPills;
        Vector<Pill> pills;
        Vector<Vector2D> forces;
        Vector<Vector2D> yForces = new Vector<Vector2D>();
        Vector<Vector2D> xForces = new Vector<Vector2D>();

        Vector2D yResultant = new Vector2D();
        Vector2D xResultant = new Vector2D();


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
            if(pills.size()<=10) resolution = 40;
            else if(pills.size()<=50) resolution = 8;

        if(ghosts == null || pacman == null)
            return new Action("nop");
        
        int x = pacman.gridPosition.x;
        int y = pacman.gridPosition.y;

        // Computar el vector de fuerza para cada fantasma
        forces = new Vector<Vector2D>();
        n = ghosts.size();

        for(i = 0; i < n; i++)
        {
            // Calcular un vector unitario en la dirección fantasma-pacman
            ghost = ghosts.get(i);
            unitVector = new Vector2D(pacman.position.x - ghost.position.x, pacman.position.y - ghost.position.y);
            distance = unitVector.getModule();

            unitVector = unitVector.toUnitVector();

            // Calcular el vector de fuerza usando la "Ley de Coulomb"
            force = new Vector2D();
            if( !(ghost.gridPosition.x > 10 && ghost.gridPosition.x < 17 && ghost.gridPosition.y > 12 && ghost.gridPosition.y < 16) ){//treshold 70: determina la distancia a la que le importa al pacman que estén los fantasmas, ademas verifica que el fantasma no se encuentre en la jaula   &&
                if(ghost.color == PacmanConstants.blinky || ghost.color == PacmanConstants.inky || ghost.color == PacmanConstants.pinky || ghost.color == PacmanConstants.sue)
                //if(ghost.color != PacmanConstants.edibleGhost)
                {
                    force = unitVector.scalarMultiply((double)((pacmanCharge * ghostsCharge)/Math.pow(distance, 2)));
                    fleeVector = fleeVector.add(force);
                }
                else
                {
                    force = unitVector.scalarMultiply((double)((pacmanCharge * -ghostsHunger*ghostsCharge)/Math.pow(distance, 2)));
                    if(distance <= 70)
                        forces.add(force);
                }

                if ((ghost.gridPosition.x <= x + zSize && ghost.gridPosition.x >= x - zSize) && Math.abs(ghost.gridPosition.y - y) <= 10) {// se encuentra en la zona de el eje Y, sumarlo a esa fuerza
                    yForces.add(force);
                    stuck = false;//ya no está trabado
                }
                
                if ( (ghost.gridPosition.y <= y + zSize && ghost.gridPosition.y >= y - zSize) && Math.abs(ghost.gridPosition.x - x) <= 10) {// se encuentra en la zona de el eje X, sumarlo a esa fuerza
                    xForces.add(force);
                    stuck = false;//ya no está trabado
                }
            }
            //if the ghost are far, lose interest in the PowerPills
            if(fleeVector.getModule() >= 0.15) disinterest = 5;
            //System.out.println("Flee: "+fleeVector.getModule());
        }
        n=pPills.size();
        for(i=0;i<n;i++){
            // Calcular un vector unitario en la dirección PowerPill-pacman
            pPill = pPills.get(i);
            unitVector = new Vector2D(pacman.position.x - pPill.position.x, pacman.position.y - pPill.position.y);
            distance = unitVector.getModule();

            unitVector = unitVector.toUnitVector();

            // Calcular el vector de fuerza usando la "Ley de Coulomb"
            force = new Vector2D();
            force = unitVector.scalarMultiply((double)((pacmanCharge * powerPillsCharge / disinterest)/Math.pow(distance, 2)));
            forces.add(force);
        }


        n=pills.size();
        for(i=0;i<n;i++){
            // Calcular un vector unitario en la dirección PowerPill-pacman
            pill = pills.get(i);
            unitVector = new Vector2D(pacman.position.x - pill.position.x, pacman.position.y - pill.position.y);
            distance = unitVector.getModule();

            unitVector = unitVector.toUnitVector();

            // Calcular el vector de fuerza usando la "Ley de Coulomb"
            force = new Vector2D();
            force = unitVector.scalarMultiply((double)((pacmanCharge * pillsCharge * resolution)/Math.pow(distance, 4)));
            forces.add(force);
        }

        // Computar la suma vectorial de las fuerzas
        n=yForces.size();
        for(i = 0; i < n; i++){
            yResultant    = yResultant.add(yForces.get(i));
        }

        n=xForces.size();
        for(i = 0; i < n; i++){
            xResultant  = xResultant.add(xForces.get(i));
        }

        pillsForce = new Vector2D();
        n=forces.size();
        for(i = 0; i < n; i++){
            pillsForce = pillsForce.add(forces.get(i));
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
        int board = (Integer)(p.getAttribute("board"));
        /*
         * if there is an error in the perseption, then, don't move
         */
        if(board <= 0 || board >=5){
            return new Action("nop");
        }
        //System.out.println("pos: "+pacman.position);
        int[] posibleMovements = MsPacmanMap1.consultPosibleMovements(x, y, board);


        //preparar las preferencias de las direcciones
        int surroundings[] = MsPacmanMap1.consultSurroundings(x, y, board);
        int forceDirection = getForceDirection(pillsForce);

        upPreferece=0;
        downPreferece=0;
        leftPreferece=0;
        rightPreferece=0;
        if((prevPositions.size()>=PositionsPerceptMax*0.8 || stuck)){
            double prevDist = prevPositions.firstElement().distance( prevPositions.lastElement() );//mide la distancia que ha recorrido en 'PositionsPerceptMax' perseptions
            if(prevDist < 3 || stuck){
                if((stuck && stuckDir==3) || (forceDirection == 2)&&(surroundings[3] == 1 )){// si hay pared en la dirección que hala la fuerza
                    downPreferece=1;//preferir hacia abajo
                    stuck=true;
                    stuckDir=3;
                }
                if((stuck && stuckDir==1) || (forceDirection == -2)&&(surroundings[1] == 1 )){// si hay pared en la dirección que hala la fuerza
                    downPreferece=1;//preferir hacia abajo
                    stuck=true;
                    stuckDir=1;
                }
                if((stuck && stuckDir==0) || (forceDirection == 1)&&(surroundings[0] == 1 )){// si hay pared en la dirección que hala la fuerza
                    rightPreferece=1;//preferir hacia la derecha
                    stuck=true;
                    stuckDir=0;
                }
                if((stuck && stuckDir==2) || (forceDirection == -1)&&(surroundings[2] == 1 )){// si hay pared en la dirección que hala la fuerza
                    rightPreferece=1;//preferir hacia la derecha
                    stuck=true;
                    stuckDir=2;
                }
                //(up,right,down,left)
                if(stuck && (surroundings[stuckDir] != 1)){//si estaba trabado, pero ya no hay pared hacia donde estaba trabado
                    stuck = false;//ya no está trabado
                    switch(stuckDir){//preferir moverse hacia donde estaba trabado
                        case 1: {upPreferece=1;break;}
                        case 2: {rightPreferece=1;break;}
                        case 3: {downPreferece=1;break;}
                        case 4: {leftPreferece=1;break;}
                    }
                }

                //System.out.println("FAKE PILL!!!");
                prevPositions.clear();
            }
        }

        // verify for each posible movement the outcome for that movement
        // and set the movement with the highest outcome.
        n = posibleMovements.length;
        double movementOutcome = Double.NEGATIVE_INFINITY;
        double currentMovementOutcome;
        int currentDirection;
        int direction = nop;// initialized in "don't move" (nop)
        for(i = 0 ; i < n ; i++){
            currentDirection = posibleMovements[i];
            currentMovementOutcome = getMovementOutcome(pillsForce, currentDirection, xResultant, yResultant);
            switch(currentDirection){
                case  1: { currentMovementOutcome+=upPreferece*currentMovementOutcome*0.15; break;}
                case -1: { currentMovementOutcome+=downPreferece*currentMovementOutcome*0.15; break;}
                case  2: { currentMovementOutcome+=leftPreferece*currentMovementOutcome*0.15; break;}
                case -2: { currentMovementOutcome+=rightPreferece*currentMovementOutcome*0.15; break;}
            }
            if(currentMovementOutcome > movementOutcome){
                direction = currentDirection;
                movementOutcome = currentMovementOutcome;
            }
        }

        switch(direction){
            case  1: { actionString="up"; break;}
            case -1: { actionString="down"; break;}
            case  2: { actionString="left"; break;}
            case -2: { actionString="right"; break;}
            default://case 0
                     { actionString="nop";}
        }
        action = new Action(actionString);
        prevDirection = direction;//guarda la accion como la ultima accion tomada

        //guarda las ultimas 3 posiciones en las que se detectó al pacman
        if(prevPositions.size()>PositionsPerceptMax){
            prevPositions.remove(0);
        }
        prevPositions.add(pacman.gridPosition);

        /*
         * Count the current movement compute as another operation
         * in the current second.
         */
        operationsCounter++;
        if( (java.lang.System.currentTimeMillis() - startTime) >= 1000){//si ya ha pasado un segundo
        System.out.println(operationsCounter+" operaciones X seg");
        operationsCounter = 0;
        startTime = java.lang.System.currentTimeMillis();//traer la hora actual
        }
        System.out.println("stuck="+stuck);
        return action;
    }

//    private double getMovementOutcome(Vector2D force, int direction){
//        double outcome = double.MIN_VALUE;
//        /*if(Math.abs(direction) == 1){
//
//            outcome = -direction * force.getY();// direction determines the sign
//
//        }else if(Math.abs(direction) == 2){
//
//            outcome = -direction/2 * force.getX();// direction determines the sign
//
//        }*/
//        switch(direction){
//            case  1: { outcome = yResultant.getY()    + upPreferece; break;}
//            case -1: { outcome = downResultant.getY()  + downPreferece; break;}
//            case  2: { outcome = xResultant.getX()  + leftPreferece; break;}
//            case -2: { outcome = rightResultant.getX() + rightPreferece; break;}
//            default://case 0
//                     {outcome = double.MIN_VALUE;}
//        }
//
//
//        return outcome;
//    }
        private double getMovementOutcome(Vector2D force, int direction, Vector2D xForce, Vector2D yForce){

            double outcome = Double.MIN_VALUE;

            if(Math.abs(direction) == 1){

                Vector2D yTotalForce = new Vector2D(0, force.getY());
                Vector2D windY = new Vector2D();
                windY.setY( yForce.getModule() * Math.signum(yForce.getY()) );
                yTotalForce = yTotalForce.add(windY);
                outcome = -direction * yTotalForce.getY();// direction determines the sign

            }else if(Math.abs(direction) == 2){

                Vector2D xTotalForce = new Vector2D(force.getX(), 0);
                Vector2D windX = new Vector2D();
                windX.setX( xForce.getModule() * Math.signum(xForce.getX()) );
                xTotalForce = xTotalForce.add(windX);
                outcome = -direction * xTotalForce.getX();// direction determines the sign
            }

            return outcome;
        }

        /**
         * Encuentra la dirección en la que mas hala la fuerza.
         * @param force la fuerza a evaluar
         * @return la dirección en la que hala (1,-1,2,-2)
         */
        private int getForceDirection(Vector2D force){
            double max = force.getX();
            int direction = 1;
            if( -force.getX() > max ){
                max = -force.getX();
                direction = -1;
            }
            if( force.getY() > max ){
                max = force.getY();
                direction = -2;
            }
            if( -force.getY() > max ){
                direction = 2;
            }

            return direction;
        }
}
