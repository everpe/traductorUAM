/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import javax.swing.JComboBox;

/**
 *
 * @author Alejo
 */
public class ControladorVista 
{
    public void llenarCombo(JComboBox combo, String lista)
    {
        combo.removeAllItems();
        String[] listado = lista.split(",");
        int numIdi = listado.length;
        for(int i = 0; i < numIdi; i++) 
        {
            combo.addItem(listado[i]);
            
        }
        
    }
    
}
