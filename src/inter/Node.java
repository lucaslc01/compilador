/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package inter;

import java.util.Vector;
import lexico.*;

/**
 *
 * @author pedro
 */
public class Node {
    int lexline = 0;
    private static Vector<String> Erros = new Vector<String>();
    Node () {
        lexline = Lexer.linha;
    }
    
    void error (String s) {
        Erros.add("Proximo à linha "+lexline+": "+s+"\n");
    }
    static int labels = 0;
    public int newlabel() {
        return ++labels;
    }
    public void emitlabel(int i) {
        System.out.println("L " + i + ":");
    }
    public void emit (String s) {
        System.out.println("\t"+s);
    }
    public void returnErros(){
        if(Erros.size() > 0){
            System.out.println("Erros Semânticos");
            for(String msg : Erros){
                System.out.print(msg);
            }
        }
    }
}
