/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tcpoo;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import javax.swing.JOptionPane;

/**
 *
 * @author ever
 */
public class Cliente extends Thread
{
    private Socket socket;
    private int serverPort;
    private String ipServer;
    private DataInputStream in;
    private DataOutputStream out;
    //private String respuesta;
    //private String peticion;

    public Cliente(int serverPort, String ipServer) 
    {        
        //this.peticion = "";
        this.serverPort = serverPort;
        this.ipServer = ipServer;
        try 
        {
            this.socket = new Socket(ipServer, serverPort);
            socket.setSoTimeout(1000);
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());
            System.out.println("Cliente Creado");
            
        } catch (IOException e) 
        {
            System.out.println("Problemas creando el socket del cliente");
        }
    }
    
    public String hablar(String peticion)
    {
        String respuesta = "";
        
        try {
            this.out.writeUTF(peticion);
            out.flush();

            if(peticion.equals("chao"))
            {
                this.socket.close();
                //break;
            }

            respuesta = in.readUTF();   

        } catch (IOException e) 
        {
            System.out.println("No se pudo conectar con el servidor");
        }           

        return respuesta;
    }

    
}
