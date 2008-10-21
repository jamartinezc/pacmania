/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pacman.fieldsagent;

import pacman.object.MsPacman;

/**
 *
 * @author jaguar
 */
public class MsPacmanMap {

    private static int ratio=8;//MsPacman ratio
    private static int PacSize = 12;
    private static int topBorder = 24;

    /*
     * 1:= wall
     * 2:= warp
     * 0:= void
     * 3:= powerpill
     * 4:= ghost's jail
     * */
    private static final int[][] maze1 = {
                    {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
                    {1,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,1},
                    {1,3,3,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,3,3,1},
                    {1,3,3,0,0,0,0,0,1,0,0,1,1,1,1,1,1,1,0,0,1,0,0,1,1,1,3,3,1},
                    {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
                    {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
                    {1,1,1,0,0,1,0,0,1,1,1,1,0,0,1,0,0,1,1,1,1,0,0,1,0,0,1,1,1},
                    {1,1,1,0,0,1,0,0,1,1,1,1,0,0,1,0,0,1,1,1,1,0,0,1,0,0,1,1,1},
                    {2,0,0,0,0,1,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,1,0,0,0,0,2},
                    {2,0,0,0,0,1,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,1,0,0,0,0,2},
                    {1,1,1,0,0,1,1,1,1,0,0,1,1,1,1,1,1,1,0,0,1,1,1,1,0,0,1,1,1},
                    {1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,1},
                    {1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,1},
                    {1,1,1,0,0,1,1,1,1,0,0,1,1,1,1,1,1,1,0,0,1,1,1,1,0,0,1,1,1},
                    {1,1,1,0,0,1,0,0,0,0,0,1,4,4,4,4,4,1,0,0,0,0,0,1,0,0,1,1,1},
                    {1,1,1,0,0,1,0,0,0,0,0,1,4,4,4,4,4,1,0,0,0,0,0,1,0,0,1,1,1},
                    {1,1,1,0,0,1,0,0,1,0,0,1,1,1,1,1,1,1,0,0,1,0,0,1,0,0,1,1,1},
                    {2,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,2},
                    {2,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,2},
                    {1,1,1,0,0,1,1,1,1,1,1,1,0,0,1,0,0,1,1,1,1,1,1,1,0,0,1,1,1},
                    {1,1,1,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,1,1,1},
                    {1,1,1,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,1,1,1},
                    {1,1,1,0,0,1,1,1,1,0,0,1,1,1,1,1,1,1,0,0,1,1,1,1,0,0,1,1,1},
                    {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
                    {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
                    {1,0,0,1,1,1,0,0,1,1,1,1,0,0,1,0,0,1,1,1,1,0,0,1,1,1,0,0,1},
                    {1,0,0,1,1,1,0,0,1,0,0,0,0,0,1,0,0,0,0,0,1,0,0,1,1,1,0,0,1},
                    {1,3,3,1,1,1,0,0,1,0,0,0,0,0,1,0,0,0,0,0,1,0,0,1,1,1,3,3,1},
                    {1,3,3,1,1,1,0,0,1,0,0,1,1,1,1,1,1,1,0,0,1,0,0,1,1,1,3,3,1},
                    {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
                    {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
                    {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
                    {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1}
                   };

    private static final int[][] maze2 = {
                    {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
                    {2,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,2},
                    {2,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,2},
                    {1,1,1,1,1,1,0,0,1,0,0,1,1,1,1,1,1,1,0,0,1,0,0,1,1,1,1,1,1},
                    {1,3,3,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,3,3,1},
                    {1,3,3,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,3,3,1},
                    {1,0,0,1,1,1,1,1,1,0,0,1,0,0,1,0,0,1,0,0,1,1,1,1,1,1,0,0,1},
                    {1,0,0,1,0,0,0,0,0,0,0,1,0,0,1,0,0,1,0,0,0,0,0,0,0,1,0,0,1},
                    {1,0,0,1,0,0,0,0,0,0,0,1,0,0,0,0,0,1,0,0,0,0,0,0,0,1,0,0,1},
                    {1,0,0,1,0,0,1,1,1,0,0,1,0,0,0,0,0,1,0,0,1,1,1,0,0,1,0,0,1},
                    {1,0,0,0,0,0,0,0,1,0,0,1,1,1,1,1,1,1,0,0,1,0,0,0,0,0,0,0,1},
                    {1,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,1},
                    {1,1,1,1,1,1,0,0,1,0,0,0,0,0,0,0,0,0,0,0,1,0,0,1,1,1,1,1,1},
                    {1,0,0,0,0,0,0,0,1,0,0,1,1,1,1,1,1,1,0,0,1,0,0,0,0,0,0,0,1},
                    {1,0,0,0,0,0,0,0,1,0,0,1,4,4,4,4,4,1,0,0,1,0,0,0,0,0,0,0,1},
                    {1,0,0,1,1,1,0,0,0,0,0,1,4,4,4,4,4,1,0,0,0,0,0,1,1,1,0,0,1},
                    {1,0,0,0,0,1,0,0,0,0,0,1,1,1,1,1,1,1,0,0,0,0,0,1,0,0,0,0,1},
                    {1,0,0,0,0,1,0,0,1,0,0,0,0,0,0,0,0,0,0,0,1,0,0,1,0,0,0,0,1},
                    {1,1,1,0,0,1,0,0,1,0,0,0,0,0,0,0,0,0,0,0,1,0,0,1,0,0,1,1,1},
                    {1,1,1,0,0,1,0,0,1,1,1,0,0,1,1,1,0,0,1,1,1,0,0,1,0,0,1,1,1},
                    {1,1,1,0,0,0,0,0,0,0,0,0,0,1,1,1,0,0,0,0,0,0,0,0,0,0,1,1,1},
                    {1,1,1,0,0,0,0,0,0,0,0,0,0,1,1,1,0,0,0,0,0,0,0,0,0,0,1,1,1},
                    {1,1,1,0,0,1,1,1,1,1,1,0,0,1,1,1,0,0,1,1,1,1,1,1,0,0,1,1,1},
                    {2,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,2},
                    {2,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,2},
                    {1,1,1,0,0,1,0,0,1,0,0,1,1,1,1,1,1,1,0,0,1,0,0,1,0,0,1,1,1},
                    {1,3,3,0,0,1,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,1,0,0,3,3,1},
                    {1,3,3,0,0,1,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,1,0,0,3,3,1},
                    {1,0,0,1,1,1,0,0,1,1,1,1,0,0,1,0,0,1,1,1,1,0,0,1,1,1,0,0,1},
                    {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
                    {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
                    {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
                    {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1}
                   };

    private static final int[][] maze3 = {
                    {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
                    {1,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,1},
                    {1,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,1},
                    {1,3,3,1,1,1,1,1,1,0,0,1,0,0,1,0,0,1,0,0,1,1,1,1,1,1,3,3,1},
                    {1,3,3,1,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,1,3,3,1},
                    {1,0,0,1,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,1,0,0,1},
                    {1,0,0,0,0,0,1,0,0,1,1,1,0,0,1,0,0,1,1,1,0,0,1,0,0,0,0,0,1},
                    {1,0,0,0,0,0,1,0,0,1,1,1,0,0,1,0,0,1,1,1,0,0,1,0,0,0,0,0,1},
                    {1,1,1,1,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,1,1,1,1},
                    {2,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,2},
                    {2,0,0,0,0,0,1,1,1,0,0,1,1,1,1,1,1,1,0,0,1,1,1,0,0,0,0,0,2},
                    {1,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,1},
                    {1,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,1},
                    {1,0,0,1,1,1,0,0,1,0,0,1,1,1,1,1,1,1,0,0,1,0,0,1,1,1,0,0,1},
                    {1,0,0,0,0,0,0,0,1,0,0,1,4,4,4,4,4,1,0,0,1,0,0,0,0,0,0,0,1},
                    {1,0,0,0,0,0,0,0,1,0,0,1,4,4,4,4,4,1,0,0,1,0,0,0,0,0,0,0,1},
                    {1,0,0,1,0,0,1,1,1,0,0,1,1,1,1,1,1,1,0,0,1,1,1,0,0,1,0,0,1},
                    {1,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,1},
                    {1,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,1},
                    {1,0,0,1,1,1,0,0,1,1,1,1,0,0,1,0,0,1,1,1,1,0,0,1,1,1,0,0,1},
                    {1,0,0,0,0,0,0,0,1,0,0,0,0,0,1,0,0,0,0,0,1,0,0,0,0,0,0,0,1},
                    {1,0,0,0,0,0,0,0,1,0,0,0,0,0,1,0,0,0,0,0,1,0,0,0,0,0,0,0,1},
                    {1,1,1,0,0,1,0,0,1,0,0,1,1,1,1,1,1,1,0,0,1,0,0,1,0,0,1,1,1},
                    {1,3,3,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,3,3,1},
                    {1,3,3,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,3,3,1},
                    {1,0,0,1,1,1,0,0,1,1,1,1,0,0,1,0,0,1,1,1,1,0,0,1,1,1,0,0,1},
                    {1,0,0,0,0,0,0,0,1,0,0,0,0,0,1,0,0,0,0,0,1,0,0,0,0,0,0,0,1},
                    {1,0,0,0,0,0,0,0,1,0,0,0,0,0,1,0,0,0,0,0,1,0,0,0,0,0,0,0,1},
                    {1,0,0,1,1,1,0,0,1,0,0,1,1,1,1,1,1,1,0,0,1,0,0,1,1,1,0,0,1},
                    {1,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,1},
                    {1,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,1},
                    {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
                    {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1}
                   };

    private static final int[][] maze4 = {
                    {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
                    {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
                    {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
                    {1,3,3,1,0,0,1,1,1,0,0,1,1,1,1,1,1,1,0,0,1,1,1,0,0,1,3,3,1},
                    {1,3,3,1,0,0,1,1,1,0,0,1,0,0,0,0,0,1,0,0,1,1,1,0,0,1,3,3,1},
                    {1,0,0,1,0,0,0,0,0,0,0,1,0,0,0,0,0,1,0,0,0,0,0,0,0,1,0,0,1},
                    {1,0,0,1,0,0,0,0,0,0,0,1,0,0,1,0,0,1,0,0,0,0,0,0,0,1,0,0,1},
                    {1,0,0,1,1,1,0,0,1,0,0,1,0,0,1,0,0,1,0,0,1,0,0,1,1,1,0,0,1},
                    {1,0,0,0,0,0,0,0,1,0,0,0,0,0,1,0,0,0,0,0,1,0,0,0,0,0,0,0,1},
                    {1,0,0,0,0,0,0,0,1,0,0,0,0,0,1,0,0,0,0,0,1,0,0,0,0,0,0,0,1},
                    {1,1,1,0,0,1,1,1,1,1,1,1,0,0,1,0,0,1,1,1,1,1,1,1,0,0,1,1,1},
                    {1,1,1,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,1,1,1},
                    {1,1,1,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,1,1,1},
                    {2,0,0,0,0,1,0,0,1,0,0,1,1,1,1,1,1,1,0,0,1,0,0,1,0,0,0,0,2},
                    {2,0,0,0,0,1,0,0,0,0,0,1,4,4,4,4,4,1,0,0,0,0,0,1,0,0,0,0,2},
                    {1,1,1,1,1,1,0,0,0,0,0,1,4,4,4,4,4,1,0,0,0,0,0,1,1,1,1,1,1},
                    {2,0,0,0,0,1,0,0,1,0,0,1,1,1,1,1,1,1,0,0,1,0,0,1,0,0,0,0,2},
                    {2,0,0,0,0,1,0,0,1,0,0,0,0,0,0,0,0,0,0,0,1,0,0,1,0,0,0,0,2},
                    {1,1,1,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,1,1,1},
                    {1,1,1,0,0,0,0,0,1,1,1,1,0,0,1,0,0,1,1,1,1,0,0,0,0,0,1,1,1},
                    {1,1,1,0,0,1,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,1,0,0,1,1,1},
                    {1,1,1,0,0,1,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,1,0,0,1,1,1},
                    {1,1,1,0,0,1,1,1,1,0,0,1,0,0,1,0,0,1,0,0,1,1,1,1,0,0,1,1,1},
                    {1,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,1},
                    {1,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,1},
                    {1,0,0,1,1,1,0,0,1,0,0,1,1,1,1,1,1,1,0,0,1,0,0,1,1,1,0,0,1},
                    {1,0,0,1,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,1,0,0,1},
                    {1,3,3,1,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,1,3,3,1},
                    {1,3,3,1,0,0,1,1,1,1,1,1,0,0,1,0,0,1,1,1,1,1,1,0,0,1,3,3,1},
                    {1,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
                    {1,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
                    {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
                    {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1}
                   };

    /**
     * Method that shows what is in the tiles from up, down, left and right, from the specified tile.<br>
     * It can show:<br>
     * <ul>
     *      <li>1 if there is a wall             </li>
     *      <li>2 if there is a warp space       </li>
     *      <li>3 if there is a power pill       </li>
     *      <li>4 if there is a ghost jaill space</li>
     * </ul>
     * @param x the x coordinate of the specified tile
     * @param y the y coordinate of the specified tile
     * @param maze the maze number, it can only be 1,2,3 or 4.
     * @throws java.lang.IllegalArgumentException if the maze number is not 1, 2, 3 or 4.
     * @return the list of the tiles (up,right,down,left)
     */
    public static int[] consultSurroundings(int xParam, int yParam, int maze) throws IllegalArgumentException{
        int[][] mazeMap;
        int[] surroundings = new int[4];
        switch(maze){
            case 1:{
                mazeMap=maze1;
                break;
            }
            case 2:{
                mazeMap=maze2;
                break;
            }
            case 3:{
                mazeMap=maze3;
                break;
            }
            case 4:{
                mazeMap=maze4;
                break;
            }
            default: {
                throw new IllegalArgumentException("maze can only take values from 1 to 4");
            }
        }
        //System.out.println("("+x+";"+y+")");
        MsPacmanTile upper=new MsPacmanTile();
        MsPacmanTile lower=new MsPacmanTile();
        MsPacmanTile left =new MsPacmanTile();
        MsPacmanTile right=new MsPacmanTile();
        MsPacmanTile tile=new MsPacmanTile();
        int x = xParam;
        int y = yParam - topBorder-2;
        //int xTile,yTile;
        
        MsPacmanTile tileCenter = pix2tile(x, y);

        //set the upper tile
        tile = pix2tile(x, y-(getPacSize()/2));
        
        if( (y-getPacSize()/2) < (tile.getY()*getRatio()-getRatio()/2)){//if the MsPacman excedes 50% of the tile
            upper.setX(tile.getX());//set that tile as the MsPacman's upper tile
            upper.setY(tile.getY());
        }else{
            upper.setX(tileCenter.getX());
            upper.setY(tileCenter.getY());
        }
//        System.out.println("arriba");
//        System.out.println(upper);

        //set the lower tile
        tile = pix2tile(x, y+(getPacSize()/2));
        if( (y+getPacSize()/2) > (tile.getY()*getRatio()+getRatio()/2)){//if the MsPacman excedes 50% of the tile
            lower.setX(tile.getX());//set that tile as the MsPacman's upper tile
            lower.setY(tile.getY());
        }else{
            lower.setX(tileCenter.getX());
            lower.setY(tileCenter.getY());
        }
//        System.out.println("abajo");
//        System.out.println(lower);
        
        //set the right tile
        tile = pix2tile(x+(getPacSize()/2), y);
        //System.out.println(xTile);
        if( (x-(getPacSize())/2) < (tile.getX()*getRatio()-getRatio()/2)){//if the MsPacman excedes 50% of the tile
            right.setX(tile.getX());//set that tile as the MsPacman's upper tile
            right.setY(tile.getY());
        }else{
            right.setX(tileCenter.getX());
            right.setY(tileCenter.getY());
        }
//        System.out.println("derecha");
//        System.out.println(right);

        
        //set the left tile
        tile = pix2tile(x-(getPacSize()/2), y);
        //System.out.println(xTile);
        if( (x+getPacSize()/2) > (tile.getX()*getRatio()+getRatio()/2)){//if the MsPacman excedes 50% of the tile
            left.setX(tile.getX());//set that tile as the MsPacman's upper tile
            left.setY(tile.getY());
        }else{
            left.setX(tileCenter.getX());
            left.setY(tileCenter.getY());
        }
//        System.out.println("izquierda");
//        System.out.println(left);
        
        //up, down, left and right
        surroundings[0] = mazeMap[upper.getY()+1 - 1][upper.getX()+1];
        surroundings[1] = mazeMap[lower.getY()+1 + 1][lower.getX()+1];
        surroundings[2] = mazeMap[left.getY()+1][left.getX()+1 - 1];
        surroundings[3] = mazeMap[right.getY()+1][right.getX()+1 + 1];
        
        /*System.out.println(mazeMap[yTileCenter-1][xTileCenter-1]+""+mazeMap[yTile-1][xTileCenter]+""+mazeMap[yTileCenter-1][xTileCenter+1]+""+mazeMap[yTileCenter-1][xTileCenter+2]);
        System.out.println(mazeMap[yTileCenter][xTileCenter-1]+""+mazeMap[yTileCenter][xTileCenter]+""+mazeMap[yTileCenter][xTileCenter+1]+""+mazeMap[yTileCenter][xTileCenter+2]);
        System.out.println(mazeMap[yTileCenter+1][xTileCenter-1]+""+mazeMap[yTileCenter+1][xTileCenter]+""+mazeMap[yTileCenter+1][xTile+1]+""+mazeMap[yTileCenter+1][xTileCenter+2]);
        System.out.println(mazeMap[yTileCenter+2][xTileCenter-1]+""+mazeMap[yTileCenter+2][xTileCenter]+""+mazeMap[yTileCenter+2][xTile+1]+""+mazeMap[yTileCenter+2][xTileCenter+2]);
        //System.out.println(mazeMap[yTileCenter+3][xTileCenter-1]+""+mazeMap[yTileCenter+3][xTileCenter]+""+mazeMap[yTileCenter+3][xTile+1]+""+mazeMap[yTileCenter+3][xTileCenter+2]);*/
//        System.out.println("centro");
//        System.out.println("\n position: ("+xTileCenter+";"+yTileCenter+")");
        
        return surroundings;
    }
    
    /**
     * 
     * @param x the x coordinate of the specified tile
     * @param y the y coordinate of the specified tile
     * @param maze the maze number, it can only be 1,2,3 or 4.
     * @throws java.lang.IllegalArgumentException if the maze number is not 1, 2, 3 or 4.
     * @return the list of the directions with possible movements (up,right,down,left  excluding the ones that are walls) <br>
     * the posible values are:<br>
     * <ul>
     *      <li>1 for up</li>
     *      <li>-1 for down</li>
     *      <li>2 for left</li>
     *      <li>-2 for right</li>
     *      <li>an empty list if there are not spaces to move to.</li>
     * </ul>
     */
    public static int[] consultPosibleMovements(int x, int y, int maze) throws IllegalArgumentException{
        int up=1;
        int down=-1;
        int left=2;
        int right=-2;

        int[] movements = new int[4];
        int[] posibleMovements;
        int[] surroundingTiles;
        int movementsCount=0;
        int j;

        surroundingTiles = consultSurroundings(x, y, maze);
        
        
        if( surroundingTiles[0] != 1 ){
            movements[0] = up;
            movementsCount++;
        }else{
            movements[0]=0;
        }
        
        if( surroundingTiles[1] != 1 ){
            movements[1] = down;
            movementsCount++;
        }else{
            movements[1]=0;
        }
        
        if( surroundingTiles[2] != 1 ){
            movements[2] = left;
            movementsCount++;
        }else{
            movements[2]=0;
        }
        
        if( surroundingTiles[3] != 1 ){
            movements[3] = right;
            movementsCount++;
        }else{
            movements[3]=0;
        }

        posibleMovements = new int[movementsCount];
        j=0;
        for(int i = 0; i < 4; i++){
            if(movements[i]!=0){
                posibleMovements[j] = movements[i];
                j++;
            }
        }

        //imprimir posibles movimientos
        /*for(int i=0; i<posibleMovements.length; i++){
        switch(posibleMovements[i]){
        case 1:{
        System.out.println("up");
        break;
        }
        case -1:{
        System.out.println("down");
        break;
        }
        case 2:{
        System.out.println("left");
        break;
        }
        case -2:{
        System.out.println("right");
        break;
        }
        default:{
        System.out.println("UPS");
        }
        }
        }
        //fin imprimir posibles movimientos*/
        return posibleMovements;
    }

    private static MsPacmanTile pix2tile(int x, int y){
        MsPacmanTile tile = new MsPacmanTile();
        tile.setX((int)((x - (getRatio() / 2f)) / getRatio()));
        tile.setY((int)((y - (getRatio() / 2f)) / getRatio()));
        
        return tile;
    }
    
    public static void main(String[] args) {
         setRatio(8);
        int [] posibleMovements = consultPosibleMovements(14, 213, 1);
    }

    /**
     * @return the ratio
     */
    public static int getRatio() {
        return ratio;
    }
    /**
     * sets the pixel ratio.
     * @param tile size in pixels.
     */
    public static void setRatio(int ratio) {
        MsPacmanMap.ratio = ratio;
    }

    /**
     * @return the PacSize
     */
    public static int getPacSize() {
        return PacSize;
    }

    /**
     * @param aPacSize the PacSize to set
     */
    public static void setPacSize(int aPacSize) {
        PacSize = aPacSize;
    }

    /**
     * @return the topBorder
     */
    public static int getTopBorder() {
        return topBorder;
    }

    /**
     * @param aTopBorder the topBorder to set
     */
    public static void setTopBorder(int aTopBorder) {
        topBorder = aTopBorder;
    }
}
