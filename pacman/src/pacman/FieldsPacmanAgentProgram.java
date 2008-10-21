/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pacman;

import java.awt.Point;
import java.util.Vector;

import pacman.fieldsagent.Vector2D;

import pacman.object.Ghost;
import pacman.object.MsPacman;

import pacman.object.Pill;
import pacman.object.PowerPill;
import pacman.util.PacmanConstants;
import unalcol.agents.Action;
import unalcol.agents.AgentProgram;
import unalcol.agents.Percept;
import unalcol.agents.simulate.util.Language;

/** Implementa un Agente para Pacman que utiliza una simulación de campos
 *  eléctricos para determinar sus acciones.
 *
 *  @author Sergio Bobillier Ceballos, Jorge Andrés Martínez Castilo
 */

public class FieldsPacmanAgentProgram implements AgentProgram{
    /** Lenguaje para el actuador */
    Language language;
    private Point prevPosition;
    private int prevActionCode[];
    
    private static final float pacmanCharge = 25.0f;
    private static final float ghostsCharge = 50.0f;
    private static final float pillsCharge = -4.0f;// >30
    private static final float powerPillsCharge = -300.0f;
    private static final float ghostsHunger = 3.0f;//

    int operationsCounter;
    long startTime;
    
    FieldsPacmanAgentProgram()
    {
        // Inicializa los atributos de la clase
        language = new PacmanLanguage();
        prevPosition = new Point(0, 0);
        operationsCounter = 0;
        startTime = 0L;
        prevActionCode = new int[2];//memoria de dos operaciones anteriores
        prevActionCode[0]=0;
    }
    
    /** Función de inicialización vacía, no se necesita un procedimiento
     *  de inicialización. */
    @Override
    public void init() {}
    
    public Action compute(Percept p)
    {
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
        float distance;
        Vector2D resultant;
        Vector2D unitVector;
        String actionString;
        Vector<Ghost> ghosts;
        Vector<PowerPill> pPills;
        Vector<Pill> pills;
        Vector<Vector2D> forces;
        
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
            if(ghost.color != PacmanConstants.edibleGhost)
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
            unitVector = new Vector2D(pacman.position.x - pPill.position.x, pacman.position.y - pPill.position.y);
            distance = unitVector.getModule();
            
            unitVector = unitVector.toUnitVector();
                        
            // Calcular el vector de fuerza usando la "Ley de Coulomb"
            force = new Vector2D();
            force = unitVector.scalarMultiply((float)((pacmanCharge * powerPillsCharge / disinterest)/Math.pow(distance, 2)));
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
            force = unitVector.scalarMultiply((float)((pacmanCharge * pillsCharge * resolution)/Math.pow(distance, 2)));
            forces.add(force);
        }
        
        // Computar la suma vectorial de las fuerzas
        n=pPills.size() + ghosts.size() + pills.size();
        resultant = new Vector2D();
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


        //determinar el movimiento según el terreno
        if(!resultant.isZeroVector())
        {
            //mirar si la mspacman se ha movido
            if( prevPosition.distance( pacman.position ) >= 2){
                // Verificar cual de las componentes del vector resultante es mayor para moverse acorde a esto
                if( (Math.abs(resultant.getX()) >= Math.abs(resultant.getY())) ){
                    // Verificar si es negativa o positiva
                    if(resultant.getX() < 0){
                        actionCode = left;
                    }else{
                        actionCode = right;
                    }
                }else{
                    // Verificar si es negativa o positiva
                    if( resultant.getY() < 0 ){
                            actionCode = up;
                        }else{
                            actionCode = down;
                    }
                }
            }else{
                if (PacmanConstants.debug) System.out.println("Se ha quedado quieto, se moverá en el otro eje");
                if( !(Math.abs(resultant.getX()) >= Math.abs(resultant.getY())) ){
                    // Verificar si es negativa o positiva
                    if(resultant.getX() < 0){
                        actionCode = left;
                    }else{
                        actionCode = right;
                    }
                }else{
                    // Verificar si es negativa o positiva
                    if( resultant.getY() < 0 ){
                            actionCode = up;
                        }else {
                            actionCode = down;
                    }
                }
                //verificar las acciones anteriores para determinar si se encuentra voviendoce hacia la pared
                if(actionCode == prevActionCode[0] ){
                    if( actionCode == up || actionCode == down ){
                        if(resultant.getX() < 0){
                                actionCode = left;
                            }else{
                                actionCode = right;
                            }
                    }else{
                        if( resultant.getY() < 0 ){
                                    actionCode = up;
                                }else {
                                    actionCode = down;
                            }
                    }
                }else if(Math.abs(Math.abs(prevActionCode[1]) - Math.abs(prevActionCode[0])) == 1){//if is stuck on a corner
                    //Panic!
                    if (PacmanConstants.debug) System.out.println("MsPacman PANIC!!!");
                    int coin = (int)(2 * Math.random());
                    actionCode=-prevActionCode[coin];//move in counter to the "posible" least dangerous direction.
                }
            }

        }else{
            actionCode = nop;
        }
        
        
        prevPosition = new Point(pacman.position.x,pacman.position.y);
        switch(actionCode){
            case  1: { actionString="up"; break;}
            case -1: { actionString="down"; break;}
            case  2: { actionString="left"; break;}
            case -2: { actionString="right"; break;}
            default://case 0
                     {actionString="nop";}
        }
        action = new Action(actionString);
        
        if (PacmanConstants.debug) System.out.println(actionString);
        if (PacmanConstants.debug) System.out.println("Pos:"+pacman.position);

        prevActionCode[1] = prevActionCode[0];
        prevActionCode[0] = actionCode;
        
        operationsCounter++;
        if( (java.lang.System.currentTimeMillis() - startTime) >= 1000){//si ya ha pasado un segundo
            System.out.println(operationsCounter+" operaciones X seg");
            operationsCounter = 0;
            startTime = java.lang.System.currentTimeMillis();//traer la hora actual
        }
        
        return action;
    }
}
