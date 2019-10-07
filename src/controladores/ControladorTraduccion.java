package controladores;

import bd.BaseDatos;
import java.net.InetAddress;
import java.util.ArrayList;
import logica.IPScanner;
import logica.Idioma;
import logica.Palabra;
import logica.Termino;
import logica.Traduccion;
import udp.ClienteUDP;

/**
 * FECHA ==> 2019-09-16.
 * Cumple la función de controlador de términos entre la interfaz gráfica
 * y la funcionalelidad de los términos del sistema.
 * @author Luis Alejandro Gómez C.
 * @author Ever Peña B.
 * @version 1.0.0
 */
public class ControladorTraduccion 
{
    /**
     * Base de datos del sistema.
     */
    private BaseDatos bd;
    
    private IPScanner scaner;

    /**
     * Crea un controlador para la comunicación con la interfaz.
     * @param bd Base de datos del sistema.
     */
    public ControladorTraduccion(BaseDatos bd, IPScanner scanner) 
    {
        this.bd = bd;
        this.scaner = scanner;
    }
    
    /**
     * Agrega una nueva traducción a la base de datos del sistema.
     * @param termino1 Término #1.
     * @param termino2 Término #2. (Término #1 en diferente idioma)
     * @return True=>Se agrego correctamente, False=>No se agrego.
     */    
    public boolean agregarTermino(Termino termino1, Termino termino2)
    {
        //Validacion de que se ingrese información correcta.
        if(termino1 == null || termino2 == null)
            return false;
        
        if((termino1.getIdioma().getIso()).equals((termino2.getIdioma().getIso())))
            return false;
        
        if((termino1.getPalabra().getTermino().equals("")) ||
                                    (termino2.getPalabra().getTermino().equals("")))
            return false;
        
        boolean bandera = true;
        
        //Agrego las palabras a la base de datos.
        String palab1 = termino1.getPalabra().getTermino();
        Palabra palabra1 = this.bd.getCrudPalabra().leerPalabra(-1, palab1);
        //La palabra no existe en la base de datos ==> la agrego.
        if(palabra1 == null)
        {
            boolean ban = this.bd.getCrudPalabra().crearPalabra(termino1.getPalabra());
            if(!ban)
                return false;
            else
                palabra1 = this.bd.getCrudPalabra().leerPalabra(-1, palab1);
        }
        
        String palab2 = termino2.getPalabra().getTermino();
        Palabra palabra2 = this.bd.getCrudPalabra().leerPalabra(-1, palab2);
        //La palabra no existe en la base de datos ==> la agrego.
        if(palabra2 == null)
        {
            boolean ban = this.bd.getCrudPalabra().crearPalabra(termino2.getPalabra());
            if(!ban)
                return false;
            else
                palabra2 = this.bd.getCrudPalabra().leerPalabra(-1, palab2);
        }
        
        //Modifico las palabras para que no hayan errores en la base de datos.
        termino1.setPalabra(palabra1);
        termino2.setPalabra(palabra2);
        
        
        //Agrego los términos a la base de datos.
        boolean ban;
        //Si ya existe el término #1 en la base de datos.
        ban = this.bd.getCrudtermino().existeTermino(termino1);
        if(!ban)
            ban = this.bd.getCrudtermino().crearTermino(termino1);
        if(!ban)
            return false;
        
        //Si ya existe el término #2 en la base de datos.
        ban = this.bd.getCrudtermino().existeTermino(termino2);
        if(!ban)
            ban = this.bd.getCrudtermino().crearTermino(termino2);
        if(!ban)
            return false;
        
        Traduccion tra = new Traduccion(termino1, termino2);
        //Si ya existe la traducción.
        ban = this.bd.getCrudTraduccion().existeTermino(tra);
        if(!ban)
            ban = this.bd.getCrudTraduccion().crearTraduccion(tra);
        if(!ban)
            return false;
        //System.out.println("FINALLLL");        
        
        return bandera;     
    }
    
    /**
     * 
     * @param tr1
     * @param tr2
     * @return 
     */
    public boolean modificarTermino(Traduccion tr1, Traduccion tr2)
    {
        if(tr1 == null  || tr2 == null)
            return false;
        
        boolean bandera = this.bd.getCrudTraduccion().actualizarTraduccion(tr1, tr2);
        //boolean bandera2 = this.bd.getCrudTraduccion().actualizarTraduccion(tr2, tr1);
        if(bandera)
            return true;
        
        return false;
    }
    
    /**
     * Elimina una traducción de la base de datos.
     * @param tradu
     * @return 
     */
    public boolean eliminarTraduccion(Traduccion tradu)
    {        
        boolean bandera = this.bd.getCrudTraduccion().eliminarTraduccion(tradu);
System.out.println("bandera 1 :"+ bandera);
        Termino t1 = tradu.getTermino1();
        Termino t2 = tradu.getTermino2();
//        boolean bandera2 = this.bd.getCrudtermino().eliminarTermino(t1);
//        boolean bandera3 = this.bd.getCrudtermino().eliminarTermino(t2);
//System.out.println("bandera 2 :"+ bandera2);
//System.out.println("bandera 3 :"+ bandera3);
        
        return bandera;
    }
    
