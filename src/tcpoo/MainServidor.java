package tcpoo;

import bd.BaseDatos;
import bd.Conexion;
import logica.Sistema;

/**
 *
 * @author ever
 */
public class MainServidor {
    public static void main(String[] args) 
    {
        Conexion conexion= new Conexion(1);
        BaseDatos baseDatos= new BaseDatos(conexion);
        Sistema sistema = new Sistema(baseDatos);       
        
        Servidor servidor = new Servidor(9090, sistema);
        servidor.hablar();
        //servidor.start();
    }
}
