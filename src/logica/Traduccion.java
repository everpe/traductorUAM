package logica;

/**
 * FECHA ==> 2019-09-16.
 * Simboliza la traducción entre términos registrados en el sistema.
 * @author Luis Alejandro Gómez C.
 * @author Ever Peña B.
 * @version 1.0.0
 */
public class Traduccion 
{
    /**
     * Término #1 involucrado en la traducción.
     */
    private Termino termino1;
    
    /**
     * Término #2 involucrado en la traducción.
     */
    private Termino termino2;

    /**
     * Crea una traducción entre dos términos.
     * @param termino1 Término #1.
     * @param termino2 Término #2. difertente idioma.
     */
    public Traduccion(Termino termino1, Termino termino2) 
    {
        this.termino1 = termino1;
        this.termino2 = termino2;
    }

    /**
     * Obtiene el primer término de la traducción.
     * @return Primer término de la traducción.
     */
    public Termino getTermino1() 
    {
        return termino1;
    }

    /**
     * Obtiene el segundo término de la traducción.
     * @return Segundo término de la traducción.
     */
    public Termino getTermino2() 
    {
        return termino2;
    }
    
    
    
    
}
