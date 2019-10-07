package bd;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import logica.Idioma;

/**
 * FECHA ==> 2019-09-16.
 * Permite realizar las consultas en la base de datos con la tabla 'idioma'.
 * @author Luis Alejandro Gómez C.
 * @author Ever Peña B.
 * @version 1.0.0
 */
public class CrudIdioma extends Crud
{
    /**
     * Inicialiaza la posibilidad de  realizar consultas con la tabla "idioma".
     * @param conexion Conexión con la base de datos.
     */
    public CrudIdioma(Conexion conexion) 
    {
        super(conexion);
    }
    
    /**
     * Crea un nuevo idioma para el sistema.
     * @param idioma Idioma que se desea agregar al sistema.
     * @return True==>Se Creó, False=>No se pudo crear.
     */
    public boolean crearIdioma(Idioma idioma)
    {
        if(idioma == null)
            return false;
        
        boolean bandera = true;             
        
        String consulta = "INSERT INTO idioma (iso, nombre)"
                + " VALUES (?, ?)";
        try 
        {
            PreparedStatement ps = this.conexion.getConexion().prepareStatement(consulta);
            ps.setString(1, idioma.getIso());
            ps.setString(2, idioma.getNombre());
            ps.executeUpdate();
        } 
        catch (SQLException e) 
        {
            bandera = false;
        }                                
        
        return bandera; 
    }
    
    /**
     * Permite obtener los datos de un idioma a partir de su código ISO.
     * @param iso Código ISO del idioma.
     * @return null=>No lo encontró, Idioma=>Lo encontró.
     */
    public Idioma leerIdioma(String iso)
    {
        if(iso == null || iso.equals(""))
            return null;
        
        Idioma idioma = null;
        String nombre;
        
        String consulta = "SELECT * FROM idioma WHERE iso = ?";
        try 
        {
            PreparedStatement ps = this.conexion.getConexion().prepareStatement(consulta);
            ps.setString(1, iso);
            
            ResultSet rs = ps.executeQuery();
            while(rs.next())
            {
                nombre = rs.getString("nombre");
                idioma = new Idioma(iso, nombre);
                break;
            }
        }
        catch (SQLException e) 
        {
            return null;
        }
        
        return idioma;
    }
    
    /**
     * Permite modificar un idioma registrado en la base de datos.
     * @param idioma1 Idioma que se desea modificar.
     * @param idioma2 Idioma que reemplazará el modificable.
     * @return True=>Se modificó, False=>No se pudó modificar.
     */
    public boolean modificarIdioma(Idioma idioma1, Idioma idioma2)
    {
        if(idioma1 == null || idioma2 == null)
            return false;
        
        boolean bandera = true;
        String consulta = "UPDATE idioma SET iso = ?, nombre = ? WHERE iso = ? ";
        try 
        {
            PreparedStatement ps = this.conexion.getConexion().prepareStatement(consulta);
            ps.setString(1, idioma2.getIso());
            ps.setString(2, idioma2.getNombre());
            ps.setString(3, idioma1.getIso());
            ps.executeUpdate(); 
        } catch (SQLException e) 
        {
            return false;
        }
        
        return bandera;
    }
    
    
    /**
     * Obtiene todos los idiomas registrados en el sistema.
     * @return Lista de idiomas registrados en el sistema.
     */
    public ArrayList<Idioma> obtenerTodosIdiomas()
    {
        ArrayList<Idioma> idiomas = new ArrayList<>();
        
        String consulta = "SELECT * FROM idioma";
        Idioma idioma = null;
        String iso;
        String nombre;
        
        try 
        {
            ResultSet rs = this.conexion.getStatement().executeQuery(consulta);
            while(rs.next())
            {
                iso = rs.getString("iso");
                nombre = rs.getString("nombre");
                
                idioma = new Idioma(iso, nombre);
                idiomas.add(idioma);                
            }
            
        } catch (SQLException e) 
        {
            return null;
        }
        
        return idiomas;
    }
    
    
}