    /**
     * Obtiene la lista de todos los idiomas registrados.
     * @return 
     */
    public String obtenerListaIdiomas()
    {
        //combo.removeAllItems();
        String lista = "";
        ArrayList<Idioma>idiomas=this.bd.getCrudIdioma().obtenerTodosIdiomas();
        for (int i = 0; i < idiomas.size(); i++)
        {
            Idioma idioma=idiomas.get(i);
            //combo.addItem(idioma.getIso()+":"+idioma.getNombre());
            lista += idioma.getIso()+":"+idioma.getNombre() +",";
        } 
        
        return lista;
    }
    
    /**
     * Obtiene todas las traducciones registradas en la base de datos.
     * @return Lista de traducciones registradas en el sistema.
     */
    public ArrayList<Traduccion> obtenerTraducciones()
    {
        ArrayList<Traduccion> traducciones = new ArrayList<>();
        ArrayList<String[]> info = this.bd.getCrudTraduccion().obtenerTodasTraducciones();
        if(info == null)
            return null;
        else
        {
            //Datos para la consulta.
            int idPalOrig;
            int idPalDes;
            String isoIdiOrig;
            String isoIdiDest;            
            
            //Datos para la traducción.
            Palabra palOrig;
            Idioma idioOrig;
            Palabra palDest;
            Idioma idioDest;
            
            int numInfo = info.size();
            for(int i = 0; i < numInfo; i++) 
            {
                String[] informacionTra = info.get(i);
                try {
                    idPalOrig = Integer.parseInt(informacionTra[0]);
                    idPalDes = Integer.parseInt(informacionTra[1]);
                } catch (NumberFormatException e) 
                {
                    return null;
                }
                isoIdiOrig = informacionTra[2];
                isoIdiDest = informacionTra[3];
                
                //Sacando la información para la conversión de las traducciones.             
                palOrig = this.bd.getCrudPalabra().leerPalabra(idPalOrig, "");
                palDest = this.bd.getCrudPalabra().leerPalabra(idPalDes, "");
                idioOrig = this.bd.getCrudIdioma().leerIdioma(isoIdiOrig);
                idioDest = this.bd.getCrudIdioma().leerIdioma(isoIdiDest);
                
                Termino termino1 = new Termino(palOrig, idioOrig);
                Termino termino2 = new Termino(palDest, idioDest);
                
                Traduccion trad = new Traduccion(termino1, termino2);
                traducciones.add(trad);
            }
        }
        
        return traducciones;
    }
    
    public String traducir(String isos, String listado)//, String[] ipsServidores)
    {
        String traduccion = "";
        
//System.out.println("LISTADO: " + listado);
        
        String[] listaSplit = listado.split("-");
        Palabra palabra = null;
        ArrayList<String[]> traducciones = this.bd.getCrudTraduccion().obtenerTodasTraducciones();
        String traducida = "";
        
        for (int i = 0; i < listaSplit.length; i++) 
        {
            palabra = this.bd.getCrudPalabra().leerPalabra(-1, listaSplit[i]);

            if(palabra != null)
            {         
System.out.println("YYYYYYEEEEEEEEESSS");
                traducida = this.traducirPalabra(traducciones, isos, palabra.getTermino());
                traduccion += traducida + "-";
            }            
            else
            {
                //preguntarle a otro servidor por la palabra.
                boolean bandera = false;
                String resp = "";
                ArrayList<InetAddress> servidores = this.scaner.getConectados();
                int numServ = servidores.size();
                if(numServ == 0)
                    resp = listaSplit[i].toUpperCase();
                InetAddress inet;
                ClienteUDP cliente;
                for(int j = 0; j < numServ; j++) 
                {
                    inet = servidores.get(j);
                    cliente = new ClienteUDP(inet.getHostAddress());
                    
                    cliente.enviar("udp," + listaSplit[i] + "," + isos);
                    resp = cliente.recibir();
                    if(!resp.equals(listaSplit[i].toUpperCase()))
                    {
                        break;
                    }                    
                }                
                
                traduccion += resp + "-";                
            }
                
        }        
        return traduccion;
    }
    
    
    public String traducirPalabra(ArrayList<String[]> traducciones,String isos, String palabra)
    {
        int numT = traducciones.size();
        String[] trad;
        String[] idiomas = isos.split("!");
        
        String palabraTraducida = "";
        
        Palabra pal; 
        String nombPal;
        Palabra palTrad;
        boolean bandera = true;
        for (int i = 0; i < numT; i++) 
        {
            trad = traducciones.get(i);   
            pal = this.bd.getCrudPalabra().leerPalabra(Integer.parseInt(trad[0]), "");
            nombPal = pal.getTermino();
//System.out.println("trad[o]: " + trad[0]);
//System.out.println("trad[1]: " + trad[1]);
//System.out.println("trad[2]: " + trad[2]);
//System.out.println("trad[3]: " + trad[3]);
            
            
            if(nombPal.equals(palabra) && trad[2].equals(idiomas[0]) 
                                            && trad[3].equals(idiomas[1]))
            {
                System.out.println("PPAPAPPAPAPAPPAPAPAPAPAP");
                bandera = false;
                palTrad = this.bd.getCrudPalabra().leerPalabra(Integer.parseInt(trad[1]), "");
                palabraTraducida = palTrad.getTermino();
            }            
        }
        
        if(bandera)
            palabraTraducida = palabra.toUpperCase();
        
        return palabraTraducida;
    }
    
    
    
    
}
