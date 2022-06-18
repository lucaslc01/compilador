/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package inter;

import lexico.*;

/**
 *
 * @author pedro
 */
public class Node {
    int lexline = 0;
    Node () {
        lexline = Lexer.linha;
    }
    
    void error (String s) {
        throw new Error("Proximo à linha "+lexline+": "+s);
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
}
