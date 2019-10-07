package udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import logica.Sistema;
import tcpoo.Protocolo;

/**
 * FECHA ==> 2019-09-10.
 * Representa el servidor del sistema.
 * @author Luis Alejandro Gómez C.
 * @author Ever Peña B.
 * @version 1.0.0
 */
public class ServidorUDP 
{
    /**
     * Protocolo de comunicación utilizado.
     */
    private Protocolo protocolo;
    
    /**
     * Puerto destinado para recibir peticiones.
     */
    public static final int PUERTO = 9020;

    /**
     * Dirección ip del servidor.
     */
    private InetAddress host; 	 
    
    /**
     * Socket que permite la comunicación del servidor con los clientes.
     */
    private DatagramSocket socket;
    
    /**
     * Permite la gestión del sistema.
     */
    private Sistema sistema;

    /**
     * Crea un servidor del sistema.
     * @param sistema Permite la gestión del sistema.
     * @param host Dirección ip del servidor.
     */
    public ServidorUDP(Sistema sistema, String host) 
    {        
        this.sistema = sistema;
        this.protocolo = new Protocolo(sistema);
        try {
            this.host =  InetAddress.getByName(host);
            this.socket = new DatagramSocket(PUERTO);
        } catch (SocketException | UnknownHostException e) 
        {
            System.out.println("Error En la Conexión Del Socket: " + e.getMessage());
        }
        
    }
    
    /**
     * Permite recibir las peticiones de los clientes.
     */
    public void recibir()
    {
        while(true)
        {
            byte[] buffer = new byte[1000];
            
            DatagramPacket request = new DatagramPacket(buffer, buffer.length);	
            String respuesta = "";
            
            try 
            {
                socket.receive(request);
                String orden = new String(request.getData()).trim();
                //System.out.println("al servidor llega: "+orden);
                respuesta = this.protocolo.comprobarComunicacion(orden);
                //respuesta = this.establecerProtocolo(orden);
            } catch (IOException e) 
            {
                System.out.println("Problemas Recibiendo [SERVIDOR]: " + e.getMessage());
            }
            
            enviar(respuesta,request.getAddress(), request.getPort());
        }
    }
    
    /**
     * Envia un mensaje o respuesta al cliente.
     * @param msj La petición que realizá el cliente.
     * @param host Dirección ip del cliente.
     * @param puerto Puerto por el que el cliente escucha.
     */
    private void enviar(String msj, InetAddress host, int puerto)
    {
        byte[] buffer = msj.getBytes();

        DatagramPacket request = 
                new DatagramPacket(buffer, buffer.length, host, puerto);
        try {
            socket.send(request);
        } catch (IOException e) 
        {
            System.out.println("Problemas enviando desde el servidor"+e.getMessage());
        }
    }   
 
}
