package logica;

import controladores.ControladorTraduccion;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import udp.ClienteUDP;

/**
 *
 * @author Alejo
 */
public class IPScanner
{
    private ArrayList<InetAddress> conectados;
    //private ClienteUDP cliente;

    public IPScanner() 
    {
        this.conectados = new ArrayList<>();
    }
    
    //La subred => 198.160.2
    public void getNetworkIPs(String subRed)
    {
        //ArrayList<String> disponibles = new ArrayList<String>();
        //InetAddress servidor = null;
        for (int i = 1; i < 255; i++) 
        {
            final int j = i;
            new Thread(() -> {
                try {
                String ser = subRed + "." + j;
                //System.out.println("Entre en el servidor: " + ser);
                InetAddress servidor = InetAddress.getByName(subRed + "." + j);
                if(servidor.isReachable(2000))
                {
                    //System.out.println("PPPPADDDDAAAAAAAAAAA");
                    if(this.isReachable(ser, 9020))
                    {
                        System.out.println("Disponible: " + ser);
                        this.conectados.add(servidor);
                    }                    
                }
                else
                    System.out.println("No disponible " + ser);
            } catch (IOException e) 
            {
                System.out.println("Error buscando ips");
            }
                
            }).start();    
        }
        
        //return disponibles;
    }

    public ArrayList<InetAddress> getConectados() {
        return conectados;
    }
            
    
        
    private boolean isReachable(String addr, int openPort) {
        // Any Open port on other machine
        // openPort =  22 - ssh, 80 or 443 - webserver, 25 - mailserver etc.
        try 
        {
             ClienteUDP cliente = new ClienteUDP(addr);
             cliente.enviar("hola,df,df");
             String rpta = cliente.recibir();
             if(rpta.equals("hola"))
                 return true;
        }catch(Exception e)
        {
            return false;
        }
        return false;
    }
    

//    public static void main(String[] arguments) 
//    {
//        IPScanner ip = new IPScanner();
//        ip.getNetworkIPs("192.168.20");
//        
//        
//    }
}