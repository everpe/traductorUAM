package logica;

/**
 * FECHA ==> 2019-09-16.
 * Representa un idioma registrado en el sistema.
 * @author Luis Alejandro Gómez C.
 * @author Ever Peña B.
 * @version 1.0.0
 */
public class Idioma 
{
    /**
     * Código ISO 639-1 con el cual se identifica el idioma.
     */
    private String iso;
    
    /**
     * Nombre del idioma.
     */
    private String nombre;

    /**
     * Crea un idioma con todos los datos de información.
     * @param iso Código ISO del idioma.
     * @param nombre Nombre del idioma.
     */
    public Idioma(String iso, String nombre) 
    {
        this.iso = iso;
        this.nombre = nombre;
    }
        
    /**
     * Obtiene el código ISO con el cuál se identifica un idioma.
     * @return Código ISO con el que se identifica un idioma.
     */    
    public String getIso() 
    {
        return iso;
    }

    /**
     * Obtiene el nombre del idioma.
     * @return Nombre del idioma.
     */
    public String getNombre() 
    {
        return nombre;
    }

    public void setIso(String iso) {
        this.iso = iso;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    
    
    
    
    
    
}
