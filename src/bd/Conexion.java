package bd;

import java.sql.*;

/**
 * FECHA ==> 2019-09-16.
 * Carga el driver SQLITE, y realiza la conexión con la base de datos del sistema.
 * @author Luis Alejandro Gómez C.
 * @author Ever Peña B.
 * @version 1.0.0
 */
public class Conexion 
{
    /**
     * Objeto que representa la conexión con la base de datos del sistema.
     */
    private Connection conexion;
    
    /**
     * Permite ejecutar consultas sobre la base de datos.
     */
    private Statement statement;
    
    /**
     * Comprueba el driver que comunica a JAVA con SQLITE y si conecta 
     * correctamente carga la base de datos destinada para el sistema.
     */    
    public Conexion(int id)
    {
        //Comprobando el driver de conexión con la base de datos.
        try 
        {
            Class.forName("org.sqlite.JDBC");
        } 
        catch (ClassNotFoundException e) 
        {
            System.err.println("ERROR: no fue posible cargar el driver JDBC");
            System.exit(1);
        }
        
        //Conectando con la base de datos del sistema.
        try 
        {
            if(id == 1)
                this.conexion = DriverManager.getConnection("jdbc:sqlite:bd/base_datos.db");
            if(id == 2)
                this.conexion = DriverManager.getConnection("jdbc:sqlite:udp/base_datos.db");
            this.statement = this.conexion.createStatement();     
            //System.out.println("Conexión correcta a la base de datos");
        } 
        catch (SQLException e) 
        {
            System.err.println("ERROR ejecutando las consultas SQL: " + e.getMessage());
        }        
    }

    /**
     * Obtiene el objeto que representa la conexión con la base de datos.
     * @return Conexión con la base de datos.
     */
    public Connection getConexion() 
    {
        return conexion;
    }

    /**
     * Obtiene el objeto sobre el que se ejecutan las consultas en la base de datos.
     * @return Objeto para ejecutar las cosultas en base de datos.
     */
    public Statement getStatement() 
    {
        return statement;
    }
}
