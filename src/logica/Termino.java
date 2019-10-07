package logica;

/**
 * FECHA ==> 2019-09-16.
 * Representa un término con su debido idioma.
 * ['amor' == 'es'], ['hello' == 'en']...
 * @author Luis Alejandro Gómez C.
 * @author Ever Peña B.
 * @version 1.0.0
 */
public class Termino 
{
    /**
     * Palabra que contiene el término.
     */
    private Palabra palabra;
    
    /**
     * Idioma en el cual se encuentra identificadá la palabra.
     */
    private Idioma idioma;

    /**
     * Crea un término con todos los datos necesarios.
     * @param palabra Palabra involucrada en el término.
     * @param idioma Idioma que identifica al término.
     */
    public Termino(Palabra palabra, Idioma idioma) 
    {
        this.palabra = palabra;
        this.idioma = idioma;
    }      

    /**
     * Obtiene la palabra asociada a un término.
     * @return Palabra asociada al término.
     */
    public Palabra getPalabra() 
    {
        return palabra;
    }

    /**
     * Obtiene el idioma al que pertenece el término.
     * @return Idioma al que pertenece el término.
     */
    public Idioma getIdioma() 
    {
        return idioma;
    }

    /**
     * Modifica la palabra que tiene el término.
     * @param palabra Palabra por la cúal se va modificar.
     */
    public void setPalabra(Palabra palabra) 
    {
        this.palabra = palabra;
    }
    
    
    
    
    
}
