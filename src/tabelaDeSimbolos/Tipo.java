/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tabelaDeSimbolos;
import lexico.*;
/**
 *
 * @author pedro
 */
public class Tipo extends Palavra {

    public Tipo(String lexema, Tag tag) {
        super(lexema, tag);
    }
    public static final Tipo INT = new Tipo("int", Tag.INT);
    public static final Tipo FLOAT = new Tipo("float", Tag.FLOAT);
    public static final Tipo CHAR = new Tipo("char", Tag.CHAR);
    
    // Funções para conversão de Tipo
    public static boolean numerico(Tipo tipo){
        if (tipo == Tipo.CHAR || tipo == Tipo.FLOAT || tipo == Tipo.INT) {
            return true;
        }
        return false;
    }
    public static Tipo max (Tipo p1, Tipo p2) {
        if (!numerico(p1) || ! numerico(p2)) return null;
        else if (p1 == Tipo.FLOAT || p2 == Tipo.FLOAT) return Tipo.FLOAT;
        else if (p1 == Tipo.INT || p2 == Tipo.INT) return Tipo.INT;
        else return Tipo.CHAR;
    }
    
}
