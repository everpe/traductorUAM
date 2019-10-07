package bd;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import logica.Palabra;
import logica.Termino;

/**
 * FECHA ==> 2019-09-16.
 * Permite realizar las consultas en la base de datos con la tabla 'palabra_idioma'.
 * Se le denomina 'Termino' a los objetos que hay en esta tabla.
 * @author Luis Alejandro Gómez C.
 * @author Ever Peña B.
 * @version 1.0.0
 */
public class CrudTermino extends Crud
{
    /**
     * Crud con la tabla de palabras.
     */
    private CrudPalabra crudPalabra;
    
    /**
     * Inicialiaza la posibilidad de  realizar consultas con la tabla "palabra_idioma".
     * @param conexion Conexión con la base de datos.
     */
    public CrudTermino(Conexion conexion) 
    {
        super(conexion);
        this.crudPalabra = new CrudPalabra(conexion);
    }
    
    /**
     * Crea un nuevo término para el sistema.
     * @param termino Término que se desea agregar al sistema.
     * @return True==>Se Creó, False=>No se pudo crear.
     */
    public boolean crearTermino(Termino termino)
    {
        if(termino == null)
            return false;
        
        boolean bandera = true;           
        String consulta = "INSERT INTO palabra_idioma (id_palabra, iso_idioma)"
                + " VALUES (?, ?)";
        try 
        {
            PreparedStatement ps = this.conexion.getConexion().prepareStatement(consulta);
            ps.setInt(1, termino.getPalabra().getId());
            ps.setString(2, termino.getIdioma().getIso());
            ps.executeUpdate();
        } 
        catch (SQLException e) 
        {
            bandera = false;
            System.out.println("ERRROOOORR "+ e.getMessage());
        }                                
        
        return bandera; 
    }
    
    /**
     * Busca en la base de datos la existencia de un término.
     * @param termino Término que se esta buscando en la base de datos.
     * @return True=>Encontró, False=No encontró.
     */
    public boolean existeTermino(Termino termino)
    {        
        String consulta = "";
        int idPal = termino.getPalabra().getId();
        String isoPal = termino.getIdioma().getIso();
        
        boolean bandera = false;     
        consulta = "SELECT * FROM palabra_idioma WHERE id_palabra = ? AND iso_idioma = ?";
        
        try 
        {
            PreparedStatement ps = this.conexion.getConexion().prepareStatement(consulta);
            ps.setInt(1, idPal);
            ps.setString(2, isoPal);
            
            ResultSet rs = ps.executeQuery();
            while(rs.next())
            {
                
                bandera = true;
                break;
            }
        }
        catch (SQLException e) 
        {
            return false;
        }
        
        return bandera;
    }
    
    
    public boolean eliminarTermino(Termino termino)
    {
        if(termino == null)
            return false;

        boolean bandera = true;
        String cadenaPalabra = termino.getPalabra().getTermino();
        Palabra pal1 = this.crudPalabra.leerPalabra(-1, cadenaPalabra);
        
        String isoPalabra1 = termino.getIdioma().getIso(); 
        //System.out.println("LA INFOOO: " + idPalabra1 + "," + idPalabra2 + "," + isoPalabra1 + "," + isoPalabra2);
        
        String consulta = "DELETE FROM palabra_idioma WHERE id_palabra = ? "
                + "AND iso_idioma = ? ";
        try 
        {
            PreparedStatement ps = this.conexion.getConexion().prepareStatement(consulta);
            ps.setInt(1,  pal1.getId());
            ps.setString(2,  isoPalabra1);
            
            ps.executeUpdate(); 
            
            this.crudPalabra.eliminarPalabra(pal1);
            
        } catch (SQLException e) 
        {
            return false;
        }
        
        return bandera;
    }
    

}
