package bd;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import logica.Palabra;

/**
 * FECHA ==> 2019-09-16.
 * Permite realizar las consultas en la base de datos con la tabla 'palabra'.
 * @author Luis Alejandro Gómez C.
 * @author Ever Peña B.
 * @version 1.0.0
 */
public class CrudPalabra extends Crud
{
    /**
     * Inicialiaza la posibilidad de  realizar consultas con la tabla "palabra".
     * @param conexion Conexión con la base de datos.
     */
    public CrudPalabra(Conexion conexion) 
    {
        super(conexion);
    }
    
    /**
     * Crea una nueva palabra para el sistema.
     * @param palabra Palabra que se desea agregar al sistema.
     * @return True==>Se Creó, False=>No se pudo crear.
     */
    public boolean crearPalabra(Palabra palabra)
    {
        if(palabra == null)
            return false;
        
        boolean bandera = true;           
        String consulta = "INSERT INTO palabra (termino)"
                + " VALUES (?)";
        try 
        {
            PreparedStatement ps = this.conexion.getConexion().prepareStatement(consulta);
            ps.setString(1, palabra.getTermino());
            ps.executeUpdate();
        } 
        catch (SQLException e) 
        {
            bandera = false;
        }                                
        
        return bandera; 
    }
    
    /**
     * Permite obtener los datos de una palabra a partir de su id.
     * @param id Identificador de la palabra. -1: si se desea buscar por término.
     * @param te Término al que hace referencia la palabra.
     * @return null=>No lo encontró, Palabra=>Lo encontró.
     */
    public Palabra leerPalabra(int id, String te)
    {        
        String consulta = "";
        
        Palabra palabra = null;
        String termino;
        int ident;
        
        if(id > 0)
            consulta = "SELECT * FROM palabra WHERE id = ?";
        else
            consulta = "SELECT * FROM palabra WHERE termino = ?";
        
        try 
        {
            PreparedStatement ps = this.conexion.getConexion().prepareStatement(consulta);
            if(id > 0)
                ps.setInt(1, id);
            else
                ps.setString(1, te);
            
            ResultSet rs = ps.executeQuery();
            while(rs.next())
            {
                
                termino = rs.getString("termino");
                ident = rs.getInt("id");
                palabra = new Palabra(ident, termino);
                break;
            }
        }
        catch (SQLException e) 
        {
            return null;
        }
        
        return palabra;
    }
    
    /**
     * Permite modificar una palabra registrada en la base de datos.
     * @param palabra1 Palabra que se desea modificar.
     * @param palabra2 Palabra que reemplazará el modificable.
     * @return True=>Se modificó, False=>No se pudó modificar.
     */
    public boolean modificarPalabra(Palabra palabra1, Palabra palabra2)
    {
        if(palabra1 == null || palabra2 == null)
            return false;
        
        boolean bandera = true;
        String consulta = "UPDATE palabra SET termino = ? WHERE id = ?";
        try 
        {
            PreparedStatement ps = this.conexion.getConexion().prepareStatement(consulta);
            ps.setString(1, palabra2.getTermino());
            ps.setInt(2, palabra1.getId());
            ps.executeUpdate(); 
        } catch (SQLException e) 
        {
            return false;
        }
        
        return bandera;
    }    
    
    
    public boolean eliminarPalabra(Palabra palabra)
    {
        if(palabra == null)
            return false;

        boolean bandera = true;
        String cadenaPalabra = palabra.getTermino();
        
        //System.out.println("LA INFOOO: " + idPalabra1 + "," + idPalabra2 + "," + isoPalabra1 + "," + isoPalabra2);
        
        String consulta = "DELETE FROM palabra WHERE termino = ? ";
        try 
        {
            PreparedStatement ps = this.conexion.getConexion().prepareStatement(consulta);
            ps.setString(1,  cadenaPalabra);
            
            ps.executeUpdate(); 
        } catch (SQLException e) 
        {
            return false;
        }
        
        return bandera;
    }
    
    /**
     * Obtiene todas las palabras registradas en el sistema.
     * @return Lista de palabras registradas en el sistema.
     */
    public ArrayList<Palabra> obtenerTodasPalabras()
    {
        ArrayList<Palabra> palabras = new ArrayList<>();
        
        String consulta = "SELECT * FROM palabra";
        Palabra palabra = null;
        int id;
        String termino;
        
        try 
        {
            ResultSet rs = this.conexion.getStatement().executeQuery(consulta);
            while(rs.next())
            {
                id = rs.getInt("id");
                termino = rs.getString("termino");
                
                palabra = new Palabra(id, termino);
                palabras.add(palabra);                
            }
            
        } catch (SQLException e) 
        {
            return null;
        }
        
        return palabras;
    }
    
    
}
