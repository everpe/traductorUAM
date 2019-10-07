package bd;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import logica.Palabra;
import logica.Traduccion;

/**
 * FECHA ==> 2019-09-16.
 * Permite realizar las consultas en la base de datos con la tabla 'palabra_traducida'.
 * @author Luis Alejandro Gómez C.
 * @author Ever Peña B.
 * @version 1.0.0
 */
public class CrudTraduccion  extends Crud
{
    /**
     * Crud con la tabla de palabras.
     */
    private CrudPalabra crudPalabra;
    
    /**
     * Inicialiaza la posibilidad de  realizar consultas con la tabla "palabra_traducida".
     * @param conexion Conexión con la base de datos.
     */
    public CrudTraduccion(Conexion conexion) 
    {
        super(conexion);
        this.crudPalabra = new CrudPalabra(conexion);
    }
    
    /**
     * Crea una traducción de una palabra entre dos idiomas.
     * @param traduccion Traducción que se desea registrar.
     * @return True=>Se registró la traducción, False=>no se registró.
     */
    public boolean crearTraduccion(Traduccion traduccion)
    {
        if(traduccion == null)
            return false;
        
        boolean bandera = true;    
        int idPalabra1 = traduccion.getTermino1().getPalabra().getId();
        int idPalabra2 = traduccion.getTermino2().getPalabra().getId();
        String isoPalabra1 = traduccion.getTermino1().getIdioma().getIso();
        String isoPalabra2 = traduccion.getTermino2().getIdioma().getIso();
        
        String consulta = "INSERT INTO palabra_traducida (id_palabra_idioma_traduce1,"
                + " id_palabra_idioma_traduce2, iso_palabra_idioma_traduce1, iso_palabra_idioma_traduce2)"
                + " VALUES (?, ?, ?, ?)";
        try 
        {
            PreparedStatement ps = this.conexion.getConexion().prepareStatement(consulta);
            ps.setInt(1, idPalabra1);
            ps.setInt(2, idPalabra2);
            ps.setString(3, isoPalabra1);
            ps.setString(4, isoPalabra2);
            
            ps.executeUpdate();
        } 
        catch (SQLException e) 
        {
            bandera = false;
        }                                
        
        return bandera; 
    }
    
    /**
     * Actualiza la información de una traducción registrada en la base de datos.
     * @param oldTrad Traducción que se desea modificar.
     * @param newTrad Traducción que modificará la anterior.
     * @return True=>Se actualizó, False=>No se actualizó.
     */
    public boolean actualizarTraduccion(Traduccion oldTrad, Traduccion newTrad)
    {
        if(oldTrad == null || newTrad == null)
            return false;
        
        boolean bandera = true;
        int id1;
        int id2;
        //Información de la primera traducción (vieja).
        String idPalabra1 = oldTrad.getTermino1().getPalabra().getTermino();
        String idPalabra2 = oldTrad.getTermino2().getPalabra().getTermino();
        
        Palabra pal1 = this.crudPalabra.leerPalabra(-1, idPalabra1);
        if(pal1 != null)
            id1 = (this.crudPalabra.leerPalabra(-1, idPalabra1)).getId();
        else
        {
            this.crudPalabra.crearPalabra(new Palabra(-1, idPalabra1));
            id1 = (this.crudPalabra.leerPalabra(-1, idPalabra1)).getId();
        }
        
        Palabra pal2 = this.crudPalabra.leerPalabra(-1, idPalabra2);
        if(pal2 != null)
            id2 = (this.crudPalabra.leerPalabra(-1, idPalabra2)).getId();
        else
        {
            this.crudPalabra.crearPalabra(new Palabra(-1, idPalabra2));
            id2 = (this.crudPalabra.leerPalabra(-1, idPalabra2)).getId();
        }

        String isoPalabra1 = oldTrad.getTermino1().getIdioma().getIso();
        String isoPalabra2 = oldTrad.getTermino2().getIdioma().getIso();
        
        
        //Información de la segunda traducción.
        int id11;
        int id22;
        String idPalabra11 = newTrad.getTermino1().getPalabra().getTermino();
        String idPalabra22 = newTrad.getTermino2().getPalabra().getTermino();
        
        Palabra pal11 = this.crudPalabra.leerPalabra(-1, idPalabra11);
        if(pal11 != null)
            id11 = (this.crudPalabra.leerPalabra(-1, idPalabra11)).getId();
        else
        {
            this.crudPalabra.crearPalabra(new Palabra(-1, idPalabra11));
            id11 = (this.crudPalabra.leerPalabra(-1, idPalabra11)).getId();
        }
        
        Palabra pal22 = this.crudPalabra.leerPalabra(-1, idPalabra22);
        if(pal22 != null)
            id22 = (this.crudPalabra.leerPalabra(-1, idPalabra22)).getId();
        else
        {
            this.crudPalabra.crearPalabra(new Palabra(-1, idPalabra22));
            id22 = (this.crudPalabra.leerPalabra(-1, idPalabra22)).getId();
        }
        
        String isoPalabra11 = newTrad.getTermino1().getIdioma().getIso();
        String isoPalabra22 = newTrad.getTermino2().getIdioma().getIso();
        
        
        String consulta = "UPDATE palabra_traducida SET id_palabra_idioma_traduce1 = ?, "
            + "id_palabra_idioma_traduce2 = ?, iso_palabra_idioma_traduce1 = ?,"
            + "iso_palabra_idioma_traduce2 = ? WHERE id_palabra_idioma_traduce1 = ? "
            + " AND id_palabra_idioma_traduce2 = ? AND iso_palabra_idioma_traduce1 = ? "
            + " AND iso_palabra_idioma_traduce2 = ?";
        
        
        try 
        {
            PreparedStatement ps = this.conexion.getConexion().prepareStatement(consulta);
            ps.setInt(1, id11);
            ps.setInt(2, id22);
            ps.setString(3, isoPalabra11);
            ps.setString(4, isoPalabra22);
            
            ps.setInt(5, id1);
            ps.setInt(6, id2);
            ps.setString(7, isoPalabra1);
            ps.setString(8, isoPalabra2);
            
            ps.executeUpdate(); 
            
            PreparedStatement ps2 = this.conexion.getConexion().prepareStatement(consulta);
            ps2.setInt(1, id22);
            ps2.setInt(2, id11);
            ps2.setString(3, isoPalabra22);
            ps2.setString(4, isoPalabra11);
            
            ps2.setInt(5, id2);
            ps2.setInt(6, id1);
            ps2.setString(7, isoPalabra2);
            ps2.setString(8, isoPalabra1);
            
            ps2.executeUpdate();            
                        
        } catch (SQLException e) 
        {
            return false;
        }
        
        return bandera;
    }
    
