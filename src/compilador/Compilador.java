/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compilador;
import java.io.*;
import javax.swing.JOptionPane;
import lexico.*;
//import lexico.*;
/**
 *
 * @author pedro
 */
public class Compilador {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException{
        // TODO code application logic here
        String file = JOptionPane.showInputDialog("Entre com o nome do arquivo: ");
        PushbackInputStream input = new PushbackInputStream(new FileInputStream(file));
        Lexer analisadorLexico = new Lexer();
        analisadorLexico.analiseLexica(input);
        
        
    }
    
}
