package logica;

/**
 * FECHA ==> 2019-09-16.
 * Representa una palabra almacenada en el sistema.
 * @author Luis Alejandro Gómez C.
 * @author Ever Peña B.
 * @version 1.0.0
 */
public class Palabra 
{
    /**
     * Número único que identifica la palabra en la base de datos.
     */
    private int id;
    
    /**
     * Término al que hace refenrencia la palabara.
     */
    private String termino;

    /**
     * Permite crear una palabra con todos sus datos.
     * @param id Número único de la palabra.
     * @param termino Termino al que hace alución la palabra.
     */
    public Palabra(int id, String termino) 
    {
        this.id = id;
        this.termino = termino;
    }
        
    /**
     * Obtiene el identificador de la palabra.
     * @return Identificador de la palabra en la base de datos.
     */
    public int getId() 
    {
        return id;
    }

    /**
     * Obtiene el termino al que hace referencia la palabra.
     * @return Término al que hace referencia la palabra.
     */
    public String getTermino() 
    {
        return termino;
    }
    
    
    
    
}