    /**
     * Obtiene todas las traducciones registradas en la base de datos.
     * @return Lista con la información de las traducciones de la base de datos.
     */
    public ArrayList<String[]> obtenerTodasTraducciones()
    {
        ArrayList<String[]> traducciones = new ArrayList<>();
        String consulta = "SELECT * FROM palabra_traducida";
        String or;
        String de;
        String is_or;
        String is_de;
        
        try 
        {
            PreparedStatement ps = this.conexion.getConexion().prepareStatement(consulta);
            ResultSet rs = ps.executeQuery();
            while(rs.next())
            {
                or = (rs.getInt("id_palabra_idioma_traduce1")) + "";
                de = (rs.getInt("id_palabra_idioma_traduce2")) + "";
                is_or = rs.getString("iso_palabra_idioma_traduce1");
                is_de = rs.getString("iso_palabra_idioma_traduce2");
                String[] info = {or, de, is_or, is_de};
                traducciones.add(info);
            }
        } 
        catch (SQLException e) 
        {
            return null;
        }     
        
        return traducciones;
    }
    
    /**
     * Busca en la base de datos la existencia de una traducción.
     * @param traduccion Traducción que se esta buscando en la base de datos.
     * @return True=>Encontró, False=No encontró.
     */
    public boolean existeTermino(Traduccion traduccion)
    {        
        String consulta = "";
        int idPal1 = traduccion.getTermino1().getPalabra().getId();
        int idPal2 = traduccion.getTermino2().getPalabra().getId();
        String iso1 = traduccion.getTermino1().getIdioma().getIso();
        String iso2 = traduccion.getTermino2().getIdioma().getIso();
        
        boolean bandera = false;     
        consulta = "SELECT * FROM palabra_traducida WHERE id_palabra_idioma_traduce1 = ?"
                + " AND id_palabra_idioma_traduce2 = ? AND iso_palabra_idioma_traduce1 = ?"
                + " AND iso_palabra_idioma_traduce2 = ?";
        
        try 
        {
            PreparedStatement ps = this.conexion.getConexion().prepareStatement(consulta);
            ps.setInt(1, idPal1);
            ps.setInt(2, idPal2);
            ps.setString(3, iso1);
            ps.setString(3, iso2);
            
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
    
    /**
     * Elimina una traducción registrada en la base de datos.
     * @param tradu
     * @return 
     */
    public boolean eliminarTraduccion(Traduccion tradu)
    {
        if(tradu == null)
            return false;

        boolean bandera = true;
        String idPalabra1 = tradu.getTermino1().getPalabra().getTermino();
        String idPalabra2 = tradu.getTermino2().getPalabra().getTermino();
        Palabra pal1 = this.crudPalabra.leerPalabra(-1, idPalabra1);
        Palabra pal2 = this.crudPalabra.leerPalabra(-1, idPalabra2);
        tradu.getTermino1().setPalabra(pal1);
        tradu.getTermino2().setPalabra(pal2);
        
        String isoPalabra1 = tradu.getTermino1().getIdioma().getIso();
        String isoPalabra2 = tradu.getTermino2().getIdioma().getIso(); 
        System.out.println("LA INFOOO: " + idPalabra1 + "," + idPalabra2 + "," + isoPalabra1 + "," + isoPalabra2);
        
        String consulta = "DELETE FROM palabra_traducida WHERE id_palabra_idioma_traduce1 = ? "
                + "AND id_palabra_idioma_traduce2 = ? AND iso_palabra_idioma_traduce1 = ? "
                + "AND iso_palabra_idioma_traduce2 = ?";
        try 
        {
            PreparedStatement ps = this.conexion.getConexion().prepareStatement(consulta);
            ps.setInt(1,  pal1.getId());
            ps.setInt(2,  pal2.getId());
            ps.setString(3,  isoPalabra1);   
            ps.setString(4,  isoPalabra2);
            
            ps.executeUpdate(); 
        } catch (SQLException e) 
        {
            return false;
        }
        
        return bandera;
    }
    
    
}
