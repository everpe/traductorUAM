package tcpoo;

import java.util.ArrayList;
import logica.Idioma;
import logica.Palabra;
import logica.Sistema;
import logica.Termino;
import logica.Traduccion;

/**
 * FECHA ==> 2019-09-11.
 * Representa el protocolo utilizado para la comunicación Cliente/Servidor.
 * @author Luis Alejandro Gómez C.
 * @author Ever Peña B.
 * @version 1.0.0
 */
public class Protocolo 
{
    /**
     * Permite la gestion del sistema.
     */
    private Sistema sistema;
    
    //private String[] ipsServidores;   

    /**
     * Inicializa un protocolo de comunicación.
     * @param sistema Permitirá le gestión del sistema.
     */
    public Protocolo(Sistema sistema) 
    {
        this.sistema = sistema;
//        ipsServidores = new String[1];
//        ipsServidores[0] = "192.168.0.7";
        //ipsServidores[1] = "";
    }
    
    /**
     * Realiza las comprobaciones para saber que información le esta 
     * pidiendo el cliente al servidor.
     * @param peticionCompleta Código de la petición del cliente.
     * @return La respuesta que le da el cliente al servidor.
     */
    public String comprobarComunicacion(String peticionCompleta)
    {
        String respuesta = "";
        boolean bandera = false;
        String[] mensaje = peticionCompleta.split(",");
System.out.println("LONGITUD: " + mensaje.length);
        
        //System.out.println("PETIIICIOONN: " + peticionCompleta);
        
        if(mensaje[0].equals("listar") && mensaje[1].equals("idiomas"))
            return listarIdiomas();
        else if(mensaje[0].equals("agregar") && mensaje[1].equals("traduccion"))
            return agregarTraduccion(mensaje[2]);
        else if(mensaje[0].equals("listar") && mensaje[1].equals("traducciones"))
            return listarTraducciones();
        else if(mensaje[0].equals("modificar") && mensaje[1].equals("traduccion"))
            return modificarTraduccion(mensaje[2]);
        else if(mensaje[0].equals("eliminar") && mensaje[1].equals("traduccion"))
            return eliminarTraduccion(mensaje[2]);
        else if(mensaje[0].equals("traducirFrase"))
            return traducir(mensaje[1], mensaje[2]);
        else if(mensaje[0].equals("udp"))
            return traducirUDP(mensaje[1], mensaje[2]);
        else if(mensaje[0].equals("hola"))
            return "hola";
            
        
        
        return respuesta;
    }
    
    public String listarIdiomas()
    {
        //ArrayList<Idioma>idiomas=this.bd.getCrudIdioma().obtenerTodosIdiomas();
        return this.sistema.getControladorTrad().obtenerListaIdiomas();        
    }
    
    public String agregarTraduccion(String datos)
    {
        //System.out.println("MMMEEETOOOODOOO");
        String[] info = datos.split(":");
        String[] ter1 = info[0].split("/");
        String[] ter2 = info[1].split("/");
        
        try {
            //System.out.println("OOOOOOOOOEEEEE");
            Termino t1 = new Termino(new Palabra(Integer.parseInt(ter1[0]), 
                                        ter1[1]), new Idioma(ter1[2], ter1[3]));
            Termino t2 = new Termino(new Palabra(Integer.parseInt(ter2[0]), 
                                        ter2[1]), new Idioma(ter2[2], ter2[3]));
            
            boolean bandera=this.sistema.getControladorTrad().agregarTermino(t1, t2);
            boolean bandera2 = this.sistema.getControladorTrad().agregarTermino(t2, t1);
            //System.out.println("PPPPASSSSSAAA");
            if(bandera && bandera2)
                return "si";
            else
                return "no";
        } catch (NumberFormatException e) 
        {            
            //System.out.println("Error convirtiendo ID");
            return "no";
        }   
    }
    
   public String listarTraducciones()
   {
       ArrayList<Traduccion> tra = this.sistema.getControladorTrad().obtenerTraducciones();
       System.out.println("Numero de traducciones: " + tra.size());
       int numTra = tra.size();
       Traduccion traduccion;
       String palOrigen;
       String idiOrigen;
       String palDestino;
       String idiDestino;       
       
       String codTraduccion = "";
       String aux = "";
       
       for(int i = 0; i < numTra; i++) 
       {
           traduccion = tra.get(i);
           palOrigen = traduccion.getTermino1().getPalabra().getTermino();
           idiOrigen = traduccion.getTermino1().getIdioma().getIso() + ":" + 
                            traduccion.getTermino1().getIdioma().getNombre();
           palDestino = traduccion.getTermino2().getPalabra().getTermino();
           idiDestino = traduccion.getTermino2().getIdioma().getIso() + ":" + 
                            traduccion.getTermino2().getIdioma().getNombre();
           
           aux = palOrigen + "/" + idiOrigen + "," + palDestino + "/" + idiDestino + "&"; 
           codTraduccion += aux;           
       }
       
       return codTraduccion;
   }
        
   public String modificarTraduccion(String datos)
   {
       
System.out.println("DATOOOOSSS: " + datos);
       String respuesta = "no";
       String[] terminos = datos.split("&");
System.out.println("Longitud de teminos: " + terminos.length);
System.out.println("Termino1: " + terminos[0]);
System.out.println("Termino2: " + terminos[1]);
       
       Traduccion old = this.sistema.getControladorTabla().construirTraduccion(terminos[0]);
       Traduccion neww = this.sistema.getControladorTabla().construirTraduccion(terminos[1]);
       
       boolean bandera =this.sistema.getControladorTrad().modificarTermino(old, neww);
       if(bandera)
           return "si";
       
       return respuesta;
   }
   
   public String eliminarTraduccion(String traduccion)
   {
System.out.println("LA TRADUCCIONNN: " + traduccion);
       Traduccion trad = this.sistema.getControladorTabla().construirTraduccion(traduccion);
       Traduccion replica;
       
       Termino term1 = trad.getTermino1();
       Termino term2 = trad.getTermino2();
       
       replica = new Traduccion(term2, term1);
       
       boolean ban = this.sistema.getControladorTrad().eliminarTraduccion(trad);
       boolean ban2 = this.sistema.getControladorTrad().eliminarTraduccion(replica);
       
       if(ban && ban2)
           return "si";
       
       return "no";
   }
    
   public String traducir(String isos, String listado)
   {
       String trad = this.sistema.getControladorTrad().traducir(isos, listado);//, ipsServidores);
       
       
       return trad;
   }
   
   public String traducirUDP(String palabra, String idiomas)
   {
       String respuesta = palabra.toUpperCase();
       ArrayList<String[]> traducciones = this.sistema.getBd().getCrudTraduccion().obtenerTodasTraducciones();
       
       respuesta = this.sistema.getControladorTrad().traducirPalabra(traducciones, idiomas, palabra);
       
       return respuesta;
   }
   
   
}
