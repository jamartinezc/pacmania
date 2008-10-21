/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pacman.fieldsagent;

/**
 *
 * @author jaguar
 */
public class MsPacmanTile {
    private int x;
    private int y;

    /**
     * @return the x
     */
    public int getX() {
        return x;
    }

    /**
     * @param x the x to set
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * @return the y
     */
    public int getY() {
        return y;
    }

    /**
     * @param y the y to set
     */
    public void setY(int y) {
        this.y = y;
    }

    public String toString(){
        return "("+x+";"+y+")";
    }

}
