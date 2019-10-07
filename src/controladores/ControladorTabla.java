package controladores;

import java.util.ArrayList;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import logica.Idioma;
import logica.Palabra;
import logica.Termino;
import logica.Traduccion;

/**
 * FECHA ==> 2019-09-16.
 * Controlador de las tablas que se ven de manera gráfica en la interfaz.
 * @author Luis Alejandro Gómez C.
 * @author Ever Peña B.
 * @version 1.0.0
 */
public class ControladorTabla 
{
    /**
     * Pinta la lista en la tabla de términos del sistema.
     * @param trad Listado de las traducciones registradas en el sistema.
     * @param modelTable Modelo de la tabla que se desea pintar.
     */
    public void paintTableTerminos(String trad, DefaultTableModel modelTable)
    {
        this.clearDataFromTable(modelTable);
        String[] traducciones = trad.split("&");
        //System.out.println("Traduccion(0): " + traducciones[0]);
        
        String[] termino1;
        String[] termino2;
        
        for (int i = 0; i < traducciones.length; i++) 
        {
            String[] terminos = traducciones[i].split(",");
            //System.out.println("TErminooooss: " + terminos[0] );
            
            termino1 = terminos[0].split("/");
            termino2 = terminos[1].split("/");
            
            //Añadiendo a la tabla.
            String[] fila = {termino1[0], termino1[1], termino2[0], termino2[1]};
            modelTable.addRow(fila);
            
        }
    }
    
    /**
     * Obtiene un término de alguna tabla de la interfaz gráfica.
     * @param tabla Tabla de la cúal se desea extraer el término.
     * @return Término completo.
     */
    public String getTerminoFromTable(JTable tabla)
    {
        //Datos que necesito para crear la traducción.
        Palabra pal1;
        Palabra pal2;
        Idioma idio1;
        Idioma idio2;
        
        Traduccion trad = null;
        int row1 = tabla.getSelectedRow();
        //int row = convertRowIndexToModel(tabla);
        int row = tabla.convertRowIndexToModel(row1);
        
        String codeTraduc = null;
        
        if(row != -1)
        {
            try 
            {
                pal1 = new Palabra(-1, tabla.getModel().getValueAt(row, 0).toString());
                pal2 = new Palabra(-1, tabla.getModel().getValueAt(row, 2).toString());
                String [] auxId = (tabla.getModel().getValueAt(row, 1).toString()).split(":");
                idio1 = new Idioma(auxId[0], auxId[1]);
                String [] auxId2 = (tabla.getModel().getValueAt(row, 3).toString()).split(":");
                idio2 = new Idioma(auxId2[0], auxId2[1]);
                Termino ter1 = new Termino(pal1, idio1);
                Termino ter2 = new Termino(pal2, idio2);
                
                trad = new Traduccion(ter1, ter2);
                codeTraduc = pal1.getTermino() + "/" + idio1.getIso() + ":" + idio1.getNombre() +
                        "#" + pal2.getTermino() + "/" + idio2.getIso() + ":" + idio2.getNombre();
                
                //trad = tabla.getModel().getValueAt(row, 0).toString();
            } catch (Exception e) 
            {
                return null;                
            }            
        }
        
        return codeTraduc;        
    }  
        
    /**
     * Realiza un filtrado en los datos de las tablas de la interfaz.
     * @param modelo
     * @param tabla
     * @param txt
     * @param campo 
     */
    public void filtrarDatos(DefaultTableModel modelo, JTable tabla, JTextField txt,
            int campo)
    {
        String filtro = txt.getText();      
        
        TableRowSorter<DefaultTableModel> trsfiltro = new TableRowSorter(modelo);
        tabla.setRowSorter(trsfiltro); 
        trsfiltro.setRowFilter(RowFilter.regexFilter(filtro, campo));               
    }   
    
    
    /**
     * Hace el barrido de la información contenida en una tabla.
     * @param modeloTabla Modelo de la tabla que se desea barrer.
     */
    public void clearDataFromTable(DefaultTableModel modeloTabla)
    {        
        int a =modeloTabla.getRowCount()-1;
        for(int i=a; i>=0; i--)
        {
            modeloTabla.removeRow(i);
        }            
    }
    
    
    public String dividirTraduccion(Traduccion traduccion)
    {
        String palOrigen;
        String idiOrigen;
        String palDestino;
        String idiDestino;  

        palOrigen = traduccion.getTermino1().getPalabra().getTermino();
        idiOrigen = traduccion.getTermino1().getIdioma().getIso() + ":" + 
                         traduccion.getTermino1().getIdioma().getNombre();
        palDestino = traduccion.getTermino2().getPalabra().getTermino();
        idiDestino = traduccion.getTermino2().getIdioma().getIso() + ":" + 
                         traduccion.getTermino2().getIdioma().getNombre();

        return (palOrigen + "/" + idiOrigen + "#" + palDestino + "/" + idiDestino);
    }
    
    
    public Traduccion construirTraduccion(String traduccion)
    {
        String[] terminos = traduccion.split("#");
        String[] termino1 = terminos[0].split("/");
        String[] termino2 = terminos[1].split("/");
        
        Palabra pal1 = new Palabra(-1, termino1[0]);
        Palabra pal2 = new Palabra(-1, termino2[0]);
        
        String[] auxIdi1 = termino1[1].split(":");
        String[] auxIdi2 = termino2[1].split(":");
        
        Idioma id1 = new Idioma(auxIdi1[0], auxIdi1[1]);
        Idioma id2 = new Idioma(auxIdi2[0], auxIdi2[1]);
        
        Termino ter1 = new Termino(pal1, id1);
        Termino ter2 = new Termino(pal2, id2);
        
        Traduccion trad = new Traduccion(ter1, ter2);
        
        return trad;
        
    }
    
}
