//Para git esta es la rama copia .
package tcpoo;

import vistas.VentanaInicio;

/**
 *
 * @author ever
 */
public class MainCliente {
    public static void main(String[] args) {
        Cliente cliente = new Cliente(9090, "127.0.0.1");
        VentanaInicio ventana = new VentanaInicio(cliente);
        ventana.setVisible(true);
        //cliente.hablar();
    }
}
