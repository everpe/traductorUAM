package bd;

/**
 * FECHA ==> 2019-09-11.
 * Posee todos los CRUD con la base de datos.
 * @author Luis Alejandro Gómez C.
 * @author Ever Peña B.
 * @version 1.0.0
 */
public class BaseDatos 
{
    /**
     * Conexión con la base de datos.
     */
    private Conexion conexion;
    
    /**
     * Crud de la tabla 'palabra'.
     */
    private CrudPalabra crudPalabra;    
    
    /**
     * Crud de la tabla 'palabra_idioma'.
     */
    private CrudTermino crudtermino;
    
    /**
     * Crud de la tabla 'palabra_traducida'.
     */
    private CrudTraduccion crudTraduccion;
   
    private CrudIdioma crudIdioma;
    
    /**
     * realiza la conexión Prepara todos los cruds con la base de datos.
     * @param conexion Conexión con la base de datos.
     */
    public BaseDatos(Conexion conexion) 
    {
        this.conexion = conexion;
        this.crudPalabra = new CrudPalabra(conexion);
        this.crudtermino = new CrudTermino(conexion);
        this.crudTraduccion = new CrudTraduccion(conexion);
        this.crudIdioma= new CrudIdioma(conexion);
    }

    /**
     * Obtiene el CRUD con la tabla 'palabra' de la base de datos.
     * @return Operabilidad con la tabla 'palabra' de la base de datos.
     */
    public CrudPalabra getCrudPalabra() 
    {
        return crudPalabra;
    }

    /**
     * Obtiene el CRUD con la tabla 'palabra_idioma' de la base de datos.
     * @return Operabilidad con la tabla 'palabra_idioma' de la base de datos.
     */
    public CrudTermino getCrudtermino() 
    {
        return crudtermino;
    }

    /**
     * Obtiene el CRUD con la tabla 'palabra_traducida' de la base de datos.
     * @return Operabilidad con la tabla 'palabra_traducida' de la base de datos.
     */
    public CrudTraduccion getCrudTraduccion() 
    {
        return crudTraduccion;
    }
            
    /**
     * Obtiene la conexión con la base de datos del sistema.
     * @return Conexión con la base de datos.
     */
    public Conexion getConexion() 
    {
        return conexion;
    }           

    public CrudIdioma getCrudIdioma() {
        return crudIdioma;
    }

    public void setCrudIdioma(CrudIdioma crudIdioma) {
        this.crudIdioma = crudIdioma;
    }
    
    
}
