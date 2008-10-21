/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pacman.fieldsagent;

/** Representa un vector en dos dimensiones
 *
 *  @author Sergio Bobillier Ceballos
 */
public class Vector2D
{
    /** Coordenada x del vector */
    private float x;
    
    /** Cordenada y del vector */
    private float y;
    
    /** Constructora sin parámetros. Crea el vector (0, 0) */
    public Vector2D()
    {
        this.x = 0;
        this.y = 0;
    }
    
    /** Crea un vector con las coordenadas especificadas
     * 
     *  @param x La coordenada x del vector
     *  @param y La coordenada y del vector
     */
    
    public Vector2D(float x, float y)
    {
        this.x = x;
        this.y = y;
    }

    /** Calcula la suma vectorial entre este vector y el vector especificado.
     * 
     * @param vector El vector con el que se desea sumar.
     * @return El vector resultante.
     */
    
    public Vector2D add(Vector2D vector)
    {
        Vector2D result;
        result = new Vector2D();
        result.setX(this.x + vector.x);
        result.setY(this.y + vector.y);
        return result;
    }
    
    /** Devuelve verdadero si el vector es el vector (0,0), falso en cualquier
     *  otro caso.
     * 
     *  @return Verdadero si se trata del vector (0,0) falso de lo contrario.
     */
    
    public boolean isZeroVector()
    {
        if(this.x == 0.0f && this.y == 0.0f)
            return true;
        
        return false;
    }
    
    /** Devuelve el módulo del vector
     * 
     *  @return Un valor flotante, el módulo del vector.
     */
    
    public float getModule()
    {
        float module;
        module = (float)Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
        return module;
    }
        
    /** Devuelve la coordenada x del vector.
     * 
     *  @return La coordenada x del vector.
     */
    
    public float getX() { return x; }
    
    /** Devuelve la coordenada y del vector.
     * 
     * @return. La coordenada y del vector.
     */
    
    public float getY() { return y; }
    
    /** Devuelve una representación del vector en forma de cadena de caracteres
     * 
     *  @return La representación del vector.
     */
    
    @Override
    public String toString()
    {
        StringBuffer string;
        string = new StringBuffer();
        string.append("(");
        string.append(String.valueOf(x));
        string.append(", ");
        string.append(String.valueOf(y));
        string.append(")");
        
        return string.toString();
    }

    /** Devuelve el vector unitario que tiene la misma dirección que este vector.
     * 
     *  @return Un vector que representa el vector unitario con la misma
     *      dirección de este vector.
     */
    
    public Vector2D toUnitVector()
    {
        Vector2D unitVector;
        float module;
        
        module = this.getModule();
        
        unitVector = new Vector2D();
        unitVector.setX(this.x/module);
        unitVector.setY(this.y/module);

        return unitVector;
    }
    
    /** Devuelve el vector resultante de multiplicar este vector por un
     *  escalar.
     * 
     * @param f El escalar por el cual se quiere multiplicar el vector.
     * @return El vector resultante.
     */
    
    public Vector2D scalarMultiply(float f)
    {
        Vector2D result;
        result = new Vector2D();
        result.setX(this.x * f);
        result.setY(this.y * f);
        return result;
    }
    
    /** Establece la coordenada x del vector.
     * 
     *  @param x La coordenada x del vector.
     */
    
    public void setX(float x) { this.x = x; }
    
    /** Establece la coordenada y del vector.
     * 
     *  @param y La coordenada y del vector.
     */
    
    public void setY(float y) { this.y = y; }

}
