package udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

/**
 * FECHA ==> 2019-09-11.
 * Representa el lado del cliente en la arquitectura Cliente/Servidor.
 * @author Luis Alejandro Gómez C.
 * @author Ever Peña B.
 * @version 1.0.0
 */
public class ClienteUDP 
{
    private boolean seCreo;
    /**
     * Socket UDP que va permitir la comunicación con el servidor.
     */
    private DatagramSocket socket;
    
    /**
     * Dirección ip del servidor.
     */
    private InetAddress hostServer; 
    
    /**
     * Puerto del por donde el servidor recibirá peticiones.
     */
    public static final int PUERTO_SERVER = 9020;  

    /**
     * Crea un cliente que se conectará a un servidor.
     * @param ipServer Dirección ip del servidor donde se conectará.
     */
    public ClienteUDP(String ipServer) 
    {
        try 
        {
            this.seCreo = true;
            this.socket = new DatagramSocket();
            this.hostServer = InetAddress.getByName(ipServer);
            this.socket.setSoTimeout(5000);
        }catch (SocketException | UnknownHostException e) 
        {
            this.seCreo = false;
            System.out.println("Error Creando El Socket Del Cliente: " + e.getMessage());
        }
    }
    
    /**
     * Permite realizar peticiones en el servidor.
     * @param msj Petición que se desea realizar.
     */
    public void enviar(String msj)
    {
        try {		
            byte[] buffer = msj.getBytes();

            DatagramPacket request = 
                    new DatagramPacket(buffer, buffer.length, this.hostServer, PUERTO_SERVER);
            socket.send(request);
        } catch (IOException e) 
        {
            System.out.println("Problemas Enviando Cliente: " + e.getMessage());
        }        
    }
    
    /**
     * Permite recibir las respuestas del servidor.
     * @return Mensaje con la respuesta del servidor.
     */
    public String recibir()
    {
        byte[] buffer = new byte[1000];
        String recibido = null;

        try {
            DatagramPacket request = 
                new DatagramPacket(buffer, buffer.length);

            socket.receive(request);
            recibido = new String(request.getData()).trim();
            
        } catch (IOException e) 
        {
            System.out.println("Problemas Recibiendo Cliente: " + e.getMessage());
        }
                
        return recibido;
    }
    
    
    
    
}
