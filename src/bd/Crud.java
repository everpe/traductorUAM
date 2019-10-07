package bd;

/**
 * FECHA ==> 2019-09-16.
 * Permite instanciar todos los cruds de la base de datos con la conexión.
 * @author Luis Alejandro Gómez C.
 * @author Ever Peña B.
 * @version 1.0.0
 */
public abstract class Crud 
{
    /**
     * Conexión con la base de datos.
     */
    protected Conexion conexion;

    /**
     * Inicialiaza las caracteristicas de un CRUD en la base de datos.
     * @param conexion Conexión con la base de datos.
     */
    public Crud(Conexion conexion) 
    {
        this.conexion = conexion;
    }        
}
