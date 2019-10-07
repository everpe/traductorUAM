package udp;

import bd.BaseDatos;
import bd.Conexion;
import logica.Sistema;


public class MainServidorUDP 
{
    public static void main(String[] args) {
        Conexion con = new Conexion(2);
        BaseDatos bd = new BaseDatos(con);
        Sistema sist = new Sistema(bd);
        
        String ip = "192.168.122.57";
        ServidorUDP serv = new ServidorUDP(sist, ip);
        serv.recibir();
        
    }
    
}
