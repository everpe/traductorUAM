package logica;

import bd.BaseDatos;
import controladores.ControladorTabla;
import controladores.ControladorTraduccion;

/**
 * FECHA ==> 2019-09-16.
 * Permite gestionar la mayoria del sistema de traducción.
 * @author Luis Alejandro Gómez C.
 * @author Ever Peña B.
 * @version 1.0.0
 */
public class Sistema 
{
    /**
     * Base de datos del sistema.
     */
    private BaseDatos bd;
    
    private final String SUB_RED = "192.168.0";
    
    /**
     * Controlador de las gestiones de traducción del sistema.
     */
    private ControladorTraduccion controladorTrad;
    
    /**
     * Controlador para las gestiones de las tablas del sistema.
     */
    private ControladorTabla controladorTabla;

    /**
     * Permite la gestión de la mayoria de funcionalidades del sistema.
     * @param bd Base de datos del sistema.
     */
    public Sistema(BaseDatos bd) 
    {
        this.bd = bd;
        IPScanner sc = new IPScanner();
        this.controladorTrad = new ControladorTraduccion(bd,  sc);
        sc.getNetworkIPs(SUB_RED);        
        this.controladorTabla = new ControladorTabla();
    }

    /**
     * Controla las funcionalidades de traducción del sistema.
     * @return Controladora de traducciones.
     */
    public ControladorTraduccion getControladorTrad() 
    {
        return controladorTrad;
    }

    public ControladorTabla getControladorTabla() 
    {
        return controladorTabla;
    }    

    public BaseDatos getBd() {
        return bd;
    }
        
    
    
}
