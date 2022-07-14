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
    public int width = 0;
    public Tipo(String lexema, Tag tag, int width) {
        super(lexema, tag);
        this.width = width;
    }
    public static final Tipo INT = new Tipo("int", Tag.BASIC, 4);
    public static final Tipo FLOAT = new Tipo("float", Tag.BASIC, 8);
    public static final Tipo CHAR = new Tipo("char", Tag.BASIC, 1);
    public static final Tipo BOOL = new Tipo("bool", Tag.BOOL, 1);
    public static final Tipo ERROR = new Tipo("error", Tag.ERROR, 0);
    
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
